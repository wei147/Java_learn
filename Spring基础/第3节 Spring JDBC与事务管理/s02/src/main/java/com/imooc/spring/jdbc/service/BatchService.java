package com.imooc.spring.jdbc.service;

import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;

import java.util.Date;

//专用于批量导入的服务
public class BatchService {
    private EmployeeDao employeeDao;


    public void importJob1() {
        for (int i = 1; i <= 2; i++) {
//            if (i == 3){
//                throw new RuntimeException("意料之外的异常");
//            }
            Employee employee = new Employee();
            employee.setEno(2000 + i);
            employee.setEname("研发部员工" + i);
            employee.setSalary(6000f);
            employee.setDname("研发部");
            employee.setHiredate(new Date());
            employeeDao.insert(employee);
        }
    }

    public void importJob2() {
        for (int i = 1; i <= 2; i++) {
            Employee employee = new Employee();
            employee.setEno(3000 + i);
            employee.setEname("批发部员工" + i);
            employee.setSalary(4900f);
            employee.setDname("批发部");
            employee.setHiredate(new Date());
            employeeDao.insert(employee);
        }
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
}
