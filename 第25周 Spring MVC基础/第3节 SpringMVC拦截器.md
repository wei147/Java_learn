## 第3节 SpringMVC拦截器



#### Interceptor拦截器入门

```
SpringMVC的高级组件 ———— 拦截器

Interceptor拦截器是springMVC的标准组件，Interceptor对象被创建以后是天然的运行在Spring ioc容器中的。而Filter则是J to EE的标准组件

```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220826142309462.png" alt="image-20220826142309462" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220826142437166.png" alt="image-20220826142437166" style="zoom:50%;" />

```java
package com.imooc.restful.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//拦截器
//作为这个类要实现一个接口,拦截器必须要实现的接口。这个接口包含了三个接口要进行实现
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        return HandlerInterceptor.super.preHandle(request, response, handler);}

    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);}

    @Override
    public void afterCompletion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }}

```

```
preHandle	前置执行处理。在一个请求产生以后，还没有进controller之前要先执行preHandle，对这个请求进行预置处理
postHandle	目标资源已被SpringMVC框架处理。如果放在controller中，postHandle的执行时机就是在我们内部方法return以后，但是并没有产生响应文本之前
afterCompletion	响应文本已经产生

注：要让SpringMVC认识这个拦截器需要到application.xml中进行配置
```

```xml
application.java
<!--拦截器的配置-->
<mvc:interceptors>
    <mvc:interceptor>
        <!--拦截器需要对哪些地址进行拦截      (这里是对所有的请求进行拦截)-->
        <mvc:mapping path="/**"/>
        <!--指明是哪个类对拦截的url进行处理-->
        <bean class="com.imooc.restful.interceptor.MyInterceptor"></bean>
    </mvc:interceptor>
</mvc:interceptors>
```



#### Interceptor使用技巧

##### 拦截器使用细则

```xml
http://localhost:8080/准备执行
http://localhost:8080/目标处理成功
http://localhost:8080/响应内容已产生
http://localhost:8080/favicon.ico准备执行
http://localhost:8080/favicon.ico目标处理成功
http://localhost:8080/favicon.ico响应内容已产生

在拦截器上，将这些不需要的url（比如静态资源：js）排除在外？ 通过配置文件 application.xml
有一个问题就是这样一个个配置会显得比较多，有没有更优解？如何对静态资源和controller应用进行有效的区分？
答：有排除法、选择法以及规范命名法

    <!--拦截器-->
    <mvc:interceptors>
        <mvc:interceptor>
            <!--拦截器需要对哪些地址进行拦截      (这里是对所有的请求进行拦截)-->
            <mvc:mapping path="/restful/**"/>       <!--反向思维，对所需要的url进行拦截-->
            <mvc:mapping path="/webapi/**"/>
            <!--exclude-mapping代表要排除的地址-->
            <mvc:exclude-mapping path="/**.ioc"/>
            <mvc:exclude-mapping path="/**.gif"/>
            <mvc:exclude-mapping path="/**.jpg"/>
            <mvc:exclude-mapping path="/**.js"/>
            <mvc:exclude-mapping path="/**.css"/>
            <!--项目中的常用做法 ：通过规范静态资源目录来一次性把所有的静态资源排除在外边-->
            <mvc:exclude-mapping path="/resources/**"/>
            <!--指明是哪个类对拦截的url进行处理-->
            <bean class="com.imooc.restful.interceptor.MyInterceptor"></bean>
        </mvc:interceptor>
    </mvc:interceptors>

问题：一个请求被多个拦截器同时过滤的话，到底哪个先执行？ 运行结果如下：

http://localhost:8080/restful/persons准备执行
http://localhost:8080/restful/persons准备执行 -1
RestfulController.findPersons - return -list
http://localhost:8080/restful/persons目标处理成功 -1
http://localhost:8080/restful/persons目标处理成功
http://localhost:8080/restful/persons响应内容已产生 -1
http://localhost:8080/restful/persons响应内容已产生

前面是按拦截器配置的顺序依次进行————准备执行 -1在后，后面是准备执行 -1对应的在后面。为什么？
答：涉及到请求和响应的流向问题   请看下图。 当controller处理完请求后，会触发后置处理，响应返回的过程中是倒序执行，就产生了以上的结果
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220827000859130.png" alt="image-20220827000859130" style="zoom:50%;" />

```java
//拦截器
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURL()+"准备执行");
        //如果这里是return true,那我们的请求就会被送达给后面的拦截器或者控制器。
        //return false的话，当前的请求就会被阻止，直接产生响应，返回客户端了。
        //这个return的结果，决定了我们的请求是继续向后执行还是立即结束返回响应
        return false;		//如果这里是return false，预计： 准备执行  	- return -list	响应内容已产生
        //控制台只打印了  http://localhost:8080/restful/persons准备执行  (上面的预计错误)
    }
    
    //return false 更像是一个阻断器，原本应该执行的所有请求和后续处理都会被阻断，同时响应在当前方法中直接产生了
```

```java
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURL()+"准备执行");
       //可以对于某个url进行前置检查，通过就直接放行；不通过的在拦截器中进行处理，直接进行响应
        response.getWriter().print("[不通过]");
        return false;
    }
