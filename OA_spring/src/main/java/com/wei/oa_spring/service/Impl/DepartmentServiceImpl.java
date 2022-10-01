package com.wei.oa_spring.service.Impl;

import com.wei.oa_spring.model.dao.DepartmentMapper;
import com.wei.oa_spring.model.pojo.Department;
import com.wei.oa_spring.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    DepartmentMapper departmentMapper;

    /**
     * 获取登录用户部门信息
     *
     * @param departmentId
     * @return
     * Department department = departmentService.selectById(employee.getDepartmentId());
     * 通过employee.getDepartmentId() 可以拿到部门信息
     */
    @Override
    public Department selectById(Long departmentId) {
        return departmentMapper.selectByPrimaryKey(departmentId);
    }

}
