package com.ren.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ren.reggie.entity.Dish;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-05 16:40
 * @description:
 **/
public interface DishService extends IService<Dish> {

    Page<Dish> getPage(Integer page, Integer pageSize, String name);

}
