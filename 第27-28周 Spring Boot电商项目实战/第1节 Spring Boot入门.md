## 第27-28周 Spring Boot电商项目实战

```java
从Spring Boot开始,换新老师了。感谢老师的传授,从Jdbc到SSM开发实战 
```



#### Spring Boot电商项目实战 周介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220914173521776.png" alt="image-20220914173521776" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220914173740434.png" alt="image-20220914173740434" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220914174327955.png" alt="image-20220914174327955" style="zoom:50%;" />

#### 课程介绍

##### Spring Boot入门

```
那么在此之前,我们掌握的是SSM框架,随着技术的发展和时间的推移,逐渐就诞生了Spring Boot并且Spring Boot这个技术正逐渐的成为我们新时代的主流
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220914175400754.png" alt="image-20220914175400754" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220914175451304.png" alt="image-20220914175451304" style="zoom:50%;" />

##### 软件版本

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220914180019027.png" alt="image-20220914180019027" style="zoom:50%;" />

#### Spring Boot概述

##### Spring Boot诞生历史

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220914180420936.png" alt="image-20220914180420936" style="zoom:50%;" />

```
Spring 的缺点: 配置过于繁琐
2014年诞生
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220914180700298.png" alt="image-20220914180700298" style="zoom: 50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220914180912032.png" alt="image-20220914180912032" style="zoom:50%;" />

```
这样一来我们就明白了这一系列Spring 家族他们的关系。最开始诞生的是Spring核心———— Framework,它的特点是IOC和AOP,在此基础之上有了MVC,然后呢为了简化又诞生了Boot,最后在分布式领域又诞生了Spring Cloud。他们就是这样一步一步迭代的
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220914181759691.png" alt="image-20220914181759691" style="zoom:50%;" />



#### Spring Boot 版本介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220915004254077.png" alt="image-20220915004254077" style="zoom:50%;" />

```java
如果哪一个版本被标记为Current,它代表是最新的GA版本。什么是GA? GA的意思就是发布版本,也就是 General Availability,它是面向大众的、可用的稳定版本。这种版本的特点就是 功能完整并且非常稳定,一旦分布之后,这个版本的内容将永远不会更改。

Snapshot在英文中是快照的意思,它的版本是可以随时被修改的。
    
Spring Boot更新了什么? 支持http 2.0(相比1而言在底层传输上、在信息头上都做了很多优化,在传输效率上有所提高)
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220915010530988.png" alt="image-20220915010530988" style="zoom:50%;" />

#### 新建Spring Boot项目演示

```java
Spring Boot有两种新建的办法:
1.通过Spring官网新建项目 https://start.spring.io/
之前会使用war包,因为要将其部署到tomcat上面,不过到了Spring Boot这里我们未来都会使用jar包。使用jar包我们可以直接用java来运行它,而不再需要额外的配置Tomcat,因为在Spring Boot中Tomcat是被内嵌在Java项目中的
流程:进入到https://start.spring.io/ 配置 ,然后下载、解压、导入到Idea

2.IDEA集成的Spring Initializr
    

```



#### 完成第一个接口开发

##### 案例实现

```
这个小节中,我们将会编写一个简单的查询学生信息的小案例,我们将会开发Spring Boot的接口,我们会新建Controller,然后启动。启动起来之后便可以通过浏览器在网页中访问我们的接口,并且还会进行一定的参数传递,,
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220915121029156.png" alt="image-20220915121029156" style="zoom:50%;" />

```java
//暂时可以看到集成了Tomcat以及SpringMVC的注解形式
package com.imooc.springbootlearnnew;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述: 演示各种传参形式
 */
@RestController //RestController含义包含两层:1.是一个普通的Controller 2.有Restful的能力
public class ParaController {
    @GetMapping("/first")
    public String firstRequest(){
        return "第一个Spring Boot接口";
    }}
```



#### 多种配置URL的方式

```java
@GetMapping("/first_new")   //请求参数 http://localhost:8080/first_new/?num=9
public String requestPara(@RequestParam Integer num) {
    return "param from request" + num;
}

@GetMapping("/path/{num}")   //路径变量 http://localhost:8080/path/9
public String requestPath(@PathVariable Integer num) {
    return "param from request : " + num;
}

