package com.imooc.spring.jdbc.service;

import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;

import java.util.Date;

public class EmployeeService {
    private EmployeeDao employeeDao;
    //持有batchService对象
    private BatchService batchService;

    public void batchImport() {
        for (int i = 1; i <= 10; i++) {
//            if (i == 3){
//                throw new RuntimeException("意料之外的异常");
//            }
            Employee employee = new Employee();
            employee.setEno(8000 + i);
            employee.setEname("员工" + i);
            employee.setSalary(4000f);
            employee.setDname("市场部");
            employee.setHiredate(new Date());
            employeeDao.insert(employee);
        }
    }

    //事务的嵌套使用  startImportJob默认也是开启声明式事务的               <tx:method name="*" propagation="REQUIRED"/>
    public void startImportJob() {  //嵌套使用时，importJob1和importJob2发现外侧有现成的事务了，运行时这两个job会加入到该外侧事务中，运行时该方法时整体是在一个事务中完成增删改查
        batchService.importJob1();
        //这个判断会影响到startImportJob() 一条数据都没有加入。 本来Job1和Job2应该是两个互不相关的方法，Job2执行不了Job2也应该执行才对 怎么实现？ 事务传播行为的设置  答：让xml中的 propagation="REQUIRES_NEW" 意思是 针对这个方法在运行时会产生新的事务进行处理
        if(1==1){
            throw new RuntimeException("意料之外的异常");
        }
        batchService.importJob2();
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
