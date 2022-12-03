## 第2节 Spring Cloud开发课程查询功能

#### 课程项目整体介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221127003213442.png" alt="image-20221127003213442" style="zoom:50%;" />



#### Spring Cloud核心组件介绍

##### Spring Cloud简介

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221127003810876.png" alt="image-20221127003810876" style="zoom:40%;" />



#### 项目技术设计

```
我们的目标是实现一个最小粒度的慕课网的课程查询项目,一共包含两个模块 分别是课程列表模块和课程价格模块,,,,
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221127222333706.png" alt="image-20221127222333706" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221127222446930.png" alt="image-20221127222446930" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221127223921349.png" alt="image-20221127223921349" style="zoom:50%;" />

```
在项目整体设计中,主要包含了项目介绍,接口设计、数据流向和表设计。这些在未来工作中应该是必不可少的,也许简历可以加上这个?
```



#### 新建多模块项目

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221130115005555.png" alt="image-20221130115005555" style="zoom:80%;" />

#### 课程列表模块开发

```
每一个模块实际上是一个Spring Boot项目
```

<hr>

```java
//application.properties
server.port=8081
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/course_practice?serverTimezone=Asia/Shanghai&characterEncoding=utf-8&useSSL=true
spring.datasource.username=root
spring.datasource.password=1234

#日志配置 建议复制
logging.pattern.console=%clr\
  (%d{${LOG_DATEFORMAT_PATTERN:HH:mm:ss.SSS}}){faint} \
  %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- })\
  {magenta} %clr(---){faint} %clr([%15.15t]){faint} \
  %clr(%-40.40logger{39}){cyan} %clr(:){faint} \
  %m%n${LOG_EXCEPTION_CONVERSION_WORD:%wEx}

#把我们用下划线连接的转成驼峰式的 (避免有些实体类中的某些属性与数据表中的字段不匹配,拿不到值)
mybatis.configuration.map-underscore-to-camel-case=true

spring.application.name=course-list
```

```java
//CourseMapper.java  mapper接口
package com.imooc.course.dao;
import com.imooc.course.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程的Mapper类
 */
@Mapper
@Repository
public interface CourseMapper {
    //寻找所有上架的课程
    @Select("select * from course where valid=1")
    List<Course> findValidCourses();}
```

```java
//CourseListService.java 
package com.imooc.course.service;
import com.imooc.course.entity.Course;
import java.util.List;

/**
 * 课程服务实现类
 */
public interface CourseListService {
    List<Course> getCourseList();}
```

```java
//具体实现类
package com.imooc.course.service.Impl;
import com.imooc.course.dao.CourseMapper;
import com.imooc.course.entity.Course;
import com.imooc.course.service.CourseListService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 课程服务实现类
 */
@Service("courseListService")
public class CourseListServiceImpl implements CourseListService {

    @Resource
    CourseMapper courseMapper;

    @Override
    public List<Course> getCourseList() {
        return courseMapper.findValidCourses();}}
```

```java
package com.imooc.course.controller;
import com.imooc.course.entity.Course;
import com.imooc.course.service.CourseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * 课程列表的Controller
 */
@RestController
public class CourseListController {

    @Resource
    CourseListService courseListService;
    //提供一个课程列表的服务

    @GetMapping("/courses")
    public List<Course> courseList() {
        return courseListService.getCourseList();}}
```

#### 常见错误的排查

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221130231853199.png" alt="image-20221130231853199" style="zoom:100%;" />

```html
会发现courseId为null拿不到数据库的数据,为什么?
	因为实体类中的courseId与数据库的字段 course_id对不上。需要在application.properties配置文件中配置
        #把我们用下划线连接的转成驼峰式的
        mybatis.configuration.map-underscore-to-camel-case=true
```

以上完成第一个模块的编写。主要所做的功能是在一个多模块的Maven项目中新建几个子模块并且把这个子模块进行了开发

##### 课程列表模块开发-总结

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221130233140945.png" alt="image-20221130233140945" style="zoom:50%;" />



#### 课程价格模块开发

![image-20221201160445151](C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221201160445151.png)

```html
不然运行时会报错 		<text>java: 无效的目标发行版: 16 </text>
```



#### Eureka的作用和架构

```
要想去在多个服务之间建立他们的相互联系,那么第一步首先要知道对方在哪里,而要想知道对方在哪里不可避免的就需要用到我们的一些服务注册与发现的相关的模块,,,,

Eureka : 服务注册与发现的模块
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221202132309973.png" alt="image-20221202132309973" style="zoom:50%;" />

##### 为什么需要服务注册与发现

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221202140655488.png" alt="image-20221202140655488" style="zoom:50%;" />

```
使用静态ip的方式不可取,服务的注册在Eureka一旦改变可以通过Eureka来查询最新的ip
```

##### Eureka架构

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221202141418533.png" alt="image-20221202141418533" style="zoom:50%;" />

##### Eureka的集群架构

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221202141623631.png" alt="image-20221202141623631" style="zoom:50%;" />

```
多个Eureka的服务中心,服务中心彼此间是共享的,一台宕机其他还能用。也就是说客户端找到一台可用的服务中心就能远程调用其他服务
```

##### 总结三者间的职责以及关系

