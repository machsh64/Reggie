package com.ren.reggie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-02-27 21:09
 * @description:
 **/
@Controller
public class PageController {

    @GetMapping("/login.html")
    public String login(){
        return "backend/page/login/login";
    }
}
