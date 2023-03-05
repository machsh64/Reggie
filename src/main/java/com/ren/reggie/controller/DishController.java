package com.ren.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ren.reggie.common.R;
import com.ren.reggie.entity.Dish;
import com.ren.reggie.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-05 16:49
 * @description:
 **/
@Slf4j
@Api("Dish控制层")
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @ApiOperation("获取菜品分页")
    @GetMapping("page")
    public R<Page<Dish>> query(Integer page, Integer pageSize, String name) {
        return R.success(dishService.getPage(page, pageSize, name));
    }

    @ApiOperation("获取菜品详情")
    @GetMapping("/{id}")
    public R<Dish> queryById(@PathVariable("id") Long id) {
        return R.success(dishService.getById(id));
    }

    @ApiOperation("删除菜品")
    @DeleteMapping
    public R<String> delete(Long[] ids) {
        boolean b = dishService.removeById(ids);
        return b ? R.success("删除菜品成功") : R.error("删除菜品失败");
    }

    @ApiOperation("新增菜品")
    @PostMapping
    public R<String> insert(@RequestBody Dish dish) {
        boolean b = dishService.save(dish);
        return b ? R.success("新增菜品成功") : R.error("新增菜品失败");
    }

    @ApiOperation("修改菜品")
    @PutMapping
    public R<String> update(@RequestBody Dish dish) {
        LambdaUpdateWrapper<Dish> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Dish::getId, dish.getId());
        boolean b = dishService.update(dish, updateWrapper);
        return b ? R.success("修改菜品成功") : R.error("修改菜品失败");
    }

    @ApiOperation("更改菜品状态")
    @PostMapping("/status/{status}")
    public R<String> changeStatus(Long[] ids, @PathVariable("status") Integer status) {
        LambdaUpdateWrapper<Dish> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Dish::getId, ids).set(Dish::getStatus, status);
        boolean b = dishService.update(updateWrapper);
        return b ? R.success("菜品状态修改成功") : R.error("菜品状态修改失败");
    }


}
