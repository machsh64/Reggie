package com.ren.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-05 16:27
 * @description:
 **/
@ApiModel("菜品实体类")
@Data
@TableName("dish")
public class Dish implements Serializable {

    public static final long  serialVersionUID = 1L;

    private Long id;

    // 菜品名称
    private String name;

    // 分类id
    private Long categoryId;

    // 菜品分类名称
    @TableField(exist = false)
    private String categoryName;

    // 菜品价格
    private Double price;

    // 商品码
    private String code;

    // 图片
    private String image;

    // 描述信息
    private String description;

    // 状态 (0停售 1起售)
    private Integer status;

    // 顺序
    private Integer sort;

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
