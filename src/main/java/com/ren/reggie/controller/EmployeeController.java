package com.ren.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ren.reggie.common.R;
import com.ren.reggie.entity.Employee;
import com.ren.reggie.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-02-27 17:18
 * @description:
 **/
@Slf4j
@Api("Employee控制层")
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @ApiOperation("employee/login")
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        /**
         *  1，将页面提交的密码password进行md5加密处理
         *  2，根据页面提交的用户名username查询数据库
         *  3，如果没有查询到则返回登录失败的结果
         *  4，密码比对，如果不一致则放回登陆失败结果
         *  5，查看员工状态，如果为已禁用状态，则返回员工已禁用结果
         *  6，登录成功，将员工id存入session，并返回登录成功结果
         */
        // 1，将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2，根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        // 3，如果没有查询到则返回登录失败的结果
        if (emp == null){
            return R.error("登录失败");
        }

        // 4，密码比对，如果不一致则放回登陆失败结果
        if (!emp.getPassword().equals(password)){
            return R.error("登录失败");
        }

        // 5，查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0){
            return R.error("用户已禁用");
        }

        // 6，登录成功，将员工id存入session，并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        // 清理session中保存的当前员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
}
