## 第26周 SSM开发社交网站

#### 周课介绍

```
已经学习了Mybatis、Spring、Spring MVC这些工作中必须掌握的框架技术，但是这里你可能会有一个问题 : 作为这些框架如何在真实的项目中去使用？ 本周开始将结合实战，通过慕课书评网这一个小项目带着大家如何利用已经学习的这些知识来开发一个现代互联网应用
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828180601833.png" alt="image-20220828180601833" style="zoom:50%;" />

```
作为慕课书评网，会从数据库建表、到创建创建工程、再到环境搭建、再到编码实现这个完整的流程。
讲解前台页面的实现以及后台数据管理功能。
可以了解到一个工程师真正接到一个任务的时候到底是从哪个角度入手，如何一步一步完成我们的开发工作。
对于慕课书评网是真正意义上的与实际工作最贴近的第一个项目，Fighting ！ 2022年8月28日18:12:00
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828181239006.png" alt="image-20220828181239006" style="zoom:50%;" />



#### 课程介绍与项目演示

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828181545408.png" alt="image-20220828181545408" style="zoom:50%;" />

```
!!! 除了以上知识点以外，还通过辅助材料的形式提供了 如何基于阿里云来实现短信认证以及如何基于腾讯云的滑块验证码来实现前置的人机登录检查。
```



#### SSM整合的意义

##### SSM整合配置

```
SSM整合的意义：因为每一个项目都是多个公司多个产品的组合使用。如果我们没有把它们形成一个整体，而是让每一个技术都是一个技术孤岛的话，从维护的角度非常困难的，因为每一个框架都有自己的配置文件和一系列的配置信息....

Spring/Spring MVC/MyBatis是业内最主流的框架搭配
why?
首先Spring MVC它提供了我们的控制器，也就是基于Spring的Web交互的能力，对我们开发Web应用做好了基础的准备。而最底层的Spring 这个框架它本意是对应用程序的对象来进行创建和管理，其他所有框架的核心对象都是由Spring来进行统筹协调的。而最后的MyBatis则是完成了与底层数据库增删改查的操作。从客户端所发来的请求，经由Spring MVC进行接收，然后再由Spring负责创建，最后将业务产生的数据再通过MyBatis向数据库进行写入或者查询。那么整体就形成了一个从web应用到底层数据库读写的完整框架组合
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828184206282.png" alt="image-20220828184206282" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828184511499.png" alt="image-20220828184511499" style="zoom:50%;" />



#### Spring与Spring MVC环境配置

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828212137958.png" alt="image-20220828212137958" style="zoom:50%;" />



```xml
//pom.xml
    <groupId>com.imooc</groupId>
    <artifactId>imooc-reader</artifactId>
    <version>1.0-SNAPSHOT</version>

    <repositories>
        <repository>
            <id>aliun</id>
            <name>aliyun</name>
            <url>https://maven.aliyun.com/repository/public</url>
        </repository>
    </repositories>

    <dependencies>
        <!--1.Spring MVC 会自动引入Spring基础依赖-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.2.0.RELEASE</version>
        </dependency>

        <!--Freemarker-->
        <dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
            <version>2.3.30</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>5.2.20.RELEASE</version>
        </dependency>

        <!--Jackson-->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-core</artifactId>
            <version>2.12.0</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-annotations</artifactId>
            <version>2.12.0</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.12.0</version>
        </dependency>
    </dependencies>
```

```xml
//applicationContext.xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:task="http://www.springframework.org/schema/task"
       xsi:schemaLocation="
            http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context
            http://www.springframework.org/schema/context/spring-context.xsd
            http://www.springframework.org/schema/task
            http://www.springframework.org/schema/task/spring-task.xsd
            http://www.springframework.org/schema/mvc
            http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!--3.开启Spring MVC注解模式  [注: 这里不是SpringMVC开启注解模式,而是Spring框架来启用注解模式,从而在容器初始化时实例像 servlet、controller这样]-->
    <context:component-scan base-package="com.imooc"/>
    <!--真正开启mvc注解  SpringMVC独有的注解才能被识别 像@GetMapping-->
    <mvc:annotation-driven>
        <!--响应中的中文乱码问题解决  消息转换器-->
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <property name="supportedMediaTypes">
                    <list>
                        <value>text/html;charset=utf-8</value>
                        <!--6.JSON（Jackson）序列化输出配置    [对json格式的字符串进行相应的编码转换]-->
                        <value>application/json;charset=utf-8</value>
                    </list>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
    <!--将静态资源排除在外(css,js,img)，进而提高SpringMVC的处理效率-->
    <mvc:default-servlet-handler/>

    <!--5.配置Freemarker模板引擎-->
    <bean id="freemarkerConfig" class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
        <property name="templateLoaderPath" value="/WEB-INF/ftl"></property>
        <!--Freemarker本身的配置-->
        <property name="freemarkerSettings">
            <props>
                <!--freemarker采用utf-8编码读取ftl文件-->
                <prop key="defaultEncoding">UTF-8</prop>
            </props>
        </property>
    </bean>
    <!--视图解析器决定了到底使用哪种模板引擎来对数据进行解析-->
    <bean id="ViewResolver" class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
        <!--在视图解析器将数据和模板引擎结合，所产生的新的html片段，那么向响应输出时设置 contentType内容类型为 value的值（utf-8）-->
        <property name="contentType" value="text/html;charset=utf-8"/>
        <!--要加载模板引擎拓展名是什么-->
        <property name="suffix" value=".ftl"/>
    </bean>
</beans>
```

```java
//用于测试的控制器类
package com.imooc.reader.test;

import com.imooc.reader.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyTestController {
    @GetMapping("/t")
    @ResponseBody
    public String test(){
        return "[I am 17]";}

    @ResponseBody
    @GetMapping("/user")
    public User jsonTest(){
        User user = new User();
        user.setName("神里绫华");
        user.setAge(20);
	return user;}

    @GetMapping("/t1")
    public ModelAndView ftlTest(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/test");
        return mav;}}
```



#### Spring与MyBatis整合配置

```
什么是整合呢？

加入了mybatis-spring，把程序创建的权利交给了spring ioc容器这让我们系统中分散的每一个技术形成了一个有机的整体，让我们程序更容易被维护、更容易被管理。
所谓整合其核心的目的就是 通过Spring 去管理其他组件或者框架的核心对象，让这些组件之间形成一个整体
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220830121441118.png" alt="image-20220830121441118" style="zoom:50%;" />

#### Spring与MyBatis整合过程

```xml
//pom.xml
<!--MyBatis整合步骤: 1.引入依赖-->
<dependency>
    <!--为什么要引入Spring jdbc ? 因为MyBatis底层操作数据库需要依赖于jdbc; 而Spring对于jdbc
    spring jdbc进行了封装和扩展。所以这是我们整合过程中需要引入的-->
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.2.20.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis</artifactId>
    <version>3.5.3</version>
</dependency>
<!--Mybatis与Spring整合组件-->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.6</version>
</dependency>

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.16</version>
</dependency>
<!--几乎在某一个商业应用都会用到的连接池 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.14</version>
</dependency>
```

```
快捷键 Ctrl Shift n 快速搜索指定文件
```

```xml
//applicationContext.xml
<!--Mybatis与Spring的整合配置-->
<!--配置数据源-->
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url"
              value="jdbc:mysql://localhost:3306/imooc_reader??useSSL=false&amp;useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=Asia/Shanghai&amp;allowPublicKeyRetrieval=true"/>
    <property name="username" value="root"/>
    <property name="password" value="1234"/>
    <!--代表了数据库连接池初始化的时候要创建多少个连接。 maxActive 代表数据库连接池中最大容许出现的数据库连接的总量-->
    <property name="initialSize" value="5"/>
    <property name="maxActive" value="20"/>
</bean>

<!--SqlSessionFactoryBean用于根据配置信息创建SqlSessionFactory, 不再需要我们自己编码创建-->
<bean id="sessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <!--代表用于存储Sql语句的mapper xml文件存储在哪个目录中-->
    <property name="mapperLocations" value="classpath:mappers/*.xml"/>
    <!--5.MyBatis配置文件地址-->
    <property name="configLocation" value="classpath:mybatis-config.xml"/>

</bean>
<!--4.配置Mapper扫描器。 [注：就是在Spring 初始化MyBatis过程中，之前我们通过接口方式来进行MyBatis数据库增删改查，
那么这个MyBatis扫描器就是用于扫描这些MyBatis的Mapper接口的]。
针对于这个接口，我们就称之为 Mapper接口。它的职责就是完成与之对于的数据表增删改查操作。
但是这个接口又有一个新问题: 系统中的接口有很多,并不是所有的接口都是相应的Mapper接口。
为了将这些特殊的Mapper和其他系统的标准接口区分开, 所以我们要进行mapper扫描器的配置-->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="basePackage" value="com.imooc.reader.mapper"></property>
</bean>
```

```xml
//mybatis-config.xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <!--来让MyBatis实现属性名与字段名之间的驼峰命名转换-->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
</configuration>
```

```java
//配置文件中Mapper扫描器的说明
package com.imooc.reader.mapper;
//针对于这个接口，我们就称之为 Mapper接口。它的职责就是完成与之对于的数据表增删改查操作。
//但是这个接口又有一个新问题: 系统中的接口有很多,并不是所有的接口都是相应的Mapper接口。
// 为了将这些特殊的Mapper和其他系统的标准接口区分开, 所以我们要进行mapper扫描器的配置

public interface TestMapper {
    public void insert();}
```



#### 整合其他组件

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220830144855705.png" alt="image-20220830144855705" style="zoom:50%;" />

```xml
        <!--单元测试依赖-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
            <version>5.2.20.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <!--Servlet  (在一开始运行时会报错，其中有一个是说 Caused by: java.lang.ClassNotFoundException:
        javax.servlet.ServletContext。所以加入这个Servlet依赖 )-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
            <scope>provided</scope>
        </dependency>    
<!--logback日志组件-->
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.3</version>
    </dependency>
</dependencies>
```

```java
//声明式事务的使用和测试
package com.imooc.reader.service;
import com.imooc.reader.mapper.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service
public class TestService {
    //运行时注入 TestMapper这个对象
    @Resource
    private TestMapper testMapper;

    //作为当前的事务控制注解,它的运行条件是当我们方法执行成功则进行全局提交;如果方法抛出了运行时异常则进行全局的回滚。
    // 通过这个注解可以做到要么数据全部完成,要么数据什么都不做
    @Transactional
    public void batchImport() {
        for (int i = 0; i < 5; i++) {
//            if (i == 3) {
//                throw new RuntimeException("预期之外的异常");
//            }
            testMapper.insert();}}}
```

