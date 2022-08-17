

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

```xml
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
    <!--组件扫描-->
    <!--context:component-scan 标签作用
        在Spring IOC初始化过程中。自动创建并管理com.springmvc及子包中
        拥有以下注解的对象。
        @Repository     （与数据发生直接交互的类 Dao类）
        @Service        （业务逻辑类 xxxService）
        @Controller     （用于描述Spring mvc的控制器类）
        @Component      （不清楚类型可以使用@Component）
    -->
    <context:component-scan base-package="com.imooc.springmvc"/>
    <!--启用Spring MVC的注解开发模式-->
    <mvc:annotation-driven/>
    <!--将图片/JS/CSS等静态资源排除在外，可提高执行效率-->
    <mvc:default-servlet-handler/>
</beans>
```

记得将依赖放入WEB-INF/lib中

```java
//TestController.java
package com.imooc.springmvc.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//处理http的请求并且返回响应
@Controller
public class TestController {
    @GetMapping("/t")    //@GetMapping() 将当前这个方法绑定某个get方式请求的url   localhost/t
    @ResponseBody           //直接向响应输出字符串数据（提供数据），不跳转页面
    public String test(){
        return "Success ! 嗨嗨害"; }}
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220813000338268.png" alt="image-20220813000338268" style="zoom:50%;" />



#### URLMapping(URL映射)

##### Spring MVC数据绑定

```
在Spring MVC中如何进行数据绑定？
	1.1什么是数据绑定？ 答：spring MVC中我们Controller控制器的某一个方法是如何和URL绑定在一起的
	
URLMapping ：spring mvc中最重要也是最基础的
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220813001304743.png" alt="image-20220813001304743" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220813001401921.png" alt="image-20220813001401921" style="zoom:50%;" />

```java
package com.imooc.springmvc.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/um")    //RequestMapping通常放在类上面 （这个注解在大多数情况下是用于进行url的全局设置的）/um/g 访问前缀
public class URLMappingController {

//    @GetMapping("/g")
    @RequestMapping(value = "/g",method = RequestMethod.GET)   //用于模拟get方法。（也可以是其他方法）
//    @RequestMapping("/g")   //作用在方法上，不再区分get/post请求
    @ResponseBody
    public String getMapping() {
        return "This is get method";
    }
    @ResponseBody
    @PostMapping("/p")
    public String postMapping() {
        return "This is post method";}}
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<!--<h2>test page!</h2>-->
<form action="/p" method="post">
    <input type="submit" value="提交">
</form>
</body>
</html>
```

#### Controller方法参数接收请求参数

##### 接收请求参数

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220813004525314.png" alt="image-20220813004525314" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220813004724952.png" alt="image-20220813004724952" style="zoom:50%;" />

```java
@Controller
@RequestMapping("/um")    //RequestMapping通常放在类上面 （这个注解在大多数情况下是用于进行url的全局设置的）/um/g 访问前缀
public class URLMappingController {

    //@GetMapping("/g")
    @RequestMapping(value = "/g", method = RequestMethod.GET)   //用于模拟get方法。（也可以是其他方法）
    //@RequestMapping("/g")   //作用在方法上，不再区分get/post请求
    @ResponseBody
    //@RequestParam专用于这种特殊的参数来进行描述,后面跟进行映射的原始参数。请求中的manager_name这个参数在运行时会被动态的注入到managerName参数中		http://localhost:8081/um/g?manager_name=wei
    public String getMapping(@RequestParam("manager_name") String managerName) {
        System.out.println(managerName);  //wei
        return "This is get method";}

    @ResponseBody
    @PostMapping("/p")
    public String postMapping(String username, Long password) {
        // 报400错误可能是前端表单校验不够严谨造成的
        System.out.println("用户名: " + username + "   密码: " + password);
        return "This is post method， 登录成功";}}
```



#### Controller实体对象接收请求参数

##### 使用Java Bean 接收请求参数

```
当输入数量多的时候，一个新的问题产生了————难道要把100个输入项一个个都列举出来吗？
结果是 方法声明的部分很长，而且不利于维护，也可以打包成一个对象但是每一次取出来都需要object.getParameter 比较麻烦。(大表单使用实体对象，缺点是需要创建一个实体类)

为了解决上述问题，Spring MVC允许我们一次性的将我们前台输入的数据保存为指定的Java bean，一步到位完成了由数据到对象的转换工作  （用实体对象来接收）	    
	@ResponseBody
    @PostMapping("/p1")
    public String postMapping1(User user){}
```

