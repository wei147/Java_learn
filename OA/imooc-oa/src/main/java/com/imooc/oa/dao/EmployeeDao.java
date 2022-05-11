package com.imooc.oa.dao;

import com.imooc.oa.entity.Employee;

/**
 * 接口类interface 只负责写方法 （而方法对应实现）
 */
public interface EmployeeDao {
    public Employee selectById(Long employeeId);
}
