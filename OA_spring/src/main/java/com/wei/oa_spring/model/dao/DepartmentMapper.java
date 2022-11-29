package com.wei.oa_spring.model.dao;

import com.wei.oa_spring.model.pojo.Department;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Long departmentId);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Long departmentId);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> selectAll();
}