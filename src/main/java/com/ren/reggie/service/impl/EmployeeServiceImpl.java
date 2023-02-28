package com.ren.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ren.reggie.entity.Employee;
import com.ren.reggie.mapper.EmployeeMapper;
import com.ren.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-02-27 17:17
 * @description:
 **/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 获取员工列表集合  弃用
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public List<Employee> getEmpList(Integer page, Integer pageSize) {
        Page<Employee> employeePage = employeeMapper.selectPage(new Page<>(page, pageSize), null);
        List<Employee> employeeList = employeePage.getRecords();
        return employeeList;
    }

    /**
     * 获取员工分页集合
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public Page<Employee> getPage(Integer page, Integer pageSize, String name) {
        // 构造分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        // 构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件(若name为空，则此句由utils报空)
        queryWrapper.like(StringUtils.hasText(name),Employee::getName,name);
        // 添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // 执行查询
        return employeeMapper.selectPage(pageInfo, queryWrapper);
    }
}
