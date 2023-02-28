package com.ren.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ren.reggie.entity.Employee;

import java.util.List;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-02-27 17:17
 * @description:
 **/
public interface EmployeeService extends IService<Employee> {

    List<Employee> getEmpList(Integer page, Integer pageSize);

    Page<Employee> getPage(Integer page, Integer pageSize,String name);
}