```xml
//test.xml	用于测试的MyBatis配置文件，会被Mapper扫描器扫描到，指向TestMapper接口
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.imooc.reader.mapper.TestMapper">
    <insert id="insertSample">
        insert into test(content) values('测试内容')
    </insert>
</mapper>
```

```java
//测试类 
package com.imooc.reader.service;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)//junit4在运行时，会自动初始化ioc容器
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) //说明配置文件在什么地方
public class TestServiceTest extends TestCase {
    @Resource
    private TestService testService;

    @Test
    public void testBatchImport() {
        testService.batchImport();
        System.out.println("批量导入成功");}}
```

```xml
logback的配置文件  logback.xml
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <!--追加器  向控制器进行输出  [class是具体追加器的实现类]-->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <!--编码器，用于说明向控制台输出的日志格式是什么 [PatternLayoutEncoder自定义日志输出格式]-->
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!--%d对应时间  %-5level:将日志级别进行打印输出  %thread:当前线程的名字
            %logger:代表日志中说明是哪个类的哪个方法所产生的日志  %msg%n:对应的日志信息和换行-->
            <pattern>%d{HH:mm:ss} %-5level [%thread] %logger{30} - %msg%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!--日志的最低输出级别为 debug-->
    <root level="debug">
        <appender-ref ref="console"/>
    </root>
</configuration>
```

```xml
applicationContext.xml中关于声明式事务的配置，以及完整体的schema（最后进入了tx命名空间）
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:task="http://www.springframework.org/schema/task"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="
            http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context
            http://www.springframework.org/schema/context/spring-context.xsd
            http://www.springframework.org/schema/task
            http://www.springframework.org/schema/task/spring-task.xsd
            http://www.springframework.org/schema/mvc
            http://www.springframework.org/schema/mvc/spring-mvc.xsd
            http://www.springframework.org/schema/tx
            http://www.springframework.org/schema/tx/spring-tx.xsd">
<!--声明式事务配置-->
<!--基于数据源的事务管理器,用于用于控制我们事务的打开、提交或者回滚-->
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource"/>
</bean>
<!--启用与之对应的注解模式-->
<tx:annotation-driven transaction-manager="transactionManager"/>
```

```java
关于声明式事务：
    public void  batchImport(){
        for(int i = 0;i<5;i++){
            if (i==3){
                throw new RuntimeException("预期之外的异常");
            }
            testMapper.insert();}}
当出现异常时，只插入了前三条数据，后面两条数据没有写入。这代表破坏了事务的完整性,解决方案的配置具体见applicationContext.xml文件
    
    Spring-tx 会在导入Spring-jdbc的时候自动也被引入。
```

```
至此，Spring、Spring MVC以及MyBatis三个框架的配置和整合的工作告一段落。现在我们已经搭建起来一个底层的开发环境
```



#### MyBatis-Plus介绍与整合步骤

##### MyBatis-Plus

```xml
MyBatis-Plus是基于MyBatis基础上的一个敏捷开发插件。它的作用是帮助我们快速的完成对应数据表的增删改查操作

<国人开发的>	https://baomidou.com/
    
为什么要使用MyBatis-plus?
    简化开发,只做增强不做修改
    

    快捷键 Ctrl+Alt+V 查看返回值类型
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220830174250834.png" alt="image-20220830174250834" style="zoom: 50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220830174727183.png" alt="image-20220830174727183" style="zoom:50%;" />

```xml
在applicationContext.xml中的修改配置
<!--SqlSessionFactoryBean用于根据配置信息创建SqlSessionFactory, 不再需要我们自己编码创建-->
<!--原生MyBatis与Spring整合  <bean id="sessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">-->
<bean id="sessionFactory" class="com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <!--代表用于存储Sql语句的mapper xml文件存储在哪个目录中-->
    <property name="mapperLocations" value="classpath:mappers/*.xml"/>
    <!--5.MyBatis配置文件地址-->
    <property name="configLocation" value="classpath:mybatis-config.xml"/>
```

```xml
在mybatis-config.xml中配置    
    <plugins>
        <!--配置MyBatis-Plus的分页插件  MyBatis-plus 3.5版本有些小变动 应该是-->
        <!--好了，我要换回3.3.2版本,3.5版本有个报错后面看看再解决-->
        <plugin interceptor="com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor">
            <!--        <plugin interceptor="com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor">-->
            <!--            <property name="@page" value="com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor"/>-->
            <!--            <property name="page:dbType" value="h2"/>-->
        </plugin>
    </plugins>
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220830180932001.png" alt="image-20220830180932001" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220830181110815.png" alt="image-20220830181110815" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220830181138557.png" alt="image-20220830181138557" style="zoom:50%;" />



#### MyBatis-Plus实现数据CRUD

```java
//Test.java MyBatis-plus中实体类中的描述	
package com.imooc.reader.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("test")  //说明实体类对应哪一张表
public class Test {
    @TableId(type = IdType.AUTO)     //说明表的主键,type设置为自动增长
    @TableField("id")   //说明属性对应哪个字段
    private Integer id;

    //如果字段名与属性名相同或者符合驼峰命名转换规则,则TableField可省略    textContent ——> test_content（数据库字段名）
    @TableField("content")
    private String content;

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getContent() {return content;}
    public void setContent(String content) {this.content = content;}}
```

```java
//原先的接口继承自Mybatis-Plus的 BaseMapper<T>，自动就有了增删改查功能
package com.imooc.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.reader.entity.Test;

public interface TestMapper extends BaseMapper<Test> {
    public void insertSample();}
```

```java
//测试类,测试MyBatis-Plus带来的CRUD功能。以及查询条件构造器，这个感觉也还行
package com.imooc.reader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.mapper.TestMapper;
import com.imooc.reader.service.TestService;
//import org.junit.Test;
import com.imooc.reader.entity.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)//junit4在运行时，会自动初始化ioc容器
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) //说明配置文件在什么地方
public class MyBatisPlusTest {
    @Resource
    private TestMapper testMapper;

    @org.junit.Test
    public void testInsert() {
        Test test = new Test();
        test.setContent("MyBatisPlus 测试");
        testMapper.insert(test);}

    @org.junit.Test
    public void testUpdate() {
        Test test = testMapper.selectById(32);
        test.setContent("我被修改了,MyBatisPlus 测试1");
        testMapper.updateById(test);}

    @org.junit.Test
    public void testDelete() {testMapper.deleteById(27);}

    @org.junit.Test
    public void testSelect() {
        //查询多条查询数据。需要传入一个查询条件构造器，它的作用就是帮我们组织查询时的筛选条件
        QueryWrapper<Test> queryWrapper = new QueryWrapper<Test>();
        queryWrapper.eq("id",32); //查询等于32
        queryWrapper.gt("id",35);   //查询所有id大于35    Preparing: SELECT id,content FROM test WHERE (id > ?)
        //默认情况下,写了多个子句的话，会通过and进行连接,并将值按照前后顺序进行传入     Preparing: SELECT id,content FROM test WHERE (id = ? AND id > ?)
        List<Test> testList = testMapper.selectList(queryWrapper);
        System.out.println(testList);
//        System.out.println(testList.get(0));}}
```



#### 案例分析与数据库建表

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220830205038644.png" alt="image-20220830205038644" style="zoom:50%;" />

```
数据表的重要程度:
book、member、evaluation(评价)、category(分类)、member_read_state(会员阅读状态)、user
```



#### Bootstrap入门介绍



```
卸载5.7 安装8.0
C:\Users\w1216\Documents\Navicat	已压缩的mysql数据
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220831163356317.png" alt="image-20220831163356317" style="zoom:50%;" />



#### 显示图书类别

```
1.category表对应了实体类中的Category
2.创建与之对应的Mapper接口(继承自MyBatis-Plus的BaseMapper)
3.有了接口就要有与之对应的xml （resources/mappers下的category.xml）

以上,分类表的配置完成
```

```java
//1.category表对应了实体类中的Category
package com.imooc.reader.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

//图书分类实体
@TableName("category")
public class Category {
    @TableId(type = IdType.AUTO)
    private Long categoryId;

//    @TableField("category_name")  这里可以不加,符合驼峰命名转换的要求
    private String categoryName;

    public Long getCategoryId() {return categoryId;}
    public void setCategoryId(Long categoryId) {this.categoryId = categoryId;}
    public String getCategoryName() {return categoryName;}
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;}
	//为看起来更清晰，要重写实体类的toString方法
        @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +", categoryName='" + categoryName + '\'' +'}';}}
```

```java
//2.创建与之对应的Mapper接口(继承自MyBatis-Plus的BaseMapper)
package com.imooc.reader.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.reader.entity.Category;

//图书分类Mapper接口
public interface CategoryMapper extends BaseMapper<Category> {
}
```

```xml
//3.有了接口就要有与之对应的xml （resources/mappers下的category.xml）
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.imooc.reader.mapper.CategoryMapper">
</mapper>
```

```
自从学习了Spring 以后,一直在强调接口的重要性————"面向接口编程",通过接口可以隐藏很多底层类的创建细节。所以在我们实际开发时,很多实际项目在具体的实现类基础上还要额外的去定义一个接口		创建CategoryService接口
此时,有了Service接口,那就要有Service的实现	创建impl/CategoryServiceImpl.java  (impl是具体实现类的存放包)

```

```java
//具体的实现类
package com.imooc.reader.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Category;
import com.imooc.reader.mapper.CategoryMapper;
import com.imooc.reader.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;

//作为beanId通常是和接口保持一致的。why? 因为在具体使用时引入的是接口,而生成的属性是categoryService,保持属性和beanId一致是一个基本的规则。详情见CategoryServiceImplTest引入
@Service("categoryService")
//事务控制的注解。[事务的传播方式]意味着在当前方法中默认情况下所有方法是不需要使用事务的 (如果遇到了某个方法需要写操作的话,那需要额外的在方法上增加@Transactional来开启事务)
@Transactional(propagation = Propagation.NOT_SUPPORTED,readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 查询所有图书分类
     * @return  图书分类List
     */
    @Override
    public List<Category> selectAll() {
        //创建了一个全新的对象,里边没有设置任何条件就意味着查询所有
        List<Category> list = categoryMapper.selectList(new QueryWrapper<Category>());
        return list;}}
