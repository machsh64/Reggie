package com.ren.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ren.reggie.entity.Category;
import com.ren.reggie.entity.Employee;
import com.ren.reggie.mapper.CategoryMapper;
import com.ren.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-02 15:49
 * @description:
 **/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取菜单分页集合
     * @param page
     * @param pageSize
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
}
