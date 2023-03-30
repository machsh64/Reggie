package com.ren.reggie.dto;

import com.ren.reggie.entity.Setmeal;
import com.ren.reggie.entity.SetmealDish;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.util.List;

@ApiModel("套餐数据传输对象")
@Data
public class SetmealDTO extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
