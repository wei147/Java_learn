package com.imooc.spring.jdbc.service;

import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service    //完成ioc容器的实例化
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true) //默认不使用事务，只读操作
public class BatchService {
    @Resource   //完成dao的注入
    private EmployeeDao employeeDao;

    @Transactional(propagation = Propagation.REQUIRES_NEW)  //设置独立的在事务中运行
    public void importJob1(){
        for (int i = 1; i <= 5; i++) {
            Employee employee = new Employee();
            employee.setEno(8000 + i);
            employee.setEname("研发部员工" + i);
            employee.setSalary(4000f);
            employee.setDname("研发部");
            employee.setHiredate(new Date());
            employeeDao.insert(employee);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void importJob2(){
        for (int i = 1; i <= 5; i++) {
            Employee employee = new Employee();
            employee.setEno(9000 + i);
            employee.setEname("市场部员工" + i);
            employee.setSalary(4500f);
            employee.setDname("市场部");
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
