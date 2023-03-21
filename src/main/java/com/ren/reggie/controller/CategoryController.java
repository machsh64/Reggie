package com.ren.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ren.reggie.common.R;
import com.ren.reggie.entity.Category;
import com.ren.reggie.entity.Dish;
import com.ren.reggie.service.CategoryService;
import com.ren.reggie.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-01 14:54
 * @description:
 **/
@Slf4j
@Api("Category控制层")
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishService dishService;

    @ApiOperation("添加菜品类")
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        boolean b = categoryService.save(category);
        return b ? R.success("新增菜品类成功") : R.error("新增菜品类失败");
    }

    @ApiOperation("修改菜品类")
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        LambdaUpdateWrapper<Category> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Category::getId, category.getId());
        boolean b = categoryService.update(category, updateWrapper);
        return b ? R.success("修改菜品类成功") : R.error("修改菜品类失败");

    }

    @ApiOperation("删除菜品类")
    @DeleteMapping
    public R<String> delete(Long ids) {
        boolean b = categoryService.remove(ids);
        return b ? R.success("删除成功") : R.error("删除失败");
    }


    @ApiOperation("获取菜单分页")
    @GetMapping("page")
    public R<Page<Category>> query(Integer page, Integer pageSize, String name) {
        return R.success(categoryService.getPage(page, pageSize, name));
    }


    @ApiOperation("以id获取菜品类详情")
    @GetMapping("/{id}")
    public R<Category> queryCategory(@PathVariable("id") Long id) {
        Category category = categoryService.getById(id);
        if (category != null) {
            return R.success(category);
        }
        return R.error("没有查询到菜品详情");
    }

    @ApiOperation("获取所有菜单分类")
    @GetMapping("/list")
    public R<List<Category>> queryCategoryList() {
        return R.success(categoryService.list());
    }

}
