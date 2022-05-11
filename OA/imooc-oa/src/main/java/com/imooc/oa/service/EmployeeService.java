package com.imooc.oa.service;

import com.imooc.oa.dao.EmployeeDao;
import com.imooc.oa.entity.Employee;
import com.imooc.oa.utils.MybatisUtils;

public class EmployeeService {
    public Employee selectById(Long employeeId) {
        //因为返回的是一个employee对象，所以需要强制类型转换  employee.xml中 resultType="com.imooc.oa.entity.Employee"
        return (Employee) MybatisUtils.executeQuery(sqlSession -> {
            //getMapper方法根据接口自动生成具体的实现
            EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
            return employeeDao.selectById(employeeId);
        });
    }
}
