package com.ren.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ren.reggie.entity.Employee;
import com.ren.reggie.mapper.EmployeeMapper;
import com.ren.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-02-27 17:17
 * @description:
 **/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
