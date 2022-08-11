package com.imooc.spring.jdbc.service;

import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service    //完成ioc容器的实例化
//声明式事务核心注解
//放在类上，将声明式事务配置应用于当前所有方法，默认事务传播为 REQUIRED
@Transactional(propagation = Propagation.REQUIRED)
public class EmployeeService {
    @Resource   //完成dao的注入
    private EmployeeDao employeeDao;
    @Resource  //要保证属性名和bean ID保持一致就可以了 （bean id会将BatchService 变成 batchService）
    private BatchService batchService;

    //也可以单独设置事务。这里是查询方法，采用非事务的方式运行。 readOnly = true 只读的并不需要使用事务。方法上配置的优先级比类的高
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Employee findById(Integer eno){
        return employeeDao.findById(eno);
    }

    public void batchImport() {
        for (int i = 1; i <= 10; i++) {
            if(i==3){
                throw new RuntimeException("意料之外的异常");
            }
            Employee employee = new Employee();
            employee.setEno(8000 + i);
            employee.setEname("员工" + i);
            employee.setSalary(4000f);
            employee.setDname("市场部");
            employee.setHiredate(new Date());
            employeeDao.insert(employee);
        }
    }

    public void startImportJob(){
        batchService.importJob1();
        if(1==1){
            throw new RuntimeException("意料之外的异常");
        }
        batchService.importJob2();
        System.out.println("批量导入成功");
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public BatchService getBatchService() {
        return batchService;
    }

    public void setBatchService(BatchService batchService) {
        this.batchService = batchService;
    }
}
