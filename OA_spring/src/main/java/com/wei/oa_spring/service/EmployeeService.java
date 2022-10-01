package com.wei.oa_spring.service;

import com.wei.oa_spring.model.pojo.Employee;

public interface EmployeeService {

    /**
     * 通过employeeId索引员工信息
     *
     * @param employeeId 员工id
     * @return 员工对象
     */
    Employee selectById(Long employeeId);
}