```

```java
//测试类
package com.imooc.reader.impl;
import com.imooc.reader.entity.Category;
import com.imooc.reader.service.CategoryService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)//junit4在运行时，会自动初始化ioc容器
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) //说明配置文件在什么地方
public class CategoryServiceImplTest extends TestCase {
    @Resource
    private CategoryService categoryService;

    @Test
    public void testSelectAll() {
        List<Category> list = categoryService.selectAll();
        System.out.println(list); }}
```

```
至此Service最关键的工作就写好了,下面进入Controller开发环节。作为Controller控制器它是整个系统中承上启下的组件。新建class controller/BookController 在这里完成一系列的url与方法之间的绑定
```

```java
//控制器
package com.imooc.reader.controller;
import com.imooc.reader.entity.Category;
import com.imooc.reader.impl.CategoryServiceImpl;
import com.imooc.reader.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;

    /**
     * 显示首页
     * @return
     */
    @GetMapping("/")
    public ModelAndView showIndex() {
        List<Category> categoryList = categoryService.selectAll();
        ModelAndView mav = new ModelAndView("/index");
        mav.addObject("categoryList", categoryList);
        return mav;}}
```

```html
//在index.ftl中用Freemarker的语法把Controller传过来的数据进行遍历并显示并且展示怎么去除最后一个竖杠的问题	酷 !!!
        <div class="col-8 mt-2">
            <span data-category="-1" style="cursor: pointer" class="highlight  font-weight-bold category">全部</span>
            |
            <#list categoryList as category>
                <a style="cursor: pointer" data-category="#{category.categoryId}"
                   class="text-black-50 font-weight-bold category">${category.categoryName}</a>
                <#--category_has_next 代表是否后面还有其他元素呢? 如果有则执行if块中的语句;如果没有就不会执行   [注:用于去除最后面还有 | 的情况,不够美观]-->
                <#if category_has_next>|</#if>
            </#list>
        </div>
```

```
这一节完成了一个非常具体的功能 : 从底层的数据库将数据查询出来并一步一步地填充到前端的展示页面上。其实这个路径也是我们程序员在日常工作时一个标准的套路
1.肯定是要先开发实体类
2.然后开发Mapper接口以及它所对应的xml
3.以上都准备好,那么就可以向上推进到了Service上,作为Service根据实际业务的需要来决定查询还是写入
4.那么Service写好,再往上推进,推到Controller上,来完成对Service的调用,调用成功之后将查询出来的结果放入到当前的请求中与模板引擎进行组合。模板引擎去读取指定的数据,完成界面的渲染产生html,于是就看到了我们最终的结果
```



#### 实现图书分页查询

```
分页查询来说 是我们日常非常普遍的一个应用场景。该如何实现 ? 
答: 作为MyBatis-Plus内置了分页插件,可以大幅度的简化我们对分页的处理
```

```java
分页查询离不开Book表,在entity中创建实体类Book,然后是BookMapper.java与其对应的book.xml,接着是Service最后是实现类Impl。重点是MyBatis-Plus提供的分页组件(Ipage以及selectPage方法)

    public IPage<Book> paging(Integer page, Integer rows) {
        Page<Book> p = new Page<Book>(page, rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
        //还没有任何需要筛选的条件,所以直接将queryWrapper对象放入到第二个参数。相当于对原始的所有数据进行分页查询了
        IPage<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        return pageObject;}}

生成对应实现类的小技巧 : 选中BookService类名,在上面按 alt + 回车 (无法复现,可能是我修改过了)

idea显示方法参数提示 Ctrl + p
```

```java
//实体类
package com.imooc.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("book")
public class Book {
    @TableId(type = IdType.AUTO)
    private Long bookId;
    private String bookName;
    private String subTitle;
    private String author;
    private String cover;
    private String description;
    private Long categoryId;
    private Float evaluationScore;
    private Long evaluationQuantity;

    public Long getBookId() {return bookId;}
    public void setBookId(Long bookId) {this.bookId = bookId;}
    public String getBookName() { return bookName;}
    public void setBookName(String bookName) {this.bookName = bookName;}
    public String getSubTitle() {return subTitle;}
    public void setSubTitle(String subTitle) {this.subTitle = subTitle;}
    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}
    public String getCover() {return cover;}
    public void setCover(String cover) {this.cover = cover;}
    public String getDescription() {return description;}
    public void setDescription(String description) { this.description = description;}
    public Long getCategoryId() {return categoryId}
    public void setCategoryId(Long categoryId) {this.categoryId = categoryId;}
    public Float getEvaluationScore() {return evaluationScore;}
    public void setEvaluationScore(Float evaluationScore) {this.evaluationScore = evaluationScore;}
    public Long getEvaluationQuantity() {return evaluationQuantity;}
    public void setEvaluationQuantity(Long evaluationQuantity) {this.evaluationQuantity = evaluationQuantity;}}
```

```java
//Mapper 接口类
package com.imooc.reader.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.reader.entity.Book;

public interface BookMapper extends BaseMapper<Book> {}
```

```xml
//mappers文件夹下的book.xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.imooc.reader.mapper.BookMapper">

</mapper>
```



```java
//BookService.java
package com.imooc.reader.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;

/**
 * 图书服务
 * IPage是由MyBatis-Plus提供分页对象。这个分页对象中不仅包含了当前查询出来的页数据;也包含了一系列的的与分页相关的信息。详情ctrl进去(好像没有中文注释了?哈哈)
 * 生成对应实现类的小技巧 : 选中BookService类名,在上面按 alt + 回车
 */
public interface BookService {
    /**
     * 分页查询图书
     * @param page 页号
     * @param rows 每页记录数
     * @return 分页对象
     */
    public IPage<Book> paging(Integer page, Integer rows);}
```

```java
//实现类
package com.imooc.reader.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)    //目前里边的每一个方法默认不开启事务
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;

    /**
     * 分页查询图书
     *
     * @param page 页号
     * @param rows 每页记录数
     * @return 分页对象
     */
    @Override
    public IPage<Book> paging(Integer page, Integer rows) {
        Page<Book> p = new Page<Book>(page, rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
        //还没有任何需要筛选的条件,所以直接将queryWrapper对象放入到第二个参数。相当于对原始的所有数据进行分页查询了
        IPage<Book> pageObject = bookMapper.selectPage(p, queryWrapper);
        return pageObject;}}
```



```java
//测试类
package com.imooc.reader.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)//junit4在运行时，会自动初始化ioc容器
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) //说明配置文件在什么地方
public class BookServiceImplTest {

    @Resource
    private BookService bookService;

    @Test
    public void paging() {
        //查询第1页的数据每页10条 1-10    (如果是page:2,rows:10,查询到的是 11-20)
        //查询的步骤: 1.先是获取没有分页的时候数据总数是多少,只有数据总数才能计算出一共有多少页 [SELECT COUNT(1) FROM book ]
        // 2.总数再除以每页的记录数就能计算出本次查询一共有多少页 [SELECT book_id,book_name,...,evaluation_quantity FROM book LIMIT ?,? ]
        IPage<Book> pageObject = bookService.paging(2, 10);
        List<Book> records = pageObject.getRecords();
        System.out.println("=================================");
        for (Book b : records) {
            System.out.println(b.getBookId() + " : " + b.getBookName());
        }
        //获取总页数
        System.out.println("总页数 : " + pageObject.getPages());      //5
        //获取总记录数
        System.out.println("总记录数 : " + pageObject.getTotal());    //44
 }}
```



#### Ajax动态加载图书信息

```
作为图书的信息我们采用哪种方式来进行加载呢?
1.可以在Freemarker中一次性直接生成
2.也可以当这个界面显示以后,利用JavaScript提供Ajax和Controller来进行交互。
这两种不同的方式处理代码也是不一样的。更倾向于后者，选择Ajax。
why?
答: 最下方有一个点击加载更多,如果采用Freemarker渲染重新返回的话,必然会导致点击按钮之后，我们界面整体重新刷新，然后页面滚回到最上方的位置。采用Ajax动态加载就不会这样,因为Ajax是在当前页面进行局部刷新,追加服务器返回新的图书数据,这样界面的交换会更加友好。但是有一个缺点:Ajax相比起Freemarker中进行渲染,它的开发的难度会稍微大一点,因为全程需要加入JavaScript来控制请求的提交以及返回的数据的处理。
```

```java
//控制器类 BookController 新增分页查询图书列表
package com.imooc.reader.controller;
import ...

@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;

    /**
     * 显示首页
     *
     * @return
     */
    @GetMapping("/")
    public ModelAndView showIndex() {
        List<Category> categoryList = categoryService.selectAll();
        ModelAndView mav = new ModelAndView("/index");
        mav.addObject("categoryList", categoryList);
        return mav;
    }

    /**
     * 分页查询图书列表
     *
     * @param p 页号(从前台传过来的)
     * @return分页对象
     */
    @GetMapping("/books")
    @ResponseBody
    public IPage<Book> selectBook(Integer p) {
        if (p == null) {
            p = 1;
        }
        IPage<Book> pageObject = bookService.paging(p, 10);
        //自动的会Json序列化输出
        return pageObject;}}

```

```JavaScript
//index.ftl, Ajax与服务器交互以后所产生的json数据,我们要转换成对应的html,然后动态的加载到对应的数据容器中。(这是在日常开发中最普遍的做法,这里是针对简单的html拼接字符串,如果复杂的话就不ok。为了解决这个问题,新的小技术————js模板引擎,完成复杂页面的构建工作)
<script>
    $(function () {
        $.ajax({
            url: "/books",
            //JavaScript处理的时候会给p加上双引号,当做字符串处理
            data: {p: 1},
            type: "get",
            dataType: "json",
            //服务器返回数据时,用success函数来接收
            success: function (json) {
                var list = json.records;
                for (var i = 0; i < list.length; i++) {
                    var book = json.records[i];
                    // var html = "<li>"+book.bookName+"</li>";
                    var html = "<h4>"+book.bookName+"</h4>";
                    $("#bookList").append(html);
                }}})})
</script>
```



#### JS模板引擎 Art-Template使用入门

##### Art-Template 腾讯JS模板引擎

##### 星型评分组件raty

```javascript
基于腾讯开源的高性能的Js模板引擎。其主要的用途是高效的基于模板来生成复杂的html片段
http://aui.github.io/art-template/zh-cn/index.html

