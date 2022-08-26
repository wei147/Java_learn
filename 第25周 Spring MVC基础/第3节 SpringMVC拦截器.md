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

问题：一个请求被多个拦截器同时过滤的话，到底哪个先执行？

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
            <!--通过规范静态资源目录来一次性把所有的静态资源排除在外边-->
            <mvc:exclude-mapping path="/resources/**"/>
            <!--指明是哪个类对拦截的url进行处理-->
            <bean class="com.imooc.restful.interceptor.MyInterceptor"></bean>
        </mvc:interceptor>
    </mvc:interceptors>



```

