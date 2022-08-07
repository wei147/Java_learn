package com.imooc.spring.jdbc.service;

import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;

public class EmployeeService {
    //底层依赖于EmployeeDao。完成属性设置
    private EmployeeDao employeeDao;
    //注入事务控制器
    private DataSourceTransactionManager transactionManager;    //还需要到 配置文件文件中关联才能使用

    //批量导入
    public void batchImport() {
        //定义了事务默认的标准配置
        TransactionDefinition definition = new DefaultTransactionDefinition();  //默认事务定义
        //开始一个事务,返回事务状态，事务状态说明当前事务的执行阶段 (这句话以后,所有数据的新增修改删除都放入到事务区中，由事务统一进行管理)
        TransactionStatus status = transactionManager.getTransaction(definition); //status事务状态，比如未提交，已提交，已回滚

        try {
            for (int i = 1; i <= 10; i++) {
//                if (i == 3) {  //就是说，第二个之后的不会出现在数据表中。
//                    throw new RuntimeException("意料之外的异常");
//                }
                Employee employee = new Employee(); //每一次循环新创建一个employee对象
                employee.setEno(700 + i);
                employee.setEname("员工" + i);
                employee.setSalary(3999F);
                employee.setDname("市场部");
                employee.setHiredate(new Date());
                employeeDao.insert(employee);
            }
        } catch (RuntimeException e) {
            //有异常就回滚事务
            transactionManager.rollback(status);
//            e.printStackTrace(); //如果这里打印(处理)了，意味着异常不会向外抛出，在sql内部就被消化了
//            但如果想让异常在外侧，也就是调用方进行处理的话，那么使用 throw e; 运行时异常被原封不动的抛出
            throw e;
            //异常捕获还是异常抛出要根据业务来决定
        }
        //当前for循环，如果执行成功。提交事务
        transactionManager.commit(status);
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public DataSourceTransactionManager getTransactionManager() {
        return transactionManager;
    }

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
}
