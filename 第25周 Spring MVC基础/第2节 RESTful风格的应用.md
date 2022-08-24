## 第2节 RESTful风格的应用

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220822232304176.png" alt="image-20220822232304176" style="zoom:50%;" />

```
Rest与Restful
Rest - REST-表现层状态转换，资源在网络中以某种表现形式进行状态转移	（翻译：在我们web环境下，如果要获取某个图片或者js、网页这些资源的时候那么就要以url的形式来进行表现，我们要访问一个图片的网址，那这个资源返回的就自然是一张图片；如果访问的是一个css，那么返回的就是一个css）   rest是理念不是具体的实现
RESTful - 是基于REST理念的一套开发风格，是具体的开发规则

而Restful开发风格下，我们并不拘泥于客户端必须是浏览器。那客户端和服务器之间如何进行交互呢？
答：比如iPhone中有一个小程序向url（http://xxx.com/list）发送了一个请求,而这个请求被发送到web端的服务器，那请求在被处理之后，关键的区分来了：作为服务器端返回的不再是某个html的文本，而是像json、xml这样的数据。作为Restful最典型的特征就是我们服务器端只返回数据。这种数据以 json或者xml来进行体现，同时返回的数据不包含如何与展现相关的内容，当这些数据被送回给客户端以后，那么再由这个客户端对这些数据进行渲染和展现。比如当pc端的浏览器接收到这个json以后是以一个表格的形式在浏览器中进行展现，而iPhone或者安卓这种移动端的小屏幕的话可能会以滑动列表的方式进行展现，如何展现就是客户端的事情了。作为服务器不管你客户端使用的是小程序、app还是浏览器，我只管专注产生数据就行了。至于数据以什么样的形式展示出来，那是客户端的事情。
这样做最大的好处就是 我们开发服务器程序的后端工程师只用专注数据不用关注任何展现，而前端可以有小程序的工程师、app的开发工程师，那么每一个工程师也不用关心后台是如何产生数据的，只需要拿到这个字符串进行解析就行了。在开发过程中前端工程师和后端工程师可以同步进行——只要我们约定好传输数据的格式是什么以及url就可以了。通过基于Restful开发风格所编写的程序，在我们行业中还有一个名词叫做 前后端分离。 前端只负责界面开发，后端只需要专注业务逻辑就可以了
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220823000719832.png" alt="image-20220823000719832" style="zoom:50%;" />

```
RESTful开发规范
使用URL作为用户交互入口
明确的语义规范(GET | POST | PUT | DELETE)  [get对应了查询，post对应新增操作，put对应了更新操作，delete对应删除操作]
只返回数据（JSON | XML）,不包含任何展现	[推荐Json]
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220823001510240.png" alt="image-20220823001510240" style="zoom:50%;" />

![image-20220823001550625](C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220823001550625.png)

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220823001510240.png" alt="image-20220823001510240" style="zoom:50%;" />

```
Restful在命名要求上主要是针对URI	[URI:统一资源标识符。网址URl刨去域名后面的部分就是uri了]
```



#### 开发第一个Restful应用

##### 开发Restful Web应用

```
src\main\webapp\WEB-INF\web.xml  版本号改为3.1
```

基本配置

```xml
pom.xml
<repositories>
    <repository>
        <id>aliun</id>
        <name>aliyun</name>
        <url>https://maven.aliyun.com/repository/public</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.0.RELEASE</version>
    </dependency>
</dependencies>
```

```xml
web.xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">

    <!--DispatchServlet 是springMVC最重要的一个类。它的作用是对所有请求进行拦截-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <!--DispatcherServlet是Spring MVC最核心的对象
            DispatcherServlet用于拦截Http请求，
            并根据请求的URL调用与之对应的Controller方法，来完成Http请求的处理-->
        <!--[次要]DispatcherServlet就是一个中转站、前台。这个前台是外部环境和公司内部人员的一个对接窗口，
        所有的进和出都要通过这个前台来完成。而Controller可以看做是公司内部的市场部同事-->
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--applicationContext.xml-->
        <init-param>
            <param-name>contextConfigLocation</param-name>   <!--上下文的配置路径所在-->
            <param-value>classpath:applicationContext.xml</param-value>
        </init-param>
        <!--在Web应用启动时自动创建Spring IOC容器，（为什么要创建Spring IOC容器? 因为spring mvc所有的底层对象都必须依赖于spring ioc容器）
            并初始化DispatcherServlet   -->
        <load-on-startup>0</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <!-- "/"代表拦截所有请求-->
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--filter是我们J to EE中的标准组件，用于对请求进行过滤-->
    <filter>
        <!--字符编码过滤器  主要作用将post请求中的原先的编码改为UTF-8-->
        <filter-name>characterFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </filter>
    <!--使字符过滤器生效，需要搞一个映射-->
    <filter-mapping>
        <filter-name>characterFilter</filter-name>
        <!--对所有URL进行拦截-->
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--2022年8月23日11:58:48 注：characterFilter比DispatcherServlet 优先执行-->
</web-app>
```

