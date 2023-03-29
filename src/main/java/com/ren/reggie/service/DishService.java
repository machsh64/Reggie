package com.ren.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ren.reggie.dto.DishDTO;
import com.ren.reggie.entity.Dish;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-05 16:40
 * @description:
 **/
public interface DishService extends IService<Dish> {

    /**
     * 获取菜品分页信息
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<DishDTO> getPage(Integer page, Integer pageSize, String name);

    /**
     * 新增菜品,同时插入菜品对应的口味数据,需要操作两张表: dish, dish_flavor
     * @param dishDTO
     */
    boolean saveWithFlavor(DishDTO dishDTO);
}
