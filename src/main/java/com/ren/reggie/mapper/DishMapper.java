package com.ren.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ren.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-05 16:39
 * @description:
 **/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
