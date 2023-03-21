package com.ren.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ren.reggie.common.R;
import com.ren.reggie.entity.Setmeal;
import com.ren.reggie.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-05 18:01
 * @description:
 **/
@Slf4j
@Api("Setmeal控制层")
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @ApiOperation("获取套餐分页信息")
    @GetMapping("/page")
    public R<Page<Setmeal>> query(Integer page, Integer pageSize, String name) {
        return R.success(setmealService.getPage(page, pageSize, name));
    }

    @ApiOperation("根据id获取套餐分页详情")
    @GetMapping("/{id}")
    public R<Setmeal> queryById(@PathVariable("id") Long id) {
        Setmeal setmeal = setmealService.getById(id);
        return R.success(setmeal);
    }
}
