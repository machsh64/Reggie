package com.ren.reggie;

import com.ren.reggie.entity.Employee;
import com.ren.reggie.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ReggieTakeOutApplicationTests {

    @Autowired
    private EmployeeService employeeService;


    @Test
    void contextLoads() {
    }

}
