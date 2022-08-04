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