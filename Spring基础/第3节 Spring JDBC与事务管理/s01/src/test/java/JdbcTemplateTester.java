import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;
import com.imooc.spring.jdbc.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

//测试用例类
@RunWith(SpringJUnit4ClassRunner.class) //将junit的控制权交给spring来进行。在Junit启动的时候自动初始化ioc容器
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
//指定加载文件  (这两句话等同于  ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");)
public class JdbcTemplateTester {
    //注入对应的类
    @Resource
    private EmployeeDao employeeDao;
    //注入对应的类
    @Resource
    private EmployeeService employeeService;

    //第一个测试用例
    @Test
    public void testFindById() {
        Employee employee = employeeDao.findById(3308);
        System.out.println(employee); //Employee{eno=3308, ename='张三', salary=6000.0, dname='研发部', hiredate=2011-05-08 00:00:00.0}
    }

    @Test
    public void testFindByDname(){
        List<Employee> employees = employeeDao.findByDname("研发部");
        System.out.println(employees); //[Employee{eno=3308, ename='张三', salary=6000.0, dname='研发部', hiredate=2011-05-08 00:00:00.0}, ..]
        System.out.println("==============================");
        for (Employee employee : employees){
            System.out.println(employee);
        }}

    @Test
    public void findMapByDname(){
        System.out.println(employeeDao.findMapByDname("研发部")); //[{empno=3308, s=6000.0}, {empno=3420, s=8700.0}]

    }

    @Test   //新增
    public void testInsert(){
        Employee employee = new Employee();
        employee.setEno(8008);
        employee.setDname("研发部");
        employee.setEname("小陈chen");
        employee.setSalary(4999.0F);
        employee.setHiredate(new Date());   //入职时间设置为当前
        employeeDao.insert(employee);
    }

    @Test   //修改
    public void testUpdate(){
        Employee employee = employeeDao.findById(808);
        employee.setEno(808);
        employee.setDname("UI部之天美分部");
        employee.setEname("小陈虎");
        employee.setSalary(employee.getSalary()+2000);
        int count = employeeDao.update(employee);
        System.out.println("本次更新 "+count+" 条数据");
    }

    @Test   //删除  (对于不存在的数据进行删除不会报错/产生实际影响)
    public void testDelete(){
        int count = employeeDao.delete(8008);
        System.out.println("本次更新 "+count+" 条数据");
    }

//    疑问：数据库的事务如何控制？没有体现到

    @Test
    public void testBatchImport(){
        //这里不是由一个事务提交的，而是分为10个，创建了10个数据库连接，10次插入，10次提交。 在pom.xml中引入日志依赖logback-classic 就能发现。
        //预期结果是一个数据库中，重复的执行insert，当这十条数据处理完后，把这个事务一提交，十条数据一次性写入，同时数据库连接释放掉
        employeeService.batchImport();
        System.out.println("批量导入成功");
    }
}
