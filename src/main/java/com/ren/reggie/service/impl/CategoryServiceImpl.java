package com.ren.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ren.reggie.common.CustomException;
import com.ren.reggie.entity.Category;
import com.ren.reggie.entity.Dish;
import com.ren.reggie.entity.Setmeal;
import com.ren.reggie.mapper.CategoryMapper;
import com.ren.reggie.service.CategoryService;
import com.ren.reggie.service.DishService;
import com.ren.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-02 15:49
 * @description:
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 获取菜单分页集合
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<Category> getPage(Integer page, Integer pageSize, String name) {
        // 构造分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件(若name为空，则此句由utils报空)
        queryWrapper.like(StringUtils.hasText(name),Category::getName,name);
        // 添加排序条件
        queryWrapper.orderByDesc(Category::getUpdateTime);
        // 执行查询
        return categoryMapper.selectPage(pageInfo, queryWrapper);
    }

    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     * @return
     */
    @Override
    public boolean remove(Long id) {
        // 查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int dishCount = dishService.count(dishLambdaQueryWrapper);
        if (dishCount > 0) {
            // 已经关联菜品，抛出一个业务异常
            throw new CustomException("当前分类项关联了菜品，不能删除");
        }
        // 查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int setmealCount = setmealService.count(setmealLambdaQueryWrapper);
        if (setmealCount > 0){
            // 已经关联了套餐，抛出一个业务异常
            throw new CustomException("当前分类项关联了套餐，不能删除");
        }
        // 正常删除分类
        return super.removeById(id);
    }
}
