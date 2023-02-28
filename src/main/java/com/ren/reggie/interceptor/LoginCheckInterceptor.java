package com.ren.reggie.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-02-27 20:58
 * @description:
 **/
@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("# 登录拦截器拦截到目标 : {} #",request.getRequestURI());
        Object employee = request.getSession().getAttribute("employee");
        if(employee == null) {
            request.setAttribute("msg","请登录后访问");
            request.getRequestDispatcher("/login.html").forward(request,response);
            return false;
        }
        return true;
    }
}