```


提供者(Service) 在启动的时候把自己注册到Eureka上。除了注册之外还需要负责去续约:比如定期发送心跳,告诉Eureka Server自己还活着。同样还需要负责服务下线,比如实例进行正常关闭了、业务变化导致机器缩减了,那么也要及时通知Eureka,告诉它我要下线了

消费者(Client) 第一步要获取服务。通过请求找到服务中心Eureka把服务清单/花名册拿过来  第二步根据这个清单找到它的信息并且去进行调用

Eureka服务注册中心  负责维护这个花名册。还有一个重要的功能是失效剔除,当某个服务提供方突然挂掉了,超过一定时间就把它剔除出去,
```



#### 开发Eureka Server

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221202143820693.png" alt="image-20221202143820693" style="zoom:50%;" />

```xml
在父pom.xml中引入

	<!--Spring Cloud开发课程查询功能/spring-cloud-course-pratice/pom.xml-->
    <!--表示Spring Cloud的版本  (dependencyManagement用于统一管理整个依赖)-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Greenwich.SR5</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>


	<!--Spring Cloud开发课程查询功能/spring-cloud-course-pratice/eureka-server/pom.xml-->
        <!--Eureka 的依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
```

```java
//application.properties
spring.application.name=eureka-server
server.port=8000
#eureka的主机名称
eureka.instance.hostname=localhost
#fetch-registry:获取注册表。 不需要同步其他节点数据。
eureka.client.fetch-registry=false
#register-with-eureka代表是否将自己注册到Eureka Server,默认是true。
eureka.client.register-with-eureka=false
eureka.client.service-url.defaultZone=http://${eureka.instance.hostname}:${server.port}/eureka/
```

```java
//com/wei/course/EurekaServerApplication.java
package com.wei.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 启动类   (Eureka服务端)
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);}}
```

```
访问  http://localhost:8000/ 即可看到页面
```



#### 进行Eureka Client改造

```xml
要想把模块变成Eureka Client需要两个比较主要的步骤:
	1.引入依赖
			<!--Spring Cloud开发课程查询功能/spring-cloud-course-pratice/course-service/course-list/pom.xml-->
	        <!--Eureka Client的依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
	2.进行配置文件的编写
		<!--Spring Cloud开发课程查询功能/spring-cloud-course-pratice/course-service/course-list/src/main/resources/application.properties-->
        #这里的地址要和 Eureka-server的配置地址对应 (spring-cloud-course-pratice/eureka-server/src/main/resources/application.properties)
        eureka.client.service-url.defaultZone=http://8000/eureka/

```

```
//具体改造
见上面。没了,比较简单
```



#### 利用Feign实现服务间调用

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221203211733633.png" alt="image-20221203211733633" style="zoom:50%;" />

##### 集成Feign的三个步骤

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221203211858221.png" alt="image-20221203211858221" style="zoom:50%;" />

```xml
1.引入依赖
	因为是在course-price里面调用course-list所以先在price里面引入依赖
		<!--Spring Cloud开发课程查询功能/spring-cloud-course-pratice/course-service/course-price/pom.xml-->
	    <!--Feign的依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
2.配置文件

3.注解
/**
 * 项目启动类
 */
@SpringBootApplication
<!--Feign的注解-->
@EnableFeignClients
@MapperScan("com.imooc.course.dao")
public class CoursePriceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoursePriceApplication.class,args); }}
```

```java
//spring-cloud-course-pratice/course-service/course-price/src/main/java/com/imooc/course/client/CourseListClient.java

//以接口的形式
package com.imooc.course.client;

import com.imooc.course.entity.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 课程列表的Feign客户端
 */
@FeignClient("course-list")
public interface CourseListClient {

    @GetMapping("/courses")
    List<Course> courseList();}
```

```java
//在CoursePriceController中调用课程列表的服务
package com.imooc.course.controller;
import com.imooc.course.client.CourseListClient;
import com.imooc.course.entity.Course;
import com.imooc.course.entity.CoursePrice;
import com.imooc.course.service.CoursePriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 课程价格控制器
 */
@RestController
public class CoursePriceController {

    @Resource
    CoursePriceService coursePriceService;

    @Resource
    CourseListClient courseListClient;

    //对外提供价格的能力
    @GetMapping("/price")
    public Float getCoursePrice(@RequestParam Integer courseId) {
        CoursePrice coursePrice = coursePriceService.getCoursePrice(courseId);
        return coursePrice.getPrice();}

    //在课程价格中调用课程列表的服务
    @GetMapping("/coursesInPrice")
    public List<Course> getCourseListInPrice() {
        return courseListClient.courseList();}}
```



#### 利用Ribbon实现负载均衡

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221203220050606.png" alt="image-20221203220050606" style="zoom:50%;" />

```
客户端负载均衡可以用在服务与服务间的
```

##### 负载均衡策略

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221203220737705.png" alt="image-20221203220737705" style="zoom:50%;" />

```
假设有1、2、3、4个

随机策略顾名思义。
轮询是1到4号都来一遍再回到1号
加权策略:会根据每一次请求的响应返回时间去决定我访问你的次数。其实就是相当于是一种判断、一种评估。
	假设有三台机器,第一台机器CPU很慢,二三台性能很好,前两个策略并不能体现出cpu的差异。(响应慢的话,分发给第一台机器的请求就会少)
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221203221744374.png" alt="image-20221203221744374" style="zoom:50%;" />

```java
//在spring-cloud-course-pratice/course-service/course-price/src/main/resources/application.properties 中配置

#采用轮询的负载均衡策略  ribbon
course-list.ribbon.NfLoadBanlancerRuleClassName=com.netflix.loadbalancer.RoundRobinRule
```

#### 利用Hystrix实现断路器

##### 为什么需要断路器



