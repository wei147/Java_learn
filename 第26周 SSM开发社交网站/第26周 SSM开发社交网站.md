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

