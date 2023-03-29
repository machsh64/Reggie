package com.ren.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ren.reggie.common.R;
import com.ren.reggie.dto.DishDTO;
import com.ren.reggie.entity.Category;
import com.ren.reggie.entity.Dish;
import com.ren.reggie.entity.DishFlavor;
import com.ren.reggie.mapper.CategoryMapper;
import com.ren.reggie.mapper.DishMapper;
import com.ren.reggie.service.CategoryService;
import com.ren.reggie.service.DishFlavorService;
import com.ren.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-05 16:41
 * @description:
 **/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取菜单分页集合
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<DishDTO> getPage(Integer page, Integer pageSize, String name) {
        // 构造分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDTO> dishDTOPage = new Page<>();
        // 构造条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件(若name为空，则此句由utils报空)
        queryWrapper.like(StringUtils.hasText(name), Dish::getName, name);
        // 添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        // 执行查询
        this.page(pageInfo, queryWrapper);
        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDTOPage, "records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDTO> list = null;
        // 将records中的普通属性赋值dishDTO并赋值categoryName
        list = records.stream().map((item) -> {
            DishDTO dishDTO = new DishDTO();
            BeanUtils.copyProperties(item, dishDTO); // 将item中普通的属性copy到dishDTO中
            // 根据id查询分类对象
            Category category = categoryMapper.selectById(item.getCategoryId());
           // Category category1 = categoryService.getById(item.getCategoryId());
            dishDTO.setCategoryName(category.getName());
            return dishDTO;
        }).collect(Collectors.toList());
        // 将查询到并修改后的dishDTOList赋值到Page的records中
        return dishDTOPage.setRecords(list);
    }

    /**
     * 新增菜品,同时保存口味数据
     *
     * @param dishDTO
     */
    @Transactional
    @Override
    public boolean saveWithFlavor(DishDTO dishDTO) {
        // 保存菜品的基本信息到菜品表dish
        boolean b = this.save(dishDTO);
        Long dishId = dishDTO.getId();  // 菜品id
        List<DishFlavor> flavors = dishDTO.getFlavors();
        // 将flavors中所有口味数据的dishId赋值
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        // 保存菜品口味数据到菜品口味表dish_flavor
        boolean c = dishFlavorService.saveBatch(flavors);
        return b&&c;
    }

    /**
     * 更新菜品详情与口味
     * @param dishDTO
     * @return
     */
    @Transactional
    @Override
    public boolean updateWithFlavor(DishDTO dishDTO) {
        // 修改菜品普通属性
        boolean b = this.updateById(dishDTO);
        // 修改菜品口味属性
        // --清理当前菜品对应口味数据--dish_flavor表的delete操作
        LambdaUpdateWrapper<DishFlavor> flavorLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        flavorLambdaUpdateWrapper.eq(DishFlavor::getDishId,dishDTO.getId());
        dishFlavorService.remove(flavorLambdaUpdateWrapper);
        // --添加当前提交过来的口味数据--dish_flavor表的insert操作
        List<DishFlavor> flavors = dishDTO.getFlavors();
        // ----将flavors中所有口味数据的dishId赋值
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDTO.getId());
            return item;
        }).collect(Collectors.toList());
        boolean c = dishFlavorService.saveBatch(flavors);
        return b&&c;
    }

    /**
     * 根据id获取菜品详情, 口味, 分类名称
     * @param id
     * @return
     */
    @Override
    public DishDTO getDTOById(Long id) {
        // 获取dish
        Dish dish = this.getById(id);
        DishDTO dishDTO = new DishDTO();
        // 将dish属性copy到dishDTO中
        BeanUtils.copyProperties(dish, dishDTO);
        // 设置dishDTO的flavors属性
        LambdaQueryWrapper<DishFlavor> flavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        flavorLambdaQueryWrapper.eq(DishFlavor::getDishId,id);
        dishDTO.setFlavors(dishFlavorService.list(flavorLambdaQueryWrapper));
        // 设置dishDTO的categoryName属性
        dishDTO.setCategoryName(categoryMapper.selectById(dish.getCategoryId()).getName());
        return dishDTO;
    }
}
