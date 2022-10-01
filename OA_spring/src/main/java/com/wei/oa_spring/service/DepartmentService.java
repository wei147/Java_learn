package com.wei.oa_spring.service;

import com.wei.oa_spring.model.pojo.Department;

public interface DepartmentService {

    Department selectById(Long departmentId);
}