//一个接口希望是想有多个地址(多个地址想访问同一个接口)
@GetMapping({"/url1", "/url2"})
public String multiUrl(@RequestParam Integer num) {
    return "多url的写法  param from request : " + num;
}

//如果用户没有传参则使用默认值 (不是必须要传参)
@GetMapping("/require")   // http://localhost:8080/require
public String require(@RequestParam(required = false,defaultValue = "99") Integer num) {
    return "param from request" + num;
}
```



#### 配置文件的两种写法

##### Web项目的三层结构

```java
我们自然不能把目光停留在Controller层,还要继续往下深入,深入之前回顾一下Web项目的三层结构 :
1.Controller层职责就是对外暴露接口,在它下面一般会有Service层
2.Service层就是在复杂业务场景下对业务逻辑做一层抽象、做一层封装,保持Controller层的简洁和独立。抽象出来的Service可以被多个Controller重复调用,相当于是实现了代码的复用。具体的业务代码尽量是写在Service层,而我们的Controller只做一些简单的逻辑判断。在Service层下面,还有一个DAO层
3.DAO层主要是放和数据相关的,比如增删改查数据库都是在DAO层
```

##### 配置文件简介

```java
三层结构之后,让我们看一下配置文件,因为这个配置文件和之前的SSM项目也有很大的不同,有必要介绍一下,介绍清楚。

演示两种对它的配置:
1.首先看到properties,可以在里面配置很多内容,比如说容器端口名、数据库信息、日志级别等。
 #端口配置
server.port=8080
#给整个项目建立统一的前缀
server.servlet.context-path=/first   
    
2.yml: 分层级,冒号后需要加空格
    server:
  port: 8080
  servlet:
    context-path: /first
以上两种写法可以相互转换(转换网址: https://toyaml.com/index.html)
```



#### 进行自定义配置

##### 配置自定义属性

```java
(利用配置文件去配置属性)

school.grade=6
school.classNum=9
    
有两种典型的写法:
1.//通过@Value读取配置文件中的值  (缺点是没有字段提示,需要自己看配置文件)
2.//第二个写法利用的是配置类 (新建一个配置类,并且在类中新定义属性然后给它加上get和set方法。这样一来在后续就可以直接使用配置类)
```

```java
//1.通过@Value读取配置文件中的值
package com.imooc.springbootlearnnew;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertiesController {
    //通过@Value来读取配置文件中的值 (利用配置文件去配置属性)
    @Value("${school.grade}")
    Integer grade;
    @Value("${school.classNum}")
    Integer classNum;

    @GetMapping("/gradeClass")
    public String gradeClass() {
        return "年级:" + grade + "班级: " + classNum;
    }}
```

```java
//2.利用的是配置类
package com.imooc.springbootlearnnew;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * School配置类
 */
@Component
@ConfigurationProperties(prefix = "school")            //配置相关的注解
public class SchoolConfig {
    Integer grade;
    Integer classNum;

    //这里要生成get和set方法,在ConfigController中才能读取到grade和classNum的值
    public Integer getGrade() {return grade;}
    public void setGrade(Integer grade) {this.grade = grade;}
    public Integer getClassNum() {return classNum;}
    public void setClassNum(Integer classNum) {
        this.classNum = classNum;}}
```

```java
package com.imooc.springbootlearnnew;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 读取配置类
 */
@RestController
public class ConfigController {
    @Resource //注入
    SchoolConfig schoolConfig;

    @GetMapping("/gradeFromConfig")
    public String gradeClass(){
        return "年级:" + schoolConfig.grade + "班级: " + schoolConfig.classNum;}}
```



#### 完成Service和DAO的编写

```
学生信息查询案例
```

```xml
//引入依赖
<!--MyBatis-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.1</version>
</dependency>

<!--Mysql-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.22</version>
</dependency>
```

```java
//application.properties
//Spring Boot连接mysql
#端口配置
#server.port=8080
#给整个项目建立统一的前缀
#server.servlet.context-path=/first
school.grade=6
school.classNum=9
#spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=1234
spring.datasource.url=jdbc:mysql://localhost:3306/springbootlearn?useSSL=false&useUnicode=true&\
  characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
```



#### 课程总结

##### 总结:Spring Boot入门

