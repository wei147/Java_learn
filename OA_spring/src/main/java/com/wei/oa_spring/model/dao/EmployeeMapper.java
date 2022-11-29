package com.wei.oa_spring.model.dao;

import com.wei.oa_spring.model.pojo.Employee;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long employeeId);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Long employeeId);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    List<Employee> selectAll();
}