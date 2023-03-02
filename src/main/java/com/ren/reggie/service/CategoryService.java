package com.ren.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ren.reggie.entity.Category;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-02 15:48
 * @description:
 **/
public interface CategoryService extends IService<Category> {

    Page<Category> getPage(Integer page, Integer pageSize, String name);

}
