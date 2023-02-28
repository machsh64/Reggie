package com.ren.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ren.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: reggie_take_out
 * @author: Ren  https://github.com/machsh64
 * @create: 2023-02-27 17:16
 * @description:
 **/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
