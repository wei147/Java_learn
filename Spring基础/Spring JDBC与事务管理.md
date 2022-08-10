## Spring JDBC与事务管理



<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220803213804785.png" alt="image-20220803213804785" style="zoom:50%;" />

#### 介绍Spring JDBC

```html
<Spring JDBC>
Spring JDBC是Spring框架用于处理关系型数据库的模块
Spring JDBC对JDBC API进行封装，极大简化开发工作量
JdbcTemplate,是Spring JDBC核心类，提供数据CRUD方法
    
问题 : 有Mybatis为什么还需要Spring JDBC？
    答 : 两者面向的对象是不一样的。Mybatis作为一个orm框架，它封装的程度较高，适合于中小企业进行软件的敏捷开发，让程序员能快速的完成与数据库的交互工作，但是我们学过Mybatis都知道这里涉及到一系列的比如xml的配置以及各种各样的操作的细节，实际上封装的程度还是比较高的，封装程度高就意味着我们执行效率相对较低。 但是Spring JDBC只是对原始的JDBC的api进行的简单封装，对一线的互联网大厂无论是数据量还是用户的并发量都是非常高的，这时如果使用MyBatis可能因为微小的性能上的差距就会导致整体应用变慢，因此作为一线大厂很少使用市面上成熟的框架，更多时候喜欢使用像Spring JDBC这样轻量级的封装框架，在这个基础上结合自己企业的特点来进行二次的封装。可以说Spring JDBC它的执行效率比MyBatis是要高的，同时因为有了spring底层ioc容器的存在，也不至于让程序像原生jdbc那样难以管理。sping jdbc是介于orm框架和原生jdbc之间的一个折中的选择。
```

```
<Spring JDBC的使用步骤>
Maven工程引入依赖<spring-jdbc>
applicationContext.xml配置<DataSource>数据源		//哪种数据库  用户名和密码是什么
在Dao注入<JdbcTemplate>对象，实现数据CRUD（增删改查）
```



#### Spring JDBC配置过程

##### JdbcTemplate实现增删改查

```xml
//pom.xml 引入依赖    
<repositories>
        <repository>
            <id>aliun</id>
            <name>aliyun</name>
            <url>https://maven.aliyun.com/repository/public</url>
        </repository>
    </repositories>
    <dependencies>
        <dependency>
            <!--这是任何一个spring工程都需要引入的-->
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>5.2.20.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.2.20.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
    </dependencies>
```

```xml
//applicationContext.xml 
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">
    <!--spring jdbc的底层配置  数据源的设置-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url"
                  value="jdbc:mysql://localhost:3306/imooc-test?useUnicode=true&amp;characterEncoding=gbk&amp;autoReconnect=true&amp;failOverReadOnly=false"/>
        <property name="username" value="root"></property>
        <property name="password" value="1234"></property>
    </bean>
    <!--关键配置  JdbcTemplate提供数据CRUE的API jdbcTemplate-->
    <!--CURE  C(Create):建立 + R(Retrieve):查询 + U(Update):修改 + D(Delete):删除-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"></property>    <!--这里的ref指向的是上面的beanId-->
    </bean>

    <bean id="employeeDao" class="com.imooc.spring.jdbc.dao.EmployeeDao">
        <!--为Dao注入JdbcTempla对象 （只有注入以后具体的业务方法才可以去调用jdbc相应的api完成数据库的增删改查操作）-->
        <property name="jdbcTemplate" ref="jdbcTemplate"></property>
    </bean>
</beans>
```

```java
//employee.java 实体类
package com.imooc.spring.jdbc.entity;
import java.util.Date;
public class Employee {
    private Integer eno;
    private String ename;
    private Float salary;
    private String dname;
    private Date hiredate;  //入职时间

    public Integer getEno() { return eno;}

    public void setEno(Integer eno) {this.eno = eno;}

    public String getEname() {return ename;}

    public void setEname(String ename) {this.ename = ename;}

    public Float getSalary() { return salary;}

    public void setSalary(Float salary) {this.salary = salary;}

    public String getDname() {return dname;}

    public void setDname(String dname) {this.dname = dname;}

    public Date getHiredate() {return hiredate;}

    public void setHiredate(Date hiredate) {this.hiredate = hiredate;}

    //为了让程序更容易调试，这里重写toString 方法
    @Override
    public String toString() {
        return "Employee{" +
                "eno=" + eno +
                ", ename='" + ename + '\'' +
                ", salary=" + salary +
                ", dname='" + dname + '\'' +
                ", hiredate=" + hiredate +
                '}';}}
```

