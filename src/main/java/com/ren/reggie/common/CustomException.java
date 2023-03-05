package com.ren.reggie.common;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-03-05 18:17
 * @description: 自定义业务异常
 **/
public class CustomException extends RuntimeException{

    public CustomException(String msg) {
        super(msg);
    }
}
