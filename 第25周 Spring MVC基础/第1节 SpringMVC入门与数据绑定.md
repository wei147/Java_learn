## 第1节 SpringMVC入门与数据绑定

### 介绍

```
Spring MVC是Spring提供的Java web的开发模块，是整个在java web领域开发应用程序最重要的一个技术

Restful不是一种技术而是一种程序编码的风格，意在通过Http协议基础上对外暴露接口并且根据具体的请求返回对应的数据
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220811223917579.png" alt="image-20220811223917579" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220811224045517.png" alt="image-20220811224045517" style="zoom:50%;" />



#### Spring MVC介绍

```
在spring生态体系中，必须掌握的web应用框架————spring mvc
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220811235741323.png" alt="image-20220811235741323" style="zoom:50%;" />

##### Spring MVC介绍

```
所谓MVC是一种著名的架构模式（不是设计模式，是架构模式）

Model模型  View视图	 Controller控制器

View: 界面部分，用于和用户进行交互，比如通过界面显示查询结果、提供表单接收用户的输入信息
Model:通常是指我们的数据，其实放在Java代码中就是我们业务逻辑的部分
在MVC的设计理念中，比如View视图中的某个表格来自后端的Model中，那么并不是由视图自动的来提供Java调用Model中的某个方法得到数据，而是通过这个控制器Controller，控制器Controller就是一个中介。作为控制器是整个MVC中最重要的部分，它的用途就是接收视图中传入的数据然后再根据数据调用后端的业务逻辑得到结果，然后再通过控制器将后端的结果返回到视图中。也就是指视图和模型之间没有必然的连接关系，一切都是通过控制器和调用和返回。其实之前讲的Service类就是用于开发控制器的技术，但是Service中通过的这些方法使用起来有的时候并不是那么方便。正是因为Service类在开发中有很多不方便的地方，所以Spring这个机构就开发了Spring MVC提供了更简单的方式帮我们快速的完成了web应用的开发。也就是说作为Spring MVC这个框架来说它的主要用途就是帮我们简化web应用程序的开发。
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220812000102488.png" alt="image-20220812000102488" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220812002204489.png" alt="image-20220812002204489" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220812002402943.png" alt="image-20220812002402943" style="zoom:50%;" />

```
响应式编程：比如点击按钮时会触发一个单击的响应事件。如果放在后台编程中也有类似的理念，当触发某个事件的时候，自动的执行某个一段代码，这就是响应式编程
```



#### IDEA环境下创建Maven WebApp

idea基础的配置能通过tomcat运行网页程序



#### Spring MVC环境配置-1

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220812121148443.png" alt="image-20220812121148443" style="zoom:50%;" />

```xml
//1.引入spring MVC Maven依赖引入    
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
            <version>5.1.9.RELEASE</version>
        </dependency>
    </dependencies>
```

```xml
    <!--DispatchServlet的配置-->
//spring mvc的web.xml

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
</web-app>
```



#### Spring MVC环境配置-2