Art-Template的使用:
1.引入js引擎	    <script src="./resources/art-template.js"></script>
2.定义模板	[需要指定type和给其一个id]
    <#--定义模板-->
    <#--type说明script中内容的类型 tpl英文模板template的简写-->
    <script type="text/html" id="tpl">
        <a href="/book/{{bookId}}" style="color: inherit">
            <div class="row mt-2 book">
                <div class="col-4 mb-2 pr-2">
                    <img class="img-fluid" src="{{cover}}"></div>
                <div class="col-8  mb-2 pl-0">
                    <h5 class="text-truncate">{{bookName}}</h5>

                    <div class="mb-2 bg-light small  p-2 w-100 text-truncate">{{author}}</div>
                    <div class="mb-2 w-100">{{subTitle}}</div><p>
                        <span class="stars" data-score="{{evaluationScore}}" title="gorgeous"><img alt="1" src="./resources/raty/lib/images/star-on.png" title="gorgeous">&nbsp;<img  alt="2"
                                    src="./resources/raty/lib/images/star-on.png"
                                    title="gorgeous">&nbsp;<img
                                    alt="3" src="./resources/raty/lib/images/star-on.png" title="gorgeous">&nbsp;<img
                                    alt="4" src="./resources/raty/lib/images/star-on.png" title="gorgeous">&nbsp;<img
                                    alt="5" src="./resources/raty/lib/images/star-on.png" title="gorgeous"><input
                                    name="score" type="hidden" value="{{evaluationScore}}" readonly=""></span>
                        <span class="mt-2 ml-2">{{evaluationScore}}</span>
                        <span class="mt-2 ml-2">{{evaluationQuantity}}</span></p></div></div></a></script>
3.在Ajax接收到数据以后,通过template方法将模板和数据结合生成新的html,这样我们将html追加到对应的图书容器中就可以显示出来了 
    <script>
        $(function () {
            //指定存储星型图片的目录在哪
            $.fn.raty.defaults.path ="./resources/raty/lib/images"
            $.ajax({
                url: "/books",
                //JavaScript处理的时候会给p加上双引号,当做字符串处理
                data: {p: 1},
                type: "get",
                dataType: "json",
                //服务器返回数据时,用success函数来接收
                success: function (json) {
                    var list = json.records;
                    for (var i = 0; i < list.length; i++) {
                        var book = json.records[i];
                        // var html = "<li>"+book.bookName+"</li>";
                        //将数据结合tpl模板,生成html
                        var html = template("tpl", book);
                        console.info(html);
                        $("#bookList").append(html);}
                        //显示星型评价组件  [选中class为stars的span标签,利用.raty便可以将对应的span转换为可视的星型组件。 readonly:true这里只是对用户进行显示并不容许用户更改]
                    $(".stars").raty({readonly:true})
                }})})</script>
```

```javascript
[这里还有一个隐藏的Bug:将第一本书的评分改为2.9,前端评分改变了但是星星却没有动静。星星是如何产生的呢?这就涉及到另外一个星型评分组件raty的用法了] 
1.导入组件依赖     
    <script src="./resources/raty/lib/jquery.raty.js"></script>
    <link rel="stylesheet" href="./resources/raty/lib/jquery.raty.css">
    <script src="./resources/jquery.3.3.1.min.js"></script>
2. //指定存储星型图片的目录在哪 [详情见上文]
	$.fn.raty.defaults.path ="./resources/raty/lib/images"
3..<p><#--data-score并不是我们自定义的属性,而是raty强制的要求。通过data-score属性描述组件当前的评分是多少-->
                        <span class="stars" data-score="{{evaluationScore}}" title="gorgeous"></span>
                        <span class="mt-2 ml-2">{{evaluationScore}}</span>
                        <span class="mt-2 ml-2">{{evaluationQuantity}}人已评</span></p>
4.//显示星型评价组件   $(".stars").raty({readonly:true})
```



#### 实现图书列表分页查询

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220902214119271.png" alt="image-20220902214119271" style="zoom:50%;" />

```
点击加载更多的按钮其本质就是对数据的分页查询,每天点一次加载更多就去查询下一页的数据,直到所有数据查完就提示没有更多数据了。作为这个功能主要考察我们对于JavaScript中分页的处理能力。

这里又产生了一个问题,作为下一页到底是第几页? 得有一个地方去保存下一页的页号

还有一个新的问题 : 到了最后一页 "加载更多按钮"就不该存在了
```

```javascript
//绑定加载更多按钮的单击事件
$(function () {
    $("#btnMore").click(function () {
        var nextPage = $("#nextPage").val();
        $.ajax({
            url: "/books",
            //JavaScript处理的时候会给p加上双引号,当做字符串处理
            data: {p: nextPage},
            type: "get",
            dataType: "json",
            //服务器返回数据时,用success函数来接收
            success: function (json) {
                var list = json.records;
                for (var i = 0; i < list.length; i++) {
                    var book = json.records[i];
                    // var html = "<li>"+book.bookName+"</li>";
                    //将数据结合tpl模板,生成html
                    var html = template("tpl", book);
                    console.info(html);
                    $("#bookList").append(html);
                }
                //显示星型评价组件  [选中class为stars的span标签,利用.raty便可以将对应的span转换为可视的星型组件。 readonly:true这里只是对用户进行显示并不容许用户更改]
                $(".stars").raty({readonly: true});

                //判断是否到最后一页
                //json.current代表当前页号, json.pages是总页数。这里还有一个小细节:json.current可能会被JavaScript当成字符串来处理,解决方法是增加parseInt转换为数字
                if (json.current < json.pages) {
                    $("#nextPage").val(parseInt(json.current) + 1);
                    $("#btnMore").show();
                    $("#divNoMore").hide();
                } else {
                    $("#btnMore").hide();
                    $("#divNoMore").show();}}})})})
```

```
以上关于动态加载分页的工作已经完成了。但此时我们的代码不太好看,因为在上面初始化的工程中来进行了一次Ajax请求和下面加载更多的时候两者的代码是非常像的。 那能不能对其重构和梳理? 养成代码及时重构和梳理是一个好习惯。具体的做法
```

```javascript
//对代码进行重构之后,新增标志位isReset,如果isReset为true的话,nextPage设置为1,即加载首页。大大减少了代码量
<script>
        $(function () {
            //指定存储星型图片的目录在哪
            $.fn.raty.defaults.path = "./resources/raty/lib/images";

            //loadMore() 加载更多数据
            //isReset参数设置为true,代表从第一页开始查询,否则按nextPage查询后续页
            function loadMore(isReset) {
                if (isReset == true) {
                    $("#nextPage").val(1);
                }
                var nextPage = $("#nextPage").val();
                $.ajax({
                    url: "/books",
                    //JavaScript处理的时候会给p加上双引号,当做字符串处理
                    data: {p: nextPage},
                    type: "get",
                    dataType: "json",
                    //服务器返回数据时,用success函数来接收
                    success: function (json) {
                        var list = json.records;
                        for (var i = 0; i < list.length; i++) {
                            var book = json.records[i];
                            // var html = "<li>"+book.bookName+"</li>";
                            //将数据结合tpl模板,生成html
                            var html = template("tpl", book);
                            console.info(html);
                            $("#bookList").append(html);
                        }
                        //显示星型评价组件  [选中class为stars的span标签,利用.raty便可以将对应的span转换为可视的星型组件。 readonly:true这里只是对用户进行显示并不容许用户更改]
                        $(".stars").raty({readonly: true});

                        //判断是否到最后一页
                        //json.current代表当前页号, json.pages是总页数。这里还有一个小细节:json.current可能会被JavaScript当成字符串来处理,解决方法是增加parseInt转换为数字
                        if (json.current < json.pages) {
                            $("#nextPage").val(parseInt(json.current) + 1);
                            $("#btnMore").show();
                            $("#divNoMore").hide();
                        } else {
                            $("#btnMore").hide();
                            $("#divNoMore").show();
                        }
                    }
                })
            }

            // $.ajax({
            //     url: "/books",
            //     //JavaScript处理的时候会给p加上双引号,当做字符串处理
            //     data: {p: 1},
            //     type: "get",
            //     dataType: "json",
            //     //服务器返回数据时,用success函数来接收
            //     success: function (json) {
            //         var list = json.records;
            //         for (var i = 0; i < list.length; i++) {
            //             var book = json.records[i];
            //             // var html = "<li>"+book.bookName+"</li>";
            //             //将数据结合tpl模板,生成html
            //             var html = template("tpl", book);
            //             console.info(html);
            //             $("#bookList").append(html);
            //         }
            //         //显示星型评价组件  [选中class为stars的span标签,利用.raty便可以将对应的span转换为可视的星型组件。 readonly:true这里只是对用户进行显示并不容许用户更改]
            //         $(".stars").raty({readonly: true})
            //     }
            // })
    
            //这里设置为ture,即默认加载第一页(nextPage=1)
            loadMore(true)

            //绑定加载更多按钮的单击事件
            $(function () {
                $("#btnMore").click(function () {
                    loadMore();
                })})}) </script>
```



#### 多条件分页查询

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220903194739595.png" alt="image-20220903194739595" style="zoom:50%;" />

```javascript
针对这样的查询功能,其本质还是建立在我们前面写过的分页方法基础之上,只不过增加了一些额外的条件。
作为当前这个功能,我们要分成两个步骤来完成 :
    1.当我们点击了这些超链接([筛选条件 全部、前端、后端...])以后,我们要它对应的进行高亮显示。[具体实现逻辑是不管点击了哪个超链接先把所有的高亮都移除,所有都设置为灰色,最后将点击的那个超链接单独设置为高亮]
                    //.category css类是每一个分类超链接都必然拥有的css类。通过.category对当前页面所有的分类的超链接进行捕获
                $(".category").click(function (){
                    $(".category").removeClass("highlight");    //移除所有高亮显示
                    $(".category").addClass(" text-black-50");  //设置为灰色
                    $(this).addClass("highlight");  //捕获当前当前点击的超链接并设置高亮
                })

                //.order css类是每一个排序超链接都必然拥有的css类
                $(".order").click(function (){
                    $(".order").removeClass("highlight");    //移除所有高亮显示
                    $(".order").addClass(" text-black-50");  //设置为灰色
                    $(this).addClass("highlight");  //捕获当前当前点击的超链接并设置高亮
                })
    2.在点击之后根据所选的条件在后台进行动态的分页查询
    //要实现联动的功能,需要修改SQL语句。在BookService.java中增加参数
     /**
     * 分页查询图书
     *
     * @param category 分页编号
     * @param order    排序方式
     * @param page     页号
     * @param rows     每页记录数
     * @return 分页对象
     */
     public IPage<Book> paging(Long category, String order, Integer page, Integer rows);
	对应了前台的分类编号以及要排序的规则
