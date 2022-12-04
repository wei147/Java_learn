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

```
断路器最典型的一个用处就是 当某一个单元发生故障的时候,然后我们就可以利用这个断路器把它给隔离出去,类似于我们的电路,我们的电路如果说某一处流量过大,为了保护整个系统它会有保险丝这样的装置,保险丝断了一部分内容是不可用的,但不会因此导致整个系统不可用,
```

```
Hystrix所起到的作用就是帮助我们快速优雅的构建一个短路的功能。当某个服务发生问题的时候,我们会返回一个默认的响应或者是一个错误的响应而不是长时间的让用户去等待,这样就避免了线程因为故障而无法释放,避免了在分布式系统中故障的蔓延,
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221204135121293.png" alt="image-20221204135121293" style="zoom: 50%;" />

```
编码:
课程价格服务会调用课程列表服务。courselist(返回课程列表)服务我们无法保证该服务是否可用,所以需要在课程价格上添加断路器功能
```

```xml
1.引入依赖 
<!--Spring Cloud开发课程查询功能/spring-cloud-course-pratice/course-service/course-price/pom.xml -->
<!--Hystrix 断路器的依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>

2.配置文件
<!--Spring Cloud开发课程查询功能/spring-cloud-course-pratice/course-service/course-price/src/main/resources/application.properties -->
#默认断路器功能是不打开的
feign.hystrix.enabled=true

3.在启动类加上注解(见下文)
4.指明短路后续操作(在断路的时候应该怎么做)  加上fallback和后续的接口实现类
```

```java
3.在启动类加上注解(见下文)
/**
 * 项目启动类
 */
@SpringBootApplication
@EnableFeignClients
//断路器的注解
@EnableCircuitBreaker
@MapperScan("com.imooc.course.dao")
public class CoursePriceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoursePriceApplication.class,args);}}
```

```java
package com.imooc.course.client;
import com.imooc.course.entity.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

/**
 * 课程列表的Feign客户端
 */
//fallback就是发生错误时所要调用的类。正常是调用本接口的方法,发生错误的话调用CourseListClientHystrix实现类
@FeignClient(value = "course-list",fallback = CourseListClientHystrix.class)
public interface CourseListClient {

    @GetMapping("/courses")
    List<Course> courseList();}
```

```java
//断路后应该怎么做
//spring-cloud-course-pratice/course-service/course-price/src/main/java/com/imooc/course/client/CourseListClient.java
package com.imooc.course.client;
import com.imooc.course.entity.Course;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * 断路器实现类
 */
@Component      //这个是必须加的吗
public class CourseListClientHystrix implements CourseListClient {
    @Override
    public List<Course> courseList() {
        List<Course> defaultCourses = new ArrayList<>();
        Course course = new Course();
        course.setId(108);
        course.setCourseId(108);
        course.setName("默认课程");
        course.setValid(1);
        defaultCourses.add(course);
        return defaultCourses; }}
```



#### 整合两个服务

```
将课程列表和课程价格合并为一个json输出
```

```java
最终效果
[{
"id": null,
"courseId": 362,
"price": 348,
"name": "Java并发核心知识体系精讲"
},
{
"id": null,
"courseId": 409,
"price": 399,
"name": "玩转java并发工具"
},
{
"id": null,
"courseId": 121,
"price": 299,
"name": "Nginx入门到实践-Nginx中间件"
}]
```

```java
CoursesAndPrice实体类
/**
 * 课程与价格的融合类(整合了课程列表和价格的类)
 */

//为什么要实现Serializable接口?  序列化：就是把对象转化成字节。
public class CoursesAndPrice implements Serializable {
    Integer id;
    Integer courseId;
    Float price;
    String name;
```

```java
//CoursePriceServiceImpl 具体实现类调用课程列表服务进行数据的整合    

//课程列表从课程列表服务中拿
@Resource
CourseListClient courseListClient;

@Override
    public List<CoursesAndPrice> getCoursesAndPriceList() {
        List<CoursesAndPrice> coursesAndPriceList = new ArrayList<>();
        List<Course> courses = courseListClient.courseList();
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            if (course != null) {
                Integer courseId = course.getCourseId();
                CoursePrice coursePrice = coursePriceMapper.getCoursePrice(courseId);
                CoursesAndPrice coursesAndPrice = new CoursesAndPrice();
                coursesAndPrice.setCourseId(courseId);
                coursesAndPrice.setPrice(coursePrice.getPrice());
                coursesAndPrice.setName(course.getName());
//                coursesAndPrice.setId(i);

                coursesAndPriceList.add(coursesAndPrice);
            }
        }
        return coursesAndPriceList; }
```

```java
//控制器 CoursePriceController

//整合两个服务
@GetMapping("/coursesAndPrice")
public List<CoursesAndPrice> getCoursesAndPrice() {
    return coursePriceService.getCoursesAndPriceList();}
```

```
以远程调用服务的方式还是挺新颖的。  步骤:
Eureka服务注册中心、
Eureka Client、
通过Feign实现服务间调用、
Ribbon实现负载均衡策略————轮询、
Hystrix实现断路器兜底(用户体验更好)、
最后是整合两个服务、
```



#### 网关Zuul

```
网关在大型项目中是必不可少的一个部分。

Spring Cloud Zuul与Spring Cloud:
	Spring Cloud Zuul是Spring cloud的一个组件。Spring Cloud Zuul会和Eureka进行整合,它自己也是Eureka的一个Client,会注册到Eureka上面去,它也可以由此通过Eureka获取到各个其他微服务模块的信息。有了这些信息之后,我们网关就会自动的去把这些信息在网关自身上进行注册,
```

##### 为什么需要网关？

```
如果没有网关做统一的校验,那么很多模块就要反复验证该用户是不是登录了。登录校验冗余
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221204225225495.png" alt="image-20221204225225495" style="zoom:40%;" /> 

```
用户会先访问到Zuul网关,之后再由网关路由到服务
网关最重要的两个功能:
	1.统一鉴权
	2.正确路由
```

##### 集成Zuul

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221204225808120.png" alt="image-20221204225808120" style="zoom:50%;" />

```xml
1.引入依赖
        <!--因为Zuul网关本身作为Eureka Client会在Eureka服务中心进行注册所以引入这个依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!--Zuul网关的依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>
```

```java
2.网关的主类
package com.wei.course;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关启动类
 */
@EnableZuulProxy
@SpringCloudApplication
public class ZuulGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);}}
```

```yml
3.配置文件的编写
spring.application.name=eureka-gateway
server.port=9000

#日志配置 建议复制
logging.pattern.console=%clr\
  (%d{${LOG_DATEFORMAT_PATTERN:HH:mm:ss.SSS}}){faint} \
  %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- })\
  {magenta} %clr(---){faint} %clr([%15.15t]){faint} \
  %clr(%-40.40logger{39}){cyan} %clr(:){faint} \
  %m%n${LOG_EXCEPTION_CONVERSION_WORD:%wEx}
#把我们用下划线连接的转成驼峰式的 (避免有些实体类中的某些属性与数据表中的字段不匹配,拿不到值)
mybatis.configuration.map-underscore-to-camel-case=true
#这里的地址要和 Eureka-server的配置地址对应 (spring-cloud-course-pratice/eureka-server/src/main/resources/application.properties)
eureka.client.service-url.defaultZone=http://localhost:8000/eureka/
```

```
http://localhost:9000/course-price/price?courseId=121
```

