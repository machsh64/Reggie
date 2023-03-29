package com.ren.reggie.dto;

import com.ren.reggie.entity.Dish;
import com.ren.reggie.entity.DishFlavor;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-29 17:54
 * @description:
 **/
@ApiModel("菜品数据传输对象")
@Data
public class DishDTO extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