```java
//URLMappingController.java
// 在程序运行时，spring mvc就会自动的创建User这个对象，并且根据前面的请求结合实体类中的参数名来进行一一的自动赋值以及类型转换的工作 （注：实体类属性名和前台传入的参数名字保持一致）
@ResponseBody
@PostMapping("/p1")
public String postMapping1(User user,String username){      //String username 一样会被赋值
    System.out.println(user);
    System.out.println(user.getUsername() + "  "+user.getPassword());
    return "ok ,it's good. This is post method";}
```

```java
//User.java  实体类
package com.imooc.springmvc.entity;
public class User {
    private String username;
    private Long password;
    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}
    public Long getPassword() {return password;}
    public void setPassword(Long password) {this.password = password;}}
```

```html
//index.html
<!--<h2>test page!</h2>-->
<form action="/um/p1" method="post">
    <input name="username"><br/>
    <input name="password">
    <input type="submit" value="提交">
</form>
```



#### 综合训练：学员调查问卷

#### 接收表单复合数据（上）

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220814225824017.png" alt="image-20220814225824017" style="zoom:50%;" />

```
复合数据的获取
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220814230629151.png" alt="image-20220814230629151" style="zoom:50%;" />

#### URI绝对路径和相对路径

```
URI是url的一个子集，说白了就是把主机和端口前半部分去掉，只留下后面我们对应资源的路径。

相对路径：以当前的文件的层级作为依据寻找其他的资源	（相对路径的好处是不用考虑上下文路径问题，只需要与form。html同级就行）
绝对路径：完整路径
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220815211029946.png" alt="image-20220815211029946" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220815211917008.png" alt="image-20220815211917008" style="zoom:50%;" />

```java
接收复合数据在spring mvc中如何做到？	spring mvc提供了两种方案
1.一种是数组进行接收
2.一种使用ArrayList进行接收

随着编程的不断增加，数组其实用得越来越少了，因为在java中提供了更加有效的管理方式，也就是集合

    //数组进行接收
    public String apply(@RequestParam(value = "n",defaultValue = "ANON") ,... Integer[] purpose){
    
    //List进行接收
    @ResponseBody
    public String apply(String name, String course, @RequestParam List<Integer> purpose){   //这里都是单独接收的，但理应存为一组数据
        //通过list来接收前端发来的其他复合数据,需要增加@RequestParam注解  （注：作为list它实际的载体是ArrayList。debug可以看到）
        
        
public String apply(String name, String course, @RequestParam List<Integer> purpose){ 
在实际应用中，对于这一组结构化的数据我们更多的时候是采用对象的方式进行接收
    //对象的方式进行接收 (通过实体类接收复合数据)
    @PostMapping("/apply")
    @ResponseBody
    public String apply(Form form){ 
    
    
    //用Map进行接收      （键值对的形式）复合数据不要用Map，否则数据会丢失
    @PostMapping("/apply")
    @ResponseBody
    public String apply(@RequestParam Map map){
```



#### 关联对象赋值

```
什么是关联对象？ 答：就是在一个对象中引用了另外一个对象。我们需要对这个被引用对象来进行赋值的操作
```

##### 复杂内容表单

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220817000536073.png" alt="image-20220817000536073" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220817000602408.png" alt="image-20220817000602408" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220817000758728.png" alt="image-20220817000758728" style="zoom:50%;" />

```java
//Delivery.java
package com.imooc.springmvc.entity;
//快递类
public class Delivery {
    private String name;
    private Long mobile;
    private String address;

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public Long getMobile() {return mobile;}
    public void setMobile(Long mobile) {this.mobile = mobile;}
    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}}
```

```java
//表单实体类
public class Form {
    private String name;
    private String course;
    private List<Integer> purpose;
    //包含快递的信息  关联对象赋值  delivery对应delivery.name在form.html中书写
    private Delivery delivery =  new Delivery();....
```

```html
//在form.html中加入 delivery.name
<h3>收货人</h3>
<!-- delivery是在Form.java中的  private Delivery delivery =  new Delivery();        -->
<input name="delivery.name" class="text" style="width: 150px">
<h3>联系电话</h3>
<input name="delivery.mobile" class="text" style="width: 150px">
<h3>收货地址</h3>
<input name="delivery.address" class="text" style="width: 150px">
```

```java
//FormController.java    控制器类
//关联对象赋值  在Form中关联了一个快递实体类 Delivery
    @PostMapping("/apply")
    @ResponseBody
    public String applyDelivery( Form form){
        System.out.println(form.getDelivery());
        return "success";}}
```

#### 日期类型转换

```
外国的日期表示是月日年，中国是年月日

我们如何来接收程序中的日期数据并且将它转换为日期对象?
网页报400错误往往就是 我们无法将数据转成目标类型，所导致的
```

