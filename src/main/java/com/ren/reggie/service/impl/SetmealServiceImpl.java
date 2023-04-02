package com.ren.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ren.reggie.common.UploadCommon;
import com.ren.reggie.dto.SetmealDTO;
import com.ren.reggie.entity.Setmeal;
import com.ren.reggie.entity.SetmealDish;
import com.ren.reggie.mapper.CategoryMapper;
import com.ren.reggie.mapper.SetmealMapper;
import com.ren.reggie.service.SetmealDishService;
import com.ren.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-05 17:54
 * @description:
 **/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetmealDishService setmealDishService;

    @Value("${basePath}")
    String basePath;

    /**
     * 获取菜单分页集合
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<SetmealDTO> getPage(Integer page, Integer pageSize, String name) {
        // 构造分页构造器
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDTO> setmealDTOPage = new Page<>();
        // 构造条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件(若name为空，则此句由utils报空)
        queryWrapper.like(StringUtils.hasText(name), Setmeal::getName,name);
        // 添加排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        // 执行查询
        this.page(pageInfo);
        // 将Setmeal中的除records数据全部赋值给setmealDishPage
        BeanUtils.copyProperties(pageInfo,setmealDTOPage,"records");
        // 将pageInfo中的records中的categoryName赋值并传输给setmealDishPage
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDTO> list = null;
        list = records.stream().map(item -> {
            SetmealDTO setmealDTO = new SetmealDTO();
            // 将item中的普通属性赋值给色图mealDTO
            BeanUtils.copyProperties(item, setmealDTO);
            // 赋值setmealDTO中的categoryName属性
            setmealDTO.setCategoryName(categoryMapper.selectById(item.getCategoryId()).getName());
            return setmealDTO;
        }).collect(Collectors.toList());
        // 将list分给setmealDTOPage的records
        setmealDTOPage.setRecords(list);
        return setmealDTOPage;
    }

    /**
     * 根据id获取套餐 以及套餐内的setmealDishes与categoryName
     * @param id
     * @return
     */
    @Override
    public SetmealDTO getDetailById(Long id) {
        Setmeal setmeal = this.getById(id);
        SetmealDTO setmealDTO = new SetmealDTO();
        // 将setmeal中的普通属性赋值给setmealDTO
        BeanUtils.copyProperties(setmeal,setmealDTO);
        // 将setmealDTO中的categoryName赋值
        setmealDTO.setCategoryName(categoryMapper.selectById(setmeal.getCategoryId()).getName());
        // 将setmealDTO中的setmealDishes赋值
        // --设置条件构造器 条件为 setmealDish表中的setmealId = id
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> setmealDishList = setmealDishService.list(setmealDishLambdaQueryWrapper);
        // -- 将获取到的setmealDishesList赋值给setmealDTO中的setmealDishes(List)属性
        setmealDTO.setSetmealDishes(setmealDishList);
        // 将setmealDTO返回
        return setmealDTO;
    }

    /**
     * 新增套餐 其中需要添加套餐关联的菜品与每个菜品的setmealId
     * @param setmealDTO
     * @return
     */
    @Transactional
    @Override
    public boolean saveSetmealDTO(SetmealDTO setmealDTO) {
        // 保存基本的套餐信息
        boolean b = this.save(setmealDTO);
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map(item -> {
            item.setSetmealId(setmealDTO.getId());
            return item;
        }).collect(Collectors.toList());
        boolean c = setmealDishService.saveBatch(setmealDishes);
        return b&&c;
    }

    /**
     * 修改套餐, 其中需要修改套餐中的Dishes
     * @param setmealDTO
     * @return
     */
    @Transactional
    @Override
    public boolean updateSetmealDTO(SetmealDTO setmealDTO) {
        // 更新套餐中的普通字段
        boolean b = this.updateById(setmealDTO);
        // 清除套餐中的菜品
        LambdaUpdateWrapper<SetmealDish> setmealDishLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        setmealDishLambdaUpdateWrapper.eq(SetmealDish::getSetmealId,setmealDTO.getId());
        setmealDishService.remove(setmealDishLambdaUpdateWrapper);
        // 为setmealDTO中的setmealDishes的setmealId赋值
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map(item -> {
            item.setSetmealId(setmealDTO.getId());
            return item;
        }).collect(Collectors.toList());
        // 新镇套餐中的菜品
        boolean c = setmealDishService.saveBatch(setmealDishes);
        return b&&c;
    }

    /**
     * 根据id集合删除套餐, 并删除套餐中的Dishes
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public boolean deleteSetmealDTO(Collection<Long> ids) {
        // 删除缓存的图片
        for (Long id : ids) {
            UploadCommon.removePicWithName(basePath, this.getById(id).getImage());
            // 删除套餐中的菜品
            LambdaUpdateWrapper<SetmealDish> setmealDishLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            setmealDishLambdaUpdateWrapper.eq(SetmealDish::getSetmealId,id);
            setmealDishService.remove(setmealDishLambdaUpdateWrapper);
        }
        // 删除套餐基本信息
        boolean b = this.removeByIds(ids);
        return b;
    }
}
