package com.ren.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ren.reggie.entity.Setmeal;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-05 17:53
 * @description:
 **/
public interface SetmealService extends IService<Setmeal> {

    Page<Setmeal> getPage(Integer page, Integer pageSize, String name);

}