```xml
applicationContext.xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mv="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context
            http://www.springframework.org/schema/context/spring-context.xsd
            http://www.springframework.org/schema/mvc
            http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!-- 注解的形式开发  扫描包-->
    <context:component-scan base-package="com.imooc.restful"/>
    <!--开启SpringMVC的注解模式-->
    <mvc:annotation-driven>
        <!--用来设置消息的转换器，在这里配置的转换器可以对响应中的消息进行调整  解决响应中的中文乱码-->
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <!--设置响应的、输出的字符集以及他的media类型-->
                <property name="supportedMediaTypes">
                    <list>
                        <value>text/html;charset=utf-8</value>
                         <!--响应一但产生json，会设置编码为utf-8-->
                        <value>application/json;charset=utf-8</value>
                    </list>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
    <!--将静态资源排除在外-->
    <mvc:default-servlet-handler/>
</beans>
```

```java
//RestfulController
package com.imooc.restful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//Restful风格的程序

@Controller
@RequestMapping("restful")
public class RestfulController {
    @GetMapping("/request")
    @ResponseBody       //如果不写这个注解则代表这是进行页面的跳转。加了则代表直接向客户端输出结果
    public String doGetRequest() {
        return "{\"message\":\"返回查询结果\"}";
    }
}
```

```
Restful是一个编码风格

http://localhost/restful/request
以html为例，页面运行过程中想得到这个url所返回的数据的话，该用什么数据？
答：使用ajax，ajax可以在我们页面中异步的与url发送请求，同时得到响应的结果  （下一节）
```



#### 开发Restful Web应用

```java
//RestfulController.java
package com.imooc.restful.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//Restful风格的程序

@Controller
@RequestMapping("/restful")
public class RestfulController {
    @GetMapping("/request")
    @ResponseBody       //如果不写这个注解则代表这是进行页面的跳转。加了则代表直接向客户端输出结果
    public String doGetRequest() {
        return "{\"message\":\"返回查询结果\"}";}

    @PostMapping("/request")
    @ResponseBody
    public String doPostRequest(){
        return "{\"message\":\"数据新建成功\"}";}

    @PutMapping("/request")
    @ResponseBody
    public String doPutRequest(){
        return "{\"message\":\"数据更新成功\"}";}

    @DeleteMapping("/request")
    @ResponseBody
    public String doDeleteRequest(){
        return "{\"message\":\"数据删除成功\"}";}}

```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Restful实验室</title>
    <script src="jquery-3.3.1.min.js"></script>
    <script>
        $(function () {
            $("#btnGet").click(function () {
                $.ajax({
                    url: "/restful/request",
                    type: "get",
                    dataType: "json",
                    success: function (json) {
                        $("#message").text(json.message)} })})})

        $(function () {
            $("#btnPost").click(function () {
                $.ajax({
                    url: "/restful/request",
                    type: "post",
                    dataType: "json",
                    success: function (json) {
                        $("#message").text(json.message)}})})})
        ......

    </script>
    <!--  //绑定按钮并添加点击事件    //ajax函数向服务器发送一个异步请求   //通过id得到还h4这个对象   text()用来设置内部的文本    json.message指向的是 json的message key-->
</head>
<body>
<h5>restful请求的发起者 客户端</h5>
<!--点击按钮，利用ajax向服务器发送http请求-->
<input type="button" id="btnGet" value="发送Get请求">
<input type="button" id="btnPost" value="发送Post请求">
<input type="button" id="btnPut" value="发送Put请求">
<input type="button" id="btnDelete" value="发送Delete请求">
<h4 id="message"></h4>
</body>
</html>
```



#### RestController注解与路径变量

```
Controller控制器每一个方法上面都要添加一个 @ResponseBody 会不会显得比较麻烦？
所以在spring4以后，提供了一个新的Controller注解
```

```java
//Restful风格的程序

