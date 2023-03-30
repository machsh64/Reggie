package com.ren.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ren.reggie.common.R;
import com.ren.reggie.dto.SetmealDTO;
import com.ren.reggie.entity.Setmeal;
import com.ren.reggie.service.SetmealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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
    public R<Page<SetmealDTO>> query(Integer page, Integer pageSize, String name) {
        return R.success(setmealService.getPage(page, pageSize, name));
    }

    @ApiOperation("根据id获取套餐分页详情")
    @GetMapping("/{id}")
    public R<Setmeal> queryById(@PathVariable("id") Long id) {
        Setmeal setmeal = setmealService.getById(id);
        return R.success(setmeal);
    }

    @ApiOperation("根据ids进行删除套餐")
    @DeleteMapping
    public R<String> deleteByIds(@RequestParam("ids") Collection<Long> ids) {
        boolean b = setmealService.removeByIds(ids);
        return b ? R.success("删除套餐成功") : R.error("删除套餐失败");
    }

    @ApiOperation("根据ids进行修改套餐status状态")
    @PostMapping("/status/{status}")
    public R<String> changeStatus(@RequestParam("ids") Collection<Long> ids, @PathVariable("status") Integer status) {
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Setmeal::getId,ids).set(Setmeal::getStatus,status);
        boolean b = setmealService.update(updateWrapper);
        return b ? R.success("套餐状态修改成功") : R.error("套餐状态修改失败");
    }

}
