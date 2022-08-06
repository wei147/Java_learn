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

疑问：数据库的事务如何控制？没有体现到   下一节讲解编程式事务



#### 编程式事务