```

```
单元测试完成之后,重点来到控制器和前台页面的交互上,我们只需要保障,从前台页面传入categoryId以及order就可以完成对应的数据处理了。具体做法回到index.ftl页面
```

```java
package ...
    
//多条件分页查询新增的代码	BookServiceImpl.java
@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)    //目前里边的每一个方法默认不开启事务
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;

    /**
     * 分页查询图书
     *
     * @param categoryId 分页编号
     * @param order      排序方式
     * @param page       页号
     * @param rows       每页记录数
     * @return 分页对象
     */
    @Override
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer rows) {
        Page<Book> p = new Page<Book>(page, rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
        //这个判断代表了从前台传入了有效的分页编号
        if (categoryId != null && categoryId != -1) {
            queryWrapper.eq("category_id", categoryId);
        }
        if (order != null) {
            //排序的规则有两种
            //按照评价人数来进行排序
            if (order.equals("quantity")) {
                queryWrapper.orderByDesc("evaluation_quantity");    //按照指定字段进行降序排列
                //按照具体的评分进行降序排列
            } else if (order.equals("score")) {
                queryWrapper.orderByDesc("evaluation_score");}}
```

```javascript
//.category css类是每一个分类超链接都必然拥有的css类。通过.category对当前页面所有的分类的超链接进行捕获
$(".category").click(function () {
    $(".category").removeClass("highlight");    //移除所有高亮显示
    $(".category").addClass(" text-black-50");  //设置为灰色
    $(this).addClass("highlight");  //捕获当前当前点击的超链接并设置高亮
    //这里对应的是自定义属性data后边的值   <span data-category="-1"...
    //一旦执行了这句话,那么它就会得到当前点击的超链接它的categoryId编号,接着将编号赋值给隐藏域categoryId。
    // 一旦赋值后,在进行数据加载过程中就会读取这个结果来完成数据的筛选
    var categoryId = $(this).data("category");
    $("#categoryId").val(categoryId);
    //每点击图书类别的时候,相当于进行重新的查询
    loadMore(true);
})

//.order css类是每一个排序超链接都必然拥有的css类
$(".order").click(function () {
    $(".order").removeClass("highlight");    //移除所有高亮显示
    $(".order").addClass(" text-black-50");  //设置为灰色
    $(this).addClass("highlight");  //捕获当前当前点击的超链接并设置高亮
    var order = $(this).data("order");
    $("#order").val(order);
    loadMore(true);
})
```

```javascript
//将获取到的值传给 Controller处理   data: {p: nextPage, "categoryId": categoryId, "order": order} 利用get传值
function loadMore(isReset) {
    if (isReset == true) {
        //将原有已经查询的所有图书信息来给予清空。 [不然的话会增加到已有信息的后面]
        $("#bookList").html("");
        $("#nextPage").val(1);
    }
    var nextPage = $("#nextPage").val();
    //获取两个隐藏域的数值 分类id和排序编号
    var categoryId = $("#categoryId").val();
    var order = $("#order").val();
    $.ajax({
        url: "/books",
        //JavaScript处理的时候会给p加上双引号,当做字符串处理
        data: {p: nextPage, "categoryId": categoryId, "order": order},
```



#### 图书详情页-读取图书信息

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220904193740645.png" alt="image-20220904193740645" style="zoom:50%;" />

```
对于详情页来说,其主要内容包含了两大块: 
1.为上图的左半部分
2.为短评部分,处于页面的最下方。上图的右半部分
```

```javascript
<#--定义模板-->
<#--type说明script中内容的类型 tpl英文模板template的简写-->
<script type="text/html" id="tpl">
    <a href="/book/{{bookId}}" style="color: inherit">
        <div class="row mt-2 book"><div class="col-4 mb-2 pr-2">
                <img class="img-fluid" src="{{cover}}"></div><div class="col-8  mb-2 pl-0">
                <h5 class="text-truncate">{{bookName}}</h5>
                <div class="mb-2 bg-light small  p-2 w-100 text-truncate">{{author}}</div>
                <div class="mb-2 w-100">{{subTitle}}</div><p>
                    <#--data-score并不是我们自定义的属性,而是raty强制的要求。通过data-score属性描述组件当前的评分是多少-->
                    <span class="stars" data-score="{{evaluationScore}}" title="gorgeous"></span>
                    <span class="mt-2 ml-2">{{evaluationScore}}</span>
                    <span class="mt-2 ml-2">{{evaluationQuantity}}人已评</span>
                </p></div> </div></a></script>
```

```java
当我们点击上面的超链接的时候,它访问的图书地址是 /book/{{bookId}},那对于当前的超链接来说图书的id是存放在url中,我们要把它提取出来。这底层传递的是图书编号,那自然要提供一个按照图书id去获取图书对象的操作。下面开发这个方法

基于Book实体类以及对应Mapper接口和xml都已经准备好了,那下面的工作自然是打开BookService接口,在这里定义全新的方法
    //BookService.java中新增查询方法
    /**
     * 根据图书编号查询图书对象
     *
     * @param bookId 图书编号
     * @return 图书对象
     */
    public Book selectById(Long bookId);

	//BookServiceImpl.java	实现类实现BookService接口中的方法
    @Override
    public Book selectById(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        return book;
    }

在BookController中根据index.ftl提供的地址/book/{{bookId}},我们要做相应的url绑定
    //BookController.java中的新增，注意这里使用了路径变量
        /**
     * 使用id这个路径变量获取存放在url中的图书编号
     * (show开头即显示页面)
     */
    @GetMapping("/book/{id}")
    //这个id从哪来呢? 从前面的路径变量。所以在参数部分增加注解@PathVariable("id"),这个("id")要和路径变量里的名字一致
    public ModelAndView showDetail(@PathVariable("id") Long id){
        Book book = bookService.selectById(id);
        ModelAndView mav = new ModelAndView("/detail");
        mav.addObject("book",book);
        return mav;}
```

```
新的问题: 当从index.ftl 跳转到 detail.ftl页面时,部分css和js失效了, 原因是请求url有问题
Request URL: http://localhost/book/resources/bootstrap/bootstrap.css
本来应该是	http://localhost/resources/bootstrap/bootstrap.css
为什么会增加了一个book的前缀?  在实际应用场景下,应该是从localhost直接访问到resources目录才是正确的,这是为什么?  原因很明确,是因为在我们实际开发时,这里使用的是相对路径	href="./resources/bootstrap/bootstrap.css"。
那么这些资源的地址实际上,它是会附加到最后一级目录后面的,也就是指作为相对路径它是基于我们目前url所在的路径之后再进行附加。因为book的存在导致对应访问的资源找不到出现404错误。
为了解决这个问题,建议是在实际应用开发时尽量的不要使用相对地址,而要使用绝对地址
"/resources/bootstrap/bootstrap.css" 把.点去掉意味着从locahost以后开始查找相应的资源
```

```html
//在html中用Freemarker语法嵌入对应的数据就ok ${book.bookName}
<div class="row">
    <div class="col-4 mb-2 pl-0 pr-0">
        <img style="width: 110px;height: 160px"
             src="${book.cover}">
    </div>
    <div class="col-8 pt-2 mb-2 pl-0">
        <h6 class="text-white">${book.bookName}</h6>
        <div class="p-1 alert alert-warning small" role="alert">
            ${book.subTitle}
```

```
至此,图书详情页-读取图书信息完成(图书基本信息的展示)。 
还有一个问题就是,图片通过idea不能直接访问到(报错403),但是在浏览器中却能打开
https://img4.mukewang.com/5ce256ea00014bc903600480.jpg
看看解决这个问题
```



#### 图书详情页-显示评论列表

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220904212039225.png" alt="image-20220904212039225" style="zoom:50%;" />

```
数据来自evaluation,评论表。创建与之对应的实体类、Mappers接口,然后是Service类、实现类,接着是Controller类,最后渲染到页面

```

```java
//EvaluationService.java
package com.imooc.reader.service;
import com.imooc.reader.entity.Evaluation;
import java.util.List;

public interface EvaluationService {
    /**
     * 按图书编号查询有效短评
     *
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId);}
```

```java
//实现类 EvaluationServiceImpl.java,实现EvaluationService中的方法
import ...

@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    //在类实例化的时候用@Resource注入对应的Mapper接口
    @Resource
    private EvaluationMapper evaluationMapper;
    /**
     * 按图书编号查询有效短评
     *
     * @param bookId 图书编号
     * @return 评论列表
     */
    @Override
    public List<Evaluation> selectByBookId(Long bookId) {
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        //首先按照bookId来进行筛选
        queryWrapper.eq("book_id",bookId);
        //筛选有效短评,当然是根据state这个字段
        queryWrapper.eq("state","enable");
        //同时默认情况下,按创建时间的降序来进行排列
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);
        return evaluationList;
    }}
```

```java
//BookController.java新增evaluationList传数据给ftl页面
/**
 * 使用id这个路径变量获取存放在url中的图书编号
 * (show开头即显示页面)
 */
@GetMapping("/book/{id}")
//这个id从哪来呢? 从前面的路径变量。所以在参数部分增加注解@PathVariable("id"),这个("id")要和路径变量里的名字一致
public ModelAndView showDetail(@PathVariable("id") Long id){
    Book book = bookService.selectById(id);
    //在BookController得到对应的图书编号以后,可以基于Service查询对应的短评信息
    List<Evaluation> evaluationList = evaluationService.selectByBookId(id);
    ModelAndView mav = new ModelAndView("/detail");
    mav.addObject("book",book);
    mav.addObject("evaluationList",evaluationList);
    return mav;
}
```

```java
//Evaluation.java  评论表
import java.util.Date;

@TableName("evaluation")
public class Evaluation {
    @TableId(type = IdType.AUTO)
    private Long evaluationId;
    private Long bookId;
    private String content;
    private Integer score;
    private Date createTime;
    private Long memberId;
    private Integer enjoy;
    private String state;
    private String disableReason;
    private Date disableTime;

    //在很多情况下,我们确实需要在这个实体中增加一些与数据库字段不对应的属性,比如说这里有一个bookId
    //但是它只能表明id这个信息,如果想获取评论的时候也把与之对应的图书的名字也打印出来,该怎么做?
    //在当前实体中增加     private Book book; 属性名为book,为其生成get、set方法,那这样作为这个属性
    //就拥有了一个book的关联对象,但是作为这个关联对象,它底层肯定是不会在Evaluation表中有对应字段的,
    //所以针对于这种没有对应字段的属性,我们还需要增加一个注解  @TableField(exist = false)  另外一个与evaluation关联的就是member 会员对象(表)了
    @TableField(exist = false)  //说明book属性没有对应字段,不会参与到sql自动生成
    private Book book;

    public Book getBook() {
        return book;}

    public void setBook(Book book) {
        this.book = book;}
    
    //创建会员对象
    import com.baomidou.mybatisplus.annotation.TableName;

    import java.util.Date;

    //由于数据库8版本存在细微差异member被选为保留字段，建议在表名上加上反引号，避免保留字段和表名冲突，			如:/@TableName("`member`")
    @TableName("member")
    public class Member {
        @TableId(type = IdType.AUTO)
        private Long memberId;
        private String username;
        private String password;
        private Integer salt;
        private Date createTime;
        private String nickname;
        ....
    //然后是对应的Mapper接口和xml
            ...
    //回到Evaluation.java 实体类  持有Member对象
    @TableField(exist = false)  //说明book属性没有对应字段,不会参与到sql自动生成
    private Book book;

    @TableField(exist = false)
    private Member member;

    public Book getBook() {return book;}
        
    
    //关键地方来了,这里的book和member我们如何对他们把数据填充上 ?
    //答: 这就要依托于书写是Service实现类了 EvaluationServiceImpl
        
    public class EvaluationServiceImpl implements EvaluationService {
    //在类实例化的时候用@Resource注入对应的Mapper接口
    @Resource
    private EvaluationMapper evaluationMapper;
    //memberMapper在查询每条评论与之对应的会员对象时用得到 1.通过evaluation表中的member_id
    // 2.然后由member表查询这个id，进而可以把member表中信息显示在evaluation模块中 （BookMapper也类似）
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;
    /**
     * 按图书编号查询有效短评
     *
     * @param bookId 图书编号
     * @return 评论列表
     */
    @Override
    public List<Evaluation> selectByBookId(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        //首先按照bookId来进行筛选
        queryWrapper.eq("book_id",bookId);
        //筛选有效短评,当然是根据state这个字段
        queryWrapper.eq("state","enable");
        //同时默认情况下,按创建时间的降序来进行排列
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);

        //[book和member我们如何对他们把数据填充上? 答:这就要依托于书写是Service实现类了EvaluationServiceImpl]
        // 在获取到对应的Evaluation List集合的时候,我们也要进行额外的查询工作————来查询
        // 每一个评论它所对应的会员以及图书的信息
        for(Evaluation eva:evaluationList){
            //查询该评论与之对应的会员对象
            Member member = memberMapper.selectById(eva.getMemberId());
            eva.setMember(member);
            eva.setBook(book);
        }
        return evaluationList;}
        
      	//最后在detail.ftl使用 		${evaluation.member.nickname}
        <#--对于日期进行格式化-->
        <span class="pt-1 small text-black-50 mr-2">${evaluation.createTime?string('yy-MM-dd')}</span>
        <span class="mr-2 small pt-1">${evaluation.member.nickname}</span>	//引用
        <span class="stars mr-2" data-score="${evaluation.score}"></span>
```

```java
写在本节课最后的结语: 作为MyBatis-Plus的实体类它是可以承载与字段没有任何对应的属性,只需要使用(如下)
    @TableField(exist = false)  //说明book属性没有对应字段,不会参与到sql自动生成
    private Book book;
那么这些与字段无关的属性,我们需要在Service实现类中进行手动的赋值 (如下),
        for(Evaluation eva:evaluationList){
            //查询该评论与之对应的会员对象
            Member member = memberMapper.selectById(eva.getMemberId());
            eva.setMember(member);}
有了以上操作,才可以在我们后续处理中被进行调用和访问(在detail.ftl中使用)
<span class="mr-2 small pt-1">${evaluation.member.nickname}</span>
```



#### Kaptcha验证码的使用和对比

##### 会员注册与登录

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220906143447991.png" alt="image-20220906143447991" style="zoom:50%;" />

```
验证码的主要用途就是 进行人机校验。 什么是人机校验? 就是为了防止大量的机器脚本来自动注册用户。
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220906143823212.png" alt="image-20220906143823212" style="zoom:50%;" />

```
本节课先学习一个比较简单的字符验证码的生成方式,在后面的学习过程中,会通过辅助材料的形式为大家提供如何实现基于腾讯云的滑块验证码的方案
```

##### Kaptcha验证码组件 （验证码生成工具）

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220906144253460.png" alt="image-20220906144253460" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220906144511909.png" alt="image-20220906144511909" style="zoom:50%;" />

```xml
使用步骤
1.导入依赖
        <!--Kaptcha验证码组件-->
        <dependency>
            <groupId>com.github.penggle</groupId>
            <artifactId>kaptcha</artifactId>
            <version>2.3.2</version>
        </dependency>
2.打开核心配置文件	applicationCont.xml
    <!--配置Kaptcha (设置所生成的图片样式)-->
    <bean id="kaptchaProducer" class="com.google.code.kaptcha.impl.DefaultKaptcha">
        <property name="config">
            <bean class="com.google.code.kaptcha.util.Config">
                <constructor-arg>
                    <props>
                        <!--验证码图片不生成边框 no-->
                        <prop key="kaptcha.border">yes</prop>
                        <!--验证码图片宽度为120像素-->
                        <prop key="kaptcha.image.width">120</prop>
                        <!--验证码图片字体颜色为蓝色-->
                        <prop key="kaptcha.textproducer.font.color">green</prop>
                        <!--每个字符最大占用40像素-->
                        <prop key="kaptcha.textproducer.font.size">40</prop>
                        <!--验证码包含4个字符-->
                        <prop key="kaptcha.textproducer.char.length">4</prop>
                    </props>
                </constructor-arg>
            </bean>
        </property>
    </bean>
3.那我们该如何使用? 在controller下创建 KaptchaController
```

```java
//KaptchaController.java  访问http://localhost/verify_code 能看到验证码
import ...

@Controller
public class KaptchaController {
    @Resource
    //要与applicationContext.xml 定义的 kaptcha beanId一致  (Producer实际上是一个接口,而在xml中引入的...kaptcha.impl...就是实现Producer接口)
    private Producer kaptchaProducer;

    //生成验证码图片
    //SpringMVC 底层还是依赖于J2EE的web模块————Servlet,对于在开发中有一些特殊的场景必须要使用到原生的请求或者响应对象,那此时就可以像当前这样书写,把原生对象放在参数列表中
    @GetMapping("/verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //响应立即过期
        response.setDateHeader("Expires",0);
        //因为每一次要求生成的验证码都是全新的,所以我们要把所有的与浏览器的缓存都清空掉 (不存储、不缓存、必须重新进行校验 | no-store,no-cache..)
        response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
        //这两项比较古老了,是在ie5以后的扩展指令,其用意也是和缓存控制相关,平时用不到,出于兼容性的考虑将其加上
        response.setHeader("Cache-Control","post-check=0,pre-check=0");
        response.setHeader("Pragma","no-cache");
        //返回的内容类型
        response.setContentType("image/png");

        //生成验证码文本
        String verifyCode = kaptchaProducer.createText();
        //将验证码放入会话中
        request.getSession().setAttribute("kaptchaVerifyCode",verifyCode);
        System.out.println(request.getSession().getAttribute("kaptchaVerifyCode"));
        //创建验证码图片 (根据传入的验证码文本创建图片)
        BufferedImage image = kaptchaProducer.createImage(verifyCode);
        //因为这里得到的图片是二进制的,所以使用getOutputStream输出流; 如果输出内容是字符的话,就要使用getWriter来输出字符
        ServletOutputStream out = response.getOutputStream();
        //javax.imageio包中所提供的图片的输入输出功能。将image放入到out输出流中,其输出的图片格式为 png。
        // 通过这一句话,就可以完成将图片从服务器端通过响应发送给客户端浏览器,客户端浏览器收到了这个图片数据以后,一看内容类型是png就当图片进行展示了
        ImageIO.write(image,"png",out);
        out.flush();    //立即输出
        out.close();    //关闭输出流
        
        //注: Servlet程序向ServletOutputStream或PrintWriter对象中写入的数据将被Servlet引擎从response里面获取，
        // Servlet引擎将这些数据当做响应消息的正文，然后再与响应状态行和各响应头组合后输出到客户端。
    }} 
```

```
验证码准备好了,那如何进行校验? 这就涉及到与前台界面的交互工作了,将在用户与注册时实现
```



#### Kaptch验证码的使用和对比

##### 登录注册功能

```
生成验证码之后,需要和前台页面联合在一起才有实际使用的价值。本节课通过实现会员注册这个页面来学习如何将验证码运用在项目实战中。

```

```html
将/verify_code加在该img上就能正常显示验证码
<!-- 验证码图片 -->
<img id="imgVerifyCode" src="/verify_code" style="width: 120px;height:50px;cursor: pointer">
```

```java
//MemberController.java
import ...
//会员控制器
@Controller
public class MemberController {
    // .html可写可不写,但是如果是在互联网应用中,在我们互联网能够访问的情况下,建议跳转页面时将其加上后缀。
    // 一旦加上以后对于百度、谷歌这样的搜索引擎对其进行抓取时是十分友好的 对网站宣传、营销有用
    @GetMapping("/register.html")
    public ModelAndView showRegister(){
        return new ModelAndView("/register");}}
```

```javascript
新需求: 当我们点击注册页面的验证码时会对其进行刷新,因为我们有些一些验证码生成之后可能看不清,需要对其更换,这样的点击事件该怎么处理?

//重新发送请求,刷新验证码
function reloadVerifyCode() {
    //请在这里实现刷新验证码   [attr() 添加参数,attr(参数名,具体的值)]    ts即timestamp 时间戳
    //我们都知道现在通过src发起的是一个get请求,get请求是容易被浏览器缓存起来的,所以就可能出现点击以后尽管这个	代码执行了但是没有刷新验证码的情况,是因为缓存
    //为了解决这个问题,我们要保证每一次发起的verify_code请求的url都不一样,最简单的做法就是增加时间戳 new Date().getTime()
        $("#imgVerifyCode").attr("src", "/verify_code?ts="+ new Date().getTime())
    }

    //点击验证码图片刷新验证码
    $("#imgVerifyCode").click(function () {
        reloadVerifyCode();});
```

```
对于当前的验证码,如何对其进行校验? 
其实原理也很简单我们填写的验证码,当提交了以后和服务器端和session中的验证码进行比对就可以了,结果不对就代表输入的验证码有问题
```

```java
//在MemberController.java 新增验证码比对和返回响应内容 
@PostMapping("registe")
@ResponseBody
public Map registe(String vc, String username, String password, String nickname, HttpServletRequest request) {
    //在这里要比对前台传入的验证码和Session中的验证码,那么怎么拿到 Session中的验证码? 和在KaptchaController.java中一样,在参数列表加入原生请求参数 拿Session中的信息
    //正确的验证码
    String verifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");
    Map result = new HashMap();
    //验证码比对     (在忽略大小写的情况下来对两者进行比对,如果两者不匹配的时候,对比失败)
    if (vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)) {
        result.put("code","VC01");
        result.put("msg","验证码错误");
    }else {
        result.put("code","0");
        result.put("msg","success");
    }
    return result;}
```



#### 实现会员注册功能

```java
作为会员注册,对应的自然就是 MemberService, 接着创建对应的实现类    [Interface abstract methods cannot have body]
import ...
public interface MemberService {

    /**
     * 会员注册,创建新会员   (所谓注册其实就是创建一个新的会员)
     *
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 新会员对象
     */
    public Member createMember(String username, String password, String nickname);}
```

```java
//自定义的异常	com/imooc/reader/service/exception/BussinessException.java
package com.imooc.reader.service.exception;
/**
 * BussinessException 业务逻辑异常
 */
//与业务逻辑相关的异常  (Bussiness与Business)
public class BussinessException extends RuntimeException {
    private String code;
    private String msg;

    public BussinessException(String code, String msg) {
        //先调用super() 完成父类构造方法的调用 (父类构造方法继承自 RuntimeException)
        super(code + ":" + msg);
        this.code = code;
        this.msg = msg;}

    public String getCode() { return code;}
    public void setCode(String code) {this.code = code;}
    public String getMsg() {return msg;}
    public void setMsg(String msg) {this.msg = msg;}}
```

```java
密码不能明文保存在数据库,因此对前端页面传过来的密码进行加密再存储	(基于md5来进行加密)

package com.imooc.reader.utils;
import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    public static String md5Digest(String source, Integer salt) {
        char[] ca = source.toCharArray();   //获取到字符数组
        //混淆源数据
        for (int i = 0; i < ca.length; i++) {
            ca[i] = (char) (ca[i] + salt);
        }
        String target = new String(ca);
        String md5 = DigestUtils.md5Hex(target);
        return md5;}}
```

```java
用户注册的底层逻辑  MemberServiceImpl.java
package com.imooc.reader.service.impl;
import java.util.Random;

@Service("memberService")
//作为当前的实现类,它的主要职责是完成与会员的交互,比如说会员的注册、登录、评论、点赞等功能。
//以上应该是写操作比较多,我们针对于当前声明式事务的配置默认是打开事务
@Transactional
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    /**
     * 会员注册,创建新会员   (所谓注册其实就是创建一个新的会员)
     *
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 新会员对象
     */
    @Override
    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<Member> queryWrapper= new QueryWrapper<Member>();
        queryWrapper.eq("username",username);
        List<Member> memberList = memberMapper.selectList(queryWrapper);
        //判断用户名是否已存在
        if (memberList.size() > 0){
            throw new BussinessException("M01","用户名已存在");   //这个错误将在前台页面进行显示
        }
        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);

        int salt = new Random().nextInt(1000) + 1000;   //盐值 1001-2000
        String password_md5 = MD5Utils.md5Digest(password, salt);
        member.setPassword(password_md5);
        member.setSalt(salt);
        member.setCreateTime(new Date());
        memberMapper.insert(member);
        return member;}}
```

```
单元测试完成,向上推进到 Controller
```

```java
@PostMapping("registe")
@ResponseBody
public Map registe(String vc, String username, String password, String nickname, HttpServletRequest request) {
    //在这里要比对前台传入的验证码和Session中的验证码,那么怎么拿到 Session中的验证码? 和在KaptchaController.java中一样,在参数列表加入原生请求参数 拿Session中的信息
    //正确的验证码
    String verifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");
    Map result = new HashMap();
    //验证码比对     (在忽略大小写的情况下来对两者进行比对,如果两者不匹配的时候,对比失败)
    if (vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)) {
        result.put("code", "VC01");
        result.put("msg", "验证码错误");
    } else {
        try {
            //如果没有异常的话直接写入数据库
            memberService.createMember(username, password, nickname);
            result.put("code", "0");
            result.put("msg", "success");
            //存在异常的话,将异常信息放入result,最终在前台进行显示
        } catch (BussinessException ex) {
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
    }
    return result;
}
```

```
新的问题,impl包移动到Service包下导致的
Annotation-specified bean name 'bookService' for bean class [com.imooc.reader.service.impl.BookServiceImpl] conflicts with existing, non-compatible bean definition of same name and class [com.imooc.reader.impl.BookServiceImpl]

2022年9月7日16:27:16 已解决  移动整个文件夹时,要选择移动所有,不然可能会产生beanId冲突
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220907162834055.png" alt="image-20220907162834055" style="zoom:50%;" />



#### 实现会员登录功能

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220907163114452.png" alt="image-20220907163114452" style="zoom:50%;" />

```java
1.将登录页面移至项目中,命名为login.ftl,接着在MemberService类新增登录检查接口
    /**
     * 登录检查
     * @param username  用户名
     * @param password  密码
     * @return  登录对象
     */
    public Member checkLogin(String username, String password);
```

```java
2.然后是书写具体的实现类
    /**
     * 登录检查
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录对象
     */
    @Override
    public Member checkLogin(String username, String password) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username", username);
        Member member = memberMapper.selectOne(queryWrapper);
        //判断用户名是否已存在
        if (member == null) {
            throw new BussinessException("M02", "用户名不存在");
        }
        String password_md5 = MD5Utils.md5Digest(password, member.getSalt()); //拿数据库的加密密码和前台传过来经过同样经过加密处理的密码作比较
        if (!member.getPassword().equals(password_md5)) {
            throw new BussinessException("M03", "输入密码有误");
        }return member;}
```

```java
3.整体checkLogin方法完成后,那业务逻辑写好,下面就该到Controller中调用了
    
    //作为HttpSession这个对象,也是容许直接进行注入的,只需要把HttpSession对象放在参数列表中。至于这个对象的用途是: 在我们进行登录校验后,会将当前登录的这个会员信息存放到Session中,以备后续使用
    @PostMapping("/check_login")
    @ResponseBody
    public Map checkLogin(String username, String password, String vc, HttpSession session) {
        //正确的验证码        (相比于上面拿到Session数据这里直接使用session就可以,好像这种方式更快捷?)
        String verifyCode = (String) session.getAttribute("kaptchaVerifyCode");
        Map result = new HashMap();
        //验证码比对
        if (vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)) {
            result.put("code", "VC01");
            result.put("msg", "验证码错误");
        } else {
            try {
                Member member = memberService.checkLogin(username, password);
                result.put("code", "0");
                result.put("msg", "success");
            } catch (BussinessException ex) {
                ex.printStackTrace();
                result.put("code", ex.getCode());
                result.put("msg", ex.getMsg());
            }} return result;}
```

```java
4.当登录成功之后,需要将当前登录的会员信息存放到当前的Session中,这样可以在前台页面————首页拿到会员的信息进行显示
//MemberController.java
//登录校验成功之后会返回一个Member对象,我们可以将这个member当前登录用户存放在Session中,以备后续使用。 (这里放入的Session信息可以在index.ftl中被拿到,太强了)
Member member = memberService.checkLogin(username, password);
//这样只要这个会话还存在,用户登录成功后就可以通过LoginMember来提取到当前登录用户的数据了 将其放在首页显示,index.ftl
session.setAttribute("loginMember",member);

下面是拿放入的Session信息
```

```html
<#--流啤 ! 在MemberController放入的Session信息可以在这里被拿到  (session.setAttribute("loginMember",member);)-->
<#--如果loginMember存在的时候 [??两个问号代表loginMember(即前提条件)存在的情况下]  已登录和未登录两种情况区分开-->
<#if loginMember??>
    <h6>
        <img style="width: 2rem;margin-top: -5px" class="mr-1"
             src="./images/user_icon.png">${loginMember.nickname}
    </h6>
<#else >
    <a href="/login.html" class="btn btn-light btn-sm">
        <img style="width: 2rem;margin-top: -5px" class="mr-1" src="./images/user_icon.png">登录
    </a>
</#if>
```

##### 太强了



#### 获取会员阅读状态

##### 实现会员交互功能

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220908134411844.png" alt="image-20220908134411844" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220908134524954.png" alt="image-20220908134524954" style="zoom:50%;" />

```java
阅读状态变更: 想看和看过这两个状态是互斥的只能二选一

member_read_state表保存了会员的阅读状态,其中阅读状态read_state 1代表想看,2代表看过

创建这张表与之对应的实体类
接着是创建Mapper接口和对应的Mapper配置文件xml
程序向上推演到Service层,作为Service关于会员交互的功能肯定是在MemberService中来进行书写

    //MemberService.java
    /**
     * 获取阅读状态
     *
     * @param memberId 会员编号
     * @param bookId   图书编号
     * @return 阅读状态对象   (这里有两种情况,1.是该用户压根对这本书无操作  2.是看过或者想看)
     */
    public MemberReadState selectMemberReadState(Long memberId, Long bookId);

然后通过实现类对上面的接口进行实现
    //MemberServiceImpl.java
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<MemberReadState>();
        queryWrapper.eq("member_id",memberId);
        queryWrapper.eq("book_id",bookId);

        //作为当前的查询结果要么只有一条数据,要么就是null
        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);
        return memberReadState;
    }

问题: 那么selectMemberReadState这个获取阅读状态的方法该在什么时候被调用 ?
    最直接的调用时间是在我们BookController.java中,在显示detail详情页面的时候我们就可以进行查询————根据当前登录的状态以及会员的编号来进行查询。
    具体的做法是 :首先在参数列表中新增一个HTTPSession(因为要获取当前登录用户的信息),如果loginMember不为空的话,则通过ModelAndView传到detail.ftl前台页面
    //BookController.java
    @GetMapping("/book/{id}")
    public ModelAndView showDetail(@PathVariable("id") Long id, HttpSession session){
		...
        ModelAndView mav = new ModelAndView("/detail");

        //将当前用户登录信息拿出来
        Member loginMember = (Member) session.getAttribute("loginMember");
        //获取会员阅读状态  (而用户阅读状态必须要登录才能进行的,所以要判断用户时否已经登录)
        if (loginMember!=null){
            //将当前用户登录的会员id和bookId传入。 作为当前的这个阅读状态,我们将结果保存在ModelAndView中
            MemberReadState memberReadState = memberService.selectMemberReadState(loginMember.getMemberId(), id);
            mav.addObject("memberReadState",memberReadState);
        }
        mav.addObject("book",book);
        mav.addObject("evaluationList",evaluationList);
        return mav;}

还有一种情况是: 作为当前这个会员他可能从来没有看过这本书,也没有点过想看或者看过的按钮,所以此时的memberReadState有可能是空,我们在对这个属性使用的时候也要进行一下阅读状态的非空判断,下面就是与界面进行连接(打开deatil.ftl)
    
```

```javascript
//detail.ftl
<script>
	...
    $(function () {
        //如果memberReadState存在的时候,则代表当前会员要么点过想看要么点过看过,总之要将对应的按钮高亮显示
        <#if memberReadState ??>
            // $("*[data-read-state='1']") 代表着将当前页面中任何拥有data-read-state这个自定义属性且值为1的元素选中。(当然这里不能写死)
            //readState 用户阅读状态: 想看值为 1;看过值为 2
            //这一句话结合服务器端、Freemarker动态的在页面加载后利用JavaScript将对应按钮的状态置为了高亮
            $("*[data-read-state='${memberReadState.readState}']").addClass("highlight")
        </#if>
    })</script>
看下图,d登录后能正常根据从数据库查询的readState的值(1/2)分别对应显示在前台页面
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220908174854880.png" alt="image-20220908174854880" style="zoom:50%;" />



#### 更新会员阅读状态

```
我们已经将会员的阅读状态进行回填和高亮显示,但是它还不具备任何的交互性,接下来将对两个按钮进行处理,使其点击后动态的调整它的阅读状态

[重点]
在开发之前,无论是调整阅读状态,还是下面的写短评或者是为其他的短评点赞都有一个前置操作————那就是必须会员登录以后才可以进行后续操作。作为这个前置的检查,我们如何完成?  这当然也是后台需要我们通过代码来进行控制的


```

```javascript
回到detail.ftl页面,做登录的前置处理 即登录控制(对会员阅读状态、写短评按钮、点赞短评做处理。不登录的情况下)
        $(function () {
		...
//如果loginMember不存在的情况下,也就是没有登录的情况下 (登录控制)
            <#if !loginMember ??>
            //只要某个标签拥有了data-read-state这个自定义属性的话,那我就给你选中,去绑定点击事件  这里对应会员阅读状态、写短评按钮、点赞短评按钮做登录控制
            $("*[data-read-state],#btnEvaluation,*[data-evaluation-id]").click(function () {
                //未登录情况下提示 "需要登录"
                //首先利用JQuery选择器选中对话框div, 然后.modal()是JQuery bootstrap中提供的对话框函数,而show是执行对应的动作
                $("#exampleModalCenter").modal("show");})
            </#if>
          })
```

```java
登录的前置处理已完成,那下面我们要进行后续操作,作为点击想看或者看过以后要产生对应的状态数据,作为这个状态数据的方法,咱们要打开MemberService.java,额外新增方法
	//MemberService.java
    /**
     * 更新阅读状态
     *
     * @param memberId  会员编号
     * @param bookId    图书编号
     * @param readState 阅读状态
     * @return 阅读状态对象
     */
    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState);
```

```java
//具体实现类的工作 MemberServiceImpl.java   
public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState) {
    //首先要进行状态的查询,如果这个会员没有对应的阅读状态,则我们需要新建一条阅读状态保存到数据;
    // 但是如果这个会员之前已经有了对应的阅读状态,则需要对这个阅读状态的字段进行更新即可
    QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<MemberReadState>();
    queryWrapper.eq("member_id", memberId);
    queryWrapper.eq("book_id", bookId);
    MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);
    //如果阅读状态是空的,则代表没有点过按钮,需要新建数据
    //无则新增,有则更新
    if (memberReadState.getReadState()==null){
        memberReadState = new MemberReadState();
        memberReadState.setMemberId(memberId);
        memberReadState.setBookId(bookId);
        memberReadState.setCreateTime(new Date());
        memberReadState.setReadState(readState);
        memberReadStateMapper.insert(memberReadState);
        //如果之前这个状态已经存在,只需要更新即可。(阅读状态改为前台传来的readState)
    }else{
        memberReadState.setReadState(readState);
        //为什么这里的updateById 传入的是一个对象..? 看源码说明,传入的可以是一个实体类?
        memberReadStateMapper.updateById(memberReadState);
    }return memberReadState;}
```

```java
接着来到MemberController.java,MemberController是用于会员交互的控制器

    /**
     * 更新想看/看过阅读状态
     *
     * @param memberId  会员id
     * @param bookId    图书id
     * @param readState 阅读状态
     * @return 处理结果
     */
    @PostMapping("/update_read_state")
    @ResponseBody
    public Map updateReadState(Long memberId, Long bookId, Integer readState) {
        Map result = new HashMap();
        //这个更新方法并没有抛出任何的业务逻辑异常,为什么在这还要捕获?
        //答: 现在你没有抛出不代表未来不会抛出,所以我们在这进行一下捕获,也是为了未来程序的扩展性考虑的
        try {
            MemberReadState memberReadState = memberService.updateMemberReadState(memberId, bookId, readState);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }return result;}
```

```javascript
继续向上推演来到 detail.ftl,在页面中发送Ajax请求来完成处理,

            <#if loginMember??>
            //选中想看或者看过这两个按钮
                $("*[data-read-state]").click(function () {
                    //首先获取当前点击按钮的data read-state这个自定义的属性值
                    //如果点击想看,这个readState数据就是1;如果点击看过这里就是2,
                    var readState = $(this).data("read-state"); //对应data-read-state="" 中的值,1或者2
                    // console.log(readState+'$(this).data("read-state")')
                    //之后发起Ajax请求
                    $.post("/update_read_state",{
                        memberId:${loginMember.memberId},
                        bookId:${book.bookId},
                        readState:readState
                    },function (json) {
                        if (json.code == "0"){
                            $("*[data-read-state]").removeClass("highlight"); //清除css样式,让两个按钮回到默认的状态
                            $("*[data-read-state='"+ readState+"']").addClass("highlight");  //将与状态对应的按钮设为高亮
                        }})})
            </#if>
```



#### 实现写短评功能

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220909223804823.png" alt="image-20220909223804823" style="zoom:50%;" />

```java
22:58:01 DEBUG [http-nio-80-exec-3] o.s.w.s.DispatcherServlet - POST "/update_read_state", parameters={masked}
22:58:01 DEBUG [http-nio-80-exec-3] o.s.w.s.m.m.a.RequestMappingHandlerMapping - Mapped to com.imooc.reader.controller.MemberController#updateReadState(Long, Long, Integer)
22:58:01 DEBUG [http-nio-80-exec-3] o.s.j.d.DataSourceTransactionManager - Creating new transaction with name [com.imooc.reader.service.impl.MemberServiceImpl.updateMemberReadState]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
22:58:01 DEBUG [http-nio-80-exec-3] o.s.j.d.DataSourceTransactionManager - Acquired Connection [com.mysql.cj.jdbc.ConnectionImpl@5659d69] for JDBC transaction
22:58:01 DEBUG [http-nio-80-exec-3] o.s.j.d.DataSourceTransactionManager - Switching JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@5659d69] to manual commit
22:58:01 DEBUG [http-nio-80-exec-3] o.m.spring.SqlSessionUtils - Creating a new SqlSession
22:58:01 DEBUG [http-nio-80-exec-3] o.m.spring.SqlSessionUtils - Registering transaction synchronization for SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4178d231]
22:58:01 DEBUG [http-nio-80-exec-3] o.m.s.t.SpringManagedTransaction - JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@5659d69] will be managed by Spring
22:58:01 DEBUG [http-nio-80-exec-3] c.i.r.m.M.selectOne - ==>  Preparing: SELECT rs_id,book_id,member_id,read_state,create_time FROM member_read_state WHERE (member_id = ? AND book_id = ?) 
22:58:01 DEBUG [http-nio-80-exec-3] c.i.r.m.M.selectOne - ==> Parameters: 1(Long), 5(Long)
22:58:01 DEBUG [http-nio-80-exec-3] c.i.r.m.M.selectOne - <==      Total: 0
22:58:01 DEBUG [http-nio-80-exec-3] o.m.spring.SqlSessionUtils - Releasing transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4178d231]
22:58:01 DEBUG [http-nio-80-exec-3] o.m.spring.SqlSessionUtils - Transaction synchronization deregistering SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4178d231]
22:58:01 DEBUG [http-nio-80-exec-3] o.m.spring.SqlSessionUtils - Transaction synchronization closing SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@4178d231]
22:58:01 DEBUG [http-nio-80-exec-3] o.s.j.d.DataSourceTransactionManager - Initiating transaction rollback
22:58:01 DEBUG [http-nio-80-exec-3] o.s.j.d.DataSourceTransactionManager - Rolling back JDBC transaction on Connection [com.mysql.cj.jdbc.ConnectionImpl@5659d69]
22:58:01 DEBUG [http-nio-80-exec-3] o.s.j.d.DataSourceTransactionManager - Releasing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@5659d69] after transaction
22:58:01 DEBUG [http-nio-80-exec-3] o.s.w.s.DispatcherServlet - Failed to complete request: java.lang.NullPointerException

```

