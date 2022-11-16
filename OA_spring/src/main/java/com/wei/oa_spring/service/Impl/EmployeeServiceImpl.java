package com.wei.oa_spring.service.Impl;

import com.wei.oa_spring.model.dao.EmployeeMapper;
import com.wei.oa_spring.model.pojo.Employee;
import com.wei.oa_spring.model.pojo.Node;
import com.wei.oa_spring.service.EmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Resource
    EmployeeMapper employeeMapper;

    /**
     * 通过employeeId索引员工信息
     *
     * @param employeeId 员工id
     * @return 员工对象
     */
    @Override
    public Employee selectById(Long employeeId) {
        return employeeMapper.selectByPrimaryKey(employeeId);
    }

}