```



#### 开发"用户流量拦截器"



```
顾名思义就是在用户访问我们应用的时候，自动的对用户的一些底层信息进行收集。比如说 访问的时间、访问的网址、用户到底是用什么浏览器用什么系统什么手机进行访问的、用户的IP是是什么

本次案例把注意力放在如何使用拦截器来实现对用户基础数据的采集，同时我们也将使用到如何用logback日志组件来对用户数据进行日志存储		这里使用logback
```

```java
//访问历史拦截器
//所有请求在被处理之前，要被这个拦截器所记录，所以这属于一个前置处理
public class AccessHistoryInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //当拿到了用户请求以后，应该把数据存放在哪？通常这些用户访问的额外信息，我们会通过日志文件来单独进行保存
		return true;}
```

```xml
<!--logback 日志组件，将其引入以后，SpringMVC默认就可以使用logback对日志进行输出-->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.3</version>
</dependency>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--logbcak的配置文件，必须这样命名-->
<configuration>
    <!--追加器。 我们要定义一个向控制台书写日志的追加器-->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <!--输出日志的格式-->
        <encoder>
            <!--分别代表了线程名 日期 级别 logger{10}由哪个类产生的日志，
            如果类名太长进行相应的压缩（10对应了包名最大容许的长度） msg是日志的内容 n是换行-->
            <pattern>[%thread] %d %level %logger{10} - %msg%n</pattern>
        </encoder>
    </appender>

    <!--如何在logback中产生日志文件？  RollingFileAppender生成按天滚动的日志文件-->
    <appender name="accessHistoryLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!--滚动策略  TimeBasedRollingPolicy按照时间进行滚动-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--日志文件保存在哪  %d日期插入到文件名中-->
            <fileNamePattern>d:/project/Jave_learn/第25周 Spring MVC基础/第3节 SpringMVC拦截器/logs/history.%d.log
            </fileNamePattern>
        </rollingPolicy>
        <!--日志显示沿用格式-->
        <encoder>
            <pattern>[%thread] %d %level %logger{10} - %msg%n</pattern>
        </encoder>
    </appender>


    <!--在当前日志中，最低容许输出debug级别的日志-->
    <root level="debug">
        <!--对上面定义好的console 进行引用-->
        <appender-ref ref="console"/>
    </root>

    <!--当前这个类中所产生的日志，都会使用这个标签所描述的规则-->
    <!--[次要] additivity="false" 单词有叠加的意思。为false的话只会向指定的RollingFileAppender文件中来进行输出-->
    <logger name="com.imooc.restful.interceptor.AccessHistoryInterceptor" level="INFO" additivity="false">
        <!--说明 AccessHistoryInterceptor 使用accessHistoryLog输出器对文件进行输出。并说明保存的地址-->
        <appender-ref ref="accessHistoryLog"></appender-ref>
    </logger>
</configuration>
```

```xml
<!--
[RMI TCP Connection(3)-127.0.0.1] 2022-08-27 16:36:32,264 DEBUG o.s.w.f.CharacterEncodingFilter - Filter 'characterFilter' configured for use
[RMI TCP Connection(3)-127.0.0.1] 2022-08-27 16:36:33,773 INFO o.s.w.s.DispatcherServlet - Initializing Servlet 'springmvc'  -->
<pattern>[%thread] %d %level %logger{10} - %msg%n</pattern>   设置好的格式会在控制台/日志文件中输出
```

```java
//AccessHistoryInterceptor.java
package com.imooc.restful.interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//访问历史拦截器
//所有请求在被处理之前，要被这个拦截器所记录，所以这属于一个前置处理
public class AccessHistoryInterceptor implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(AccessHistoryInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //当拿到了用户请求以后，应该把数据存放在哪？通常这些用户访问的额外信息，我们会通过日志文件来单独进行保存
        //对内容进行输出和打印 这个内容应该包含哪些信息？
        StringBuffer log = new StringBuffer();
        log.append(request.getRemoteAddr());    //远程用户的IP地址
        log.append("|");
        log.append(request.getRequestURL());    //用户访问的url网址
        log.append("|");
        log.append(request.getHeader("user-agent"));    //用户的环境
        logger.info(log.toString());
        return true;}}
```

```xml
        //applicationContext.xml中的拦截器配置
        <!--用户流量拦截器-->
        <mvc:interceptor>
            <mvc:mapping path="/**"/>
            <mvc:exclude-mapping path="/resources/**"/>
            <bean class="com.imooc.restful.interceptor.AccessHistoryInterceptor"></bean>
        </mvc:interceptor>
```



课后题目: 如何将文件中的信息，按ip、用户访问网址、用户环境一一截取出来   （java截取文本的基本功）



#### Spring MVC处理流程

```
Spring MVC的底层的执行原理以及数据的处理流程（程序运行背后作了什么事情有所了解）

Handler 也叫处理器，在计算机中也叫做句柄。
在下图中，Handler可能是一个拦截器也可能是一个控制器
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828174651817.png" alt="image-20220828174651817" style="zoom: 50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828175406530.png" alt="image-20220828175406530" style="zoom:50%;" />





