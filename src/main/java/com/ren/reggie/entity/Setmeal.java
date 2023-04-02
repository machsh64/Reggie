package com.ren.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-05 17:50
 * @description:
 **/
@ApiModel("套餐实体类")
@Data
@TableName("setmeal")
public class Setmeal implements Serializable {

    public static final long  serialVersionUID = 1L;

    private Long id;

    // 分类id
    private Long categoryId;

    // 套餐名称
    private String name;

    // 套餐价格
    private Double price;

    // 状态 (0停售 1起售)
    private Integer status;

    // 商品码
    private String code;

    // 描述信息
    private String description;

    // 图片
    private String image;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    private Integer isDeleted;
}