//@Controller
@RestController //默认当前返回的都是rest形式的数据，而不是页面的跳转。加了这个就不用加 @ResponseBody
@RequestMapping("/restful")
public class RestfulController {
    @GetMapping("/request")
//    @ResponseBody       //如果不写这个注解则代表这是进行页面的跳转。加了则代表直接向客户端输出结果
    public String doGetRequest() {
        return "{\"message\":\"返回查询结果\"}";
    }

    // POST /article/1  创建一个id值为1的文章  1是灵活的是变化的，可以是2,3
    // 这种放在uri的变量就称为 路径变量
    // POST /request/restful/100 在服务器端怎么接收到这个100? 它并不是请求的参数，它是uri中的一部分。好在SpringMVC为我们提供了路径变量的支持
    // 可以匹配到rid的数值会被自动注入到 requestId中
    @PostMapping("/request/{rid}")
//    @ResponseBody
    public String doPostRequest(@PathVariable("rid") Integer requestId){
        return "{\"message\":\"数据新建成功\",\"id\":"+ requestId +"}";
    }
```

```javascript
$(function () {
    $("#btnPost").click(function () {
        $.ajax({
            url: "/restful/request/100",
            type: "post",
            dataType: "json",
            success: function (json) {
                $("#message").text(json.message+":"+json.id);}})})})
```



#### 简单请求与非简单请求

```
什么是简单请求  什么是非简单请求？

如果是简单的请求，我们直接把请求发过来处理就完事了。如果是非简单请求的话，首先会发送一个预检请求，预检请求的作用是让服务器返回当前这个请求能不能被正常的处理。如果服务器接收了这个请求并返回一个能进行处理。之后再由浏览器发送实际请求给服务器进行处理

形象：	简单请求可以看做是送较为廉价的快递，快递员不会询问你在不在家，而是直接安排蜜蜂箱。非简单请求可以看做是较贵的或者顺丰快递，会先打电话询问在不在家，再给送快递。
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220824203017664.png" alt="image-20220824203017664" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220824203802210.png" alt="image-20220824203802210" style="zoom:50%;" />

```
如果接收一系列数据的话，最好的方式就是增加实体对象
```

```javascript
在post请求和put请求中加入 data字段。 （post是简单请求，put是非简单请求）
$("#btnPost").click(function () {
                $.ajax({
                    url: "/restful/request/100",
                    type: "post",
                    data:"name=wei&age=21",
                    dataType: "json",
                    
$("#btnPut").click(function () {
    $.ajax({
        url: "/restful/request",
        type: "put",
        data:"name=wei&age=21",
        dataType: "json",
```

```java
    @PostMapping("/request/{rid}")
//    @ResponseBody
    public String doPostRequest(@PathVariable("rid") Integer requestId,Person person){
        System.out.println(person.getName()+person.getAge());
        return "{\"message\":\"数据新建成功\",\"id\":"+ requestId +"}";}

    @PutMapping("/request")
//    @ResponseBody
    public String doPutRequest(Person person){
        System.out.println(person.getName()+person.getAge());
        return "{\"message\":\"数据更新成功\"}";}

post能正常获取到前端传过来的数据，put请求则不能。为什么会这样？
    答：涉及到一个历史问题，作为最早的SpringMVC当然是为我们网页服务的，默认网页在表单提交的时候只支持post和get这两种请求，对于put和delete这两种特殊请求是不支持的。但是随着技术的演进，put和delete作为Spring MVC是必须要考虑的。但SpringMvc又不能把put和delete请求的处理方式强塞进原有的代码中，所以SpringMVC做了一个折中的方案：作为put和delete这两种非简单请求，springMVC提供了一个额外的表单内容过滤器来对put和delete进行额外处理。具体写法是：在web.xml中配置表单内容过滤器 见下文
```

```xml
    <filter>
        <!--表单内容过滤器  (利用这个过滤器对put和delete请求进行支持）
        写好这个之后，put和delete请求就能完美的支持参数的获取
        作为FormContentFilter其实是对springMVC能力的拓展-->
        <filter-name>formContentFilter</filter-name>
        <filter-class>org.springframework.web.filter.FormContentFilter</filter-class>
    </filter>
    <!--要增加filter mapping对url进行过滤  （默认对所有请求地址进行过滤）-->
    <filter-mapping>
        <filter-name>formContentFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```



#### JSON序列化