```java
//EmployeeDao.java
package com.imooc.spring.jdbc.dao;
import com.imooc.spring.jdbc.entity.Employee;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class EmployeeDao {
    //持有jdbcTemplate
    private JdbcTemplate jdbcTemplate;

    public Employee findById(Integer eno){
        //queryForObject的含义是进行指定的查询，将唯一返回的数据转成对应的对象
        String sql = "select * from employee where eno=?";
        //如何将指定的sql转成相应的对象? BeanPropertyRowMapper的含义是 将bean的属性和每一行的列（？）来进行一一的对应(需要先刻意的将属性名和字段名按驼峰规则保持一致),
        // 由这个对象(RowMapper)来完成从数据库记录到实体对象的转化，类似mybatis中将每一条记录转化为实体对象的过程
        Employee employee = jdbcTemplate.queryForObject(sql,new Object[]{eno},new BeanPropertyRowMapper<Employee>(Employee.class));
        return employee;}

    public JdbcTemplate getJdbcTemplate() {return jdbcTemplate;}

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate; }}
```

```java
//SpringApplication.java
public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        EmployeeDao employeeDao = context.getBean("employeeDao",EmployeeDao.class);
        Employee employee = employeeDao.findById(3308);
        System.out.println(employee);   //这里打印employee这个对象会直接调用toString() 方法
    }
}
```



#### JdbcTemplate的数据查询方法

```
spring-test和Junit可以很好的整合在一起，通过编写单元测试用例可以更灵活的编写JdbcTemplate各种使用办法
```

```java
//EmployeeDao.java
package com.imooc.spring.jdbc.dao;
import com.imooc.spring.jdbc.entity.Employee;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import java.util.Map;

public class EmployeeDao {
    //持有jdbcTemplate
    private JdbcTemplate jdbcTemplate;

    public Employee findById(Integer eno){
        //queryForObject的含义是进行指定的查询，将唯一返回的数据转成对应的对象  (查询单条数据)
        String sql = "select * from employee where eno=?";
        //如何将指定的sql转成相应的对象? BeanPropertyRowMapper的含义是 将bean的属性和每一行的列（？）来进行一一的对应(需要先刻意的将属性名和字段名按驼峰规则保持一致),
        // 由这个对象(RowMapper)来完成从数据库记录到实体对象的转化，类似mybatis中将每一条记录转化为实体对象的过程 （属性和字段间的彼此转换）
        Employee employee = jdbcTemplate.queryForObject(sql,new Object[]{eno},new BeanPropertyRowMapper<Employee>(Employee.class));
        return employee;
    }

    //查询多条/复合数据 （按照部门名称进行数据查询）
    public List<Employee> findByDname(String dname){
        String sql = "select * from employee where dname = ?";
        //jdbcTemplate.query()方法默认返回list集合  query(参数1 sql语句,参数2与sql语句对应的实际参数是什么, 参数3我们要将每一条记录转换为什么对象[期待转换为employee实体类])
        List<Employee> list = jdbcTemplate.query(sql, new Object[]{dname},new BeanPropertyRowMapper<Employee>(Employee.class));   //参数3 属性和字段间的彼此转换
        return list;}

    //测试: 当字段名和属性名不一样时会怎么样 （这里起了两个别名，和属性名就对不上了）模拟无法进行有效的实体属性映射
    public List<Map<String, Object>> findMapByDname(String dname){
        String sql = "select eno as empno, salary as s from employee where dname = ?";
        //将查询结果作为列表返回同时默认情况下将每一条数据按Map对象来进行包裹(将查询结果作为Map进行封装)  Map<String, Object>中的key是原始字段名，value则是字段名所对应的数值
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{dname});
        return maps;}

    public JdbcTemplate getJdbcTemplate() {return jdbcTemplate;}

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;}}
```

