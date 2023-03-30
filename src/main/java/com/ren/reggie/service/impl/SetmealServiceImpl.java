package com.ren.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ren.reggie.dto.SetmealDTO;
import com.ren.reggie.entity.Setmeal;
import com.ren.reggie.mapper.CategoryMapper;
import com.ren.reggie.mapper.SetmealMapper;
import com.ren.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private SetmealMapper setmealMapper;

    @Autowired
    private CategoryMapper categoryMapper;

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
}
