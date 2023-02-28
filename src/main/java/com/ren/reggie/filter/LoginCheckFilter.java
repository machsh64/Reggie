package com.ren.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.ren.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-02-27 20:42
 * @description: 检查用户是否已经完成登录
 **/
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    // 路径匹配符，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        /**
         * 1，获取本次请求的URL
         * 2，判断此次请求是否需要处理
         * 3，如果不需要处理，则直接放行
         * 4，判断登录状态，如果已登录，则直接放行
         * 5，如果未登录，则返回未登录结果
         */

        // 1，获取本次请求的URL
        String requestURI = request.getRequestURI();
        log.info("拦截到请求：{} ",requestURI);
        // 定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/swagger-ui.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/csrf ",
                "/v2/**"
        };

        // 2，判断此次请求是否需要处理
        boolean check = check(urls, requestURI);

        // 3，如果不需要处理，则直接放行
        if (check) {
            log.info("本次请求 {} 不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        // 4，判断登录状态，如果已登录，则直接放行
        Object employee = request.getSession().getAttribute("employee");
        if (employee != null){
            log.info("用户已登录 用户id为：{} ",employee);
            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户未登录");
        // 5，如果未登录，则返回未登录结果
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，判断此次请求是否需要放行
     * @param urls
     * @param requestURL
     * @return
     */
    public boolean check(String[] urls, String requestURL) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURL);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
