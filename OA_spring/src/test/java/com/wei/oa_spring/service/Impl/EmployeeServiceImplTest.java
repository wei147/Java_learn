package com.wei.oa_spring.service.Impl;

import com.wei.oa_spring.model.dao.EmployeeMapper;
import com.wei.oa_spring.model.pojo.Employee;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceImplTest {

    @Resource
    EmployeeMapper employeeMapper;

    @Test
    void selectById() {
        Employee employee = employeeMapper.selectByPrimaryKey(1L);
        System.out.println(employee);

    }
}