```java
// JdbcTemplateTester.java 测试类
import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

//测试用例类
@RunWith(SpringJUnit4ClassRunner.class) //将junit的控制权交给spring来进行。在Junit启动的时候自动初始化ioc容器
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
//指定加载文件  (这两句话等同于  ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");)
public class JdbcTemplateTester {
    //注入对应的类
    @Resource
    private EmployeeDao employeeDao;

    //第一个测试用例
    @Test
    public void testFindById() {
        Employee employee = employeeDao.findById(3308);
        System.out.println(employee);} //Employee{eno=3308, ename='张三', salary=6000.0, dname='研发部', hiredate=2011-05-08 00:00:00.0}

    @Test
    public void testFindByDname(){
        List<Employee> employees = employeeDao.findByDname("市场部");
        System.out.println(employees);	//[Employee{eno=3610, ename='王五', salary=4550.0, dname='市场部', hiredate=2009-10-01 00:00:00.0}]
        System.out.println("==============================");
        for (Employee employee : employees){
            System.out.println(employee);}}

    @Test
    public void findMapByDname(){
        System.out.println(employeeDao.findMapByDname("研发部")); //[{empno=3308, s=6000.0}, {empno=3420, s=8700.0}]}}
```

```
 String sql = "select eno as empno, salary as s from employee where dname = ?";
疑问 ：为什么只有jdbcTemplate.queryForList() 方法能查询到改了别名的mysql字段？
2022年8月5日01:29:03
```



#### JdbcTemplate数据写入方法

##### JdbcTemplate实现增删改查	crud 

```java
//EmployeeDao.java
//写入操作  新增操作
public void insert(Employee employee){
    String sql = "insert into employee(eno,ename,salary,dname,hiredate) value(?,?,?,?,?)";
    //update()方法泛指所有写入操作（不只是更新操作）
    jdbcTemplate.update(sql,new Object[]{
        employee.getEno(),employee.getEname(),employee.getSalary(),employee.getDname(),employee.getHiredate()
    });}

//更新内容操作 修改操作
public int update(Employee employee){
    String sql = "update employee set ename=?, salary=?,dname=?,hiredate=? where eno=?";
    //int count 代表本次新增、修改或者删除操作所真实影响的数据条目
    int count =  jdbcTemplate.update(sql, new Object[]{
           employee.getEname(),employee.getSalary(),employee.getDname(), employee.getHiredate(), employee.getEno()
    });
    return count;
}

//删除操作
public int delete(Integer eno){
    String sql = "delete from employee where eno=?";
    int count = jdbcTemplate.update(sql,new Object[]{eno});
    return count;}
```

```java
//JdbcTemplateTester.java 测试类
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
```

疑问：数据库的事务如何控制/管理？暂时没有体现到   下一节讲解编程式事务



#### 编程式事务

```html
Spring中如何进行事务管理？

<什么是事务？>
事务是以一种可靠的、一致的方式，访问和操作数据库的程序单元
说人话：要么把事情做完，要么什么都不做，不要做一半  (commit?rollback)
事务依赖于数据库实现，MySQL通过<事务区>作为数据缓冲地带	(mysql通过引入事务区的概念，将当前事务数据进行缓存，当所有操作完成后，提交。就可以将事务区中的数据一次性写入到真实的表中，如果出现问题则将事务区中的数据全部进行回滚，这样就相当于什么都没有做)

对于Spring jdbc这个模块来说，如何进行事务控制？
答：编程式事务

<编程式事务>
编程式事务是指通过代码手动提交回滚事务的事务控制方法
SpringJDBC通过TransactionManager事务管理器实现事务控制
事务管理器提供commit/rollback方法进行事务提交与回滚

    
需求：向表中导入10条数据，要么全部导入成功，要么什么都不做
    
    service 业务逻辑
    
在Spring中使用事务的方式有两种： 编程式事务 和 声明式事务
    
    编程式事务优点：直接写在代码中，对于人眼阅读来说非常好
    编程式事务缺点：可能存在人为风险（忘记加事务控制了，导致数据缺失没有提交到）
    
    下一节由Spring提供的更高级的做法————声明式事务
    
```

```xml
//配置文件   
<bean id="employeeService" class="com.imooc.spring.jdbc.service.EmployeeService">
        <property name="employeeDao" ref="employeeDao"></property>
        <!--关联到事务管理器-->
        <property name="transactionManager" ref="transactionManager"/>
    </bean>

    <!--事务管理器  （控制事务的整体提交和整体回滚）-->
    <!--怎么使用?  在需要事务控制的类里，去注入它 比如EmployeeService中-->
    <bean id="transactionManager"
          class="org.springframework.jdbc.datasource.DataSourceTransactionManager">     <!--基于数据源的事务管理器-->
        <property name="dataSource" ref="dataSource"/>
    </bean>
```

```java
//EmployeeService.java  业务逻辑
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
        transactionManager.commit(status);}

    public EmployeeDao getEmployeeDao() {
        return employeeDao;}

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;}

    public DataSourceTransactionManager getTransactionManager() {return transactionManager;}

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {this.transactionManager = transactionManager;}}
```

```java
//测试类
//注入对应的类
@Resource
private EmployeeDao employeeDao;
//注入对应的类
@Resource
private EmployeeService employeeService;
@Test
public void testBatchImport(){
    //这里不是由一个事务提交的，而是分为10个，创建了10个数据库连接，10次插入，10次提交。 在pom.xml中引入日志依赖logback-classic 就能发现。
    //预期结果是一个数据库中，重复的执行insert，当这十条数据处理完后，把这个事务一提交，十条数据一次性写入，同时数据库连接释放掉
    employeeService.batchImport();
    System.out.println("批量导入成功");}
```



#### 声明式事务配置

```
声明式事务
声明式事务指在不修改源码情况下通过配置形式自动实现事务控制，声明式事务本质就是AOP环绕通知
													<两种触发时机>
当目标方法执行成功时，自动提交事务
当目标方法抛出运行时异常时，自动事务回滚
（以上两种是环绕通知的一种典型形式）
(作为声明式事务就是通过aop的特性来完成应用程序的扩展，让我们在不修改源代码的情况下自动实现事务的控制)
```

```
<配置过程>
配置TransactionManager事务管理器	（事务管理器用于提交和回滚事务）
配置事务通知与事务属性		（在平时进行方法调用时，有些方法需要使用事务，有些查询方法不需要。对于不同情况进行不同的配置）
为事务绑定PointCut切点  （PointCut用于说明在在哪些类的哪些方法上来应用通知，限定事务通知的执行范围）
```

```xml
xmlns:tx="http://www.springframework.org/schema/tx"		这个命名空间专用于事务控制
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/tx
        https://www.springframework.org/schema/tx/spring-tx.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">
    <!-- 数据源 -->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url"
                  value="jdbc:mysql://localhost:3306/imooc-test?useUnicode=true&amp;useSSL=false&amp;characterEncoding=gbk&amp;autoReconnect=true&amp;failOverReadOnly=false"/>
        <property name="username" value="root"></property>
        <property name="password" value="1234"></property>
    </bean>
    <!--JdbcTemplate提供数据CRUD的API-->
    <bean id="jdbcTemplate" class="org.springframework.jdbc.core.JdbcTemplate">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <bean id="employeeDao" class="com.imooc.spring.jdbc.dao.EmployeeDao">
        <!--为Dao注入JdbcTemplate对象-->
        <property name="jdbcTemplate" ref="jdbcTemplate"/>
    </bean>

    <bean id="employeeService" class="com.imooc.spring.jdbc.service.EmployeeService">
        <property name="employeeDao" ref="employeeDao"/>
    </bean>

    <!--声明式事务-->
    <!--1.事务管理器，用于创建事务/提交/回滚-->
    <bean id="transactionManger" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">  <!--事务管理器-->
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--2.事务通知配置，决定哪些方法使用事务，哪些方法不使用事务-->
    <tx:advice id="txAdvice" transaction-manager="transactionManger">
        <tx:attributes>
            <!--目标方法名为batchImport时，启用声明式事务，成功提交，运行时异常回滚-->
            <!-- [次要] propagation:设置事务的传播行为  基本上设置为propagation="REQUIRED" 需要使用事务 required必须的-->
            <tx:method name="batchImport" propagation="REQUIRED"/>
            <tx:method name="bath*" propagation="REQUIRED"/>   <!--支持通配符的形式进行匹配-->
            <!--设置所有findXXX方法不需要使用事务-->
            <tx:method name="find*" propagation="NOT_SUPPORTED" read-only="true"/>
            <tx:method name="get*" propagation="NOT_SUPPORTED" read-only="true"/>
            <!--不符合上面要求的其他方法都默认使用事务-->
            <tx:method name="*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>

    <!--定义声明式事务的作用范围-->
    <aop:config>
        <aop:pointcut id="pointcut" expression="execution(* com.imooc..*Service.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="pointcut"/>
    </aop:config>
</beans>
```

```java
//EmployeeService.java  由方法自己决定rollback还是commit，不需要自己写判断和捕获异常
package com.imooc.spring.jdbc.service;
import com.imooc.spring.jdbc.dao.EmployeeDao;
import com.imooc.spring.jdbc.entity.Employee;
import java.util.Date;

public class EmployeeService {
    private EmployeeDao employeeDao;

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
            employeeDao.insert(employee);}}

    public EmployeeDao getEmployeeDao() {return employeeDao;}

    public void setEmployeeDao(EmployeeDao employeeDao) {this.employeeDao = employeeDao;}}
```



#### 事务传播行为

##### 事务传播行为是指多个拥有事务的方法在嵌套调用时的事务控制方式 

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220810134403592.png" alt="image-20220810134403592" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220810142426092.png" alt="image-20220810142426092" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220810143112671.png" alt="image-20220810143112671" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220810144232219.png" alt="image-20220810144232219" style="zoom:50%;" />

```java
//EmployeeService.java
//事务的嵌套使用  startImportJob默认也是开启声明式事务的               <tx:method name="*" propagation="REQUIRED"/>
    public void startImportJob() {  //嵌套使用时，importJob1和importJob2发现外侧有现成的事务了，运行时这两个job会加入到该外侧事务中，运行时该方法时整体是在一个事务中完成增删改查。这就有个问题：当startImportJob（）方法内发生错误时，它会回滚整个事务，就是说该事务内正常的方法也会被牵扯到	那么怎么让嵌套的方法独立呢？不受外侧事务的影响独立起来		答：让xml中的 propagation="REQUIRES_NEW" 意思是 针对这个方法在运行时会产生新的事务进行处理
        batchService.importJob1();
        //这个判断会影响到startImportJob() 一条数据都没有加入。 本来Job1和Job2应该是两个互不相关的方法，Job2执行不了Job2也应该执行才对 怎么实现？ 事务传播行为的设置
        if(1==1){
            throw new RuntimeException("意料之外的异常");
        }
        batchService.importJob2();}
```

```
PROPAGATION REQUIRED(默认)	如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。这是最常见的选择
PROPAGATION SUPPORTS	在非事务执行方式中，遇到每一次的新增修改删除它马上就会提交，所以采用非事务方式它无法保障数据的完整性
PROPAGATION MANDATORY	使用当前的事务，如果当前没有事务，就抛出异常
PROPAGATION REQUIRES NEW	新建事务，如果当前存在事务，把当前事务挂起
PROPAGATION NOT SUPPORTED	以非事务方式执行操作，如果当前存在事务，就把当前事务挂起（用在查询方法上）
PROPAGATION NEVER		以非事务方式执行，如果当前存在事务，则抛出异常（几乎不用）
PROPAGATION NESTED	如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION REQUIRED类似的操作






```

