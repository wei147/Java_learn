## AOP面向切面编程

#### 介绍AOP

##### 课程介绍

```
介绍Spring AOP与相关概念名词
Spring AOP开发与配置流程
Spring五种通知类型与应用场景
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220727154654646.png" alt="image-20220727154654646" style="zoom:50%;" />

```html
<Spring AOP>
Spring AOP-Aspect Oriented Programming面向切面编程
AOP的做法是将<通用>、<与业务无关>的功能抽象封装为<切面类>
<切面>可<配置>在<目标方法>的执行前、后运行，真正做到即插即用
```

##### 面向切面编程的最终目的是 :  在不修改源码的情况下对程序行为进行扩展



#### 初识AOP编程

```
需求：在执行Service或者dao任何方法的时候，都要打印当前方法所执行的时间
```

```xml
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
        <artifactId>spring-context</artifactId>
        <version>5.2.20.RELEASE</version>
    </dependency>

    <!--aspectjweaver是Spring AOP的底层依赖-->
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.9.5</version>
    </dependency>
</dependencies>
```
```java
//com/imooc/spring/aop/aspect/MethodAspect.java
package com.imooc.spring.aop.aspect;
import org.aspectj.lang.JoinPoint;
import java.text.SimpleDateFormat;
import java.util.Date;

// 切面类 (方法的切面)
public class MethodAspect {
    //切面方法，用于拓展额外功能
    //JoinPoint 连接点，通过连接点可以获取目标类/方法的信息
    public void printExecutionTime(JoinPoint joinPoint) {   //打印执行时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS"); //SSS代表毫秒
        String now = sdf.format(new Date());
        String className = joinPoint.getTarget().getClass().getName(); //获取目标类的名称
        String methodName = joinPoint.getSignature().getName();//获取目标方法名称   （getSignature）目标执行的方法是哪个
        //在方法运行前，来将对应的类和方法名称在控制台进行打印
        System.out.println("---->" + now + ":" + className + "." + methodName); }}
```

```xml
applicationContext.xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <bean id="userDao" class="com.imooc.spring.aop.dao.UserDao"></bean>
    <bean id="employeeDao" class="com.imooc.spring.aop.dao.EmployeeDao"></bean>

    <bean id="userService" class="com.imooc.spring.aop.service.UserService">
        <property name="userDao" ref="userDao"></property>
    </bean>
    <bean id="employeeService" class="com.imooc.spring.aop.service.EmployeeService">
        <property name="employeeDao" ref="employeeDao"></property>
    </bean>

    <!--AOP配置 (作为AOP底层运行也是要在ioc容器中的，所以这里要定义一个bean，让ioc容器去实例化这个方法MethodAspect)-->
    <bean id="methodAspect" class="com.imooc.spring.aop.aspect.MethodAspect"></bean>
    <aop:config>
        <!-- pointcut 切点，使用execution表达式描述切面的作用范围 -->
        <!-- execution(public * com.imooc..*.*(..)) 说明切面作用在com.imooc包下的所有类的所有方法上-->
        <aop:pointcut id="pointcut" expression="execution(public * com.imooc..*.*(..))"/>
        <!-- 定义切面类-->
        <aop:aspect ref="methodAspect">
            <!-- before通知，代表在目标方法运行前先执行methodAspect.printExecutionTime()方法 前置通知-->
            <aop:before method="printExecutionTime" pointcut-ref="pointcut"></aop:before>
        </aop:aspect>
    </aop:config>
</beans>
```

```
作为AOP整个运行过程是基于Ioc基础上的，没有Ioc就没有AOP。
需要在pom.xml引入两个依赖
```



#### AOP关键概念

```html
<Spring AOP与AspectJ的关系>
Eclipse AspectJ, 一种基于ava平台的面向切面编程的语言
Spring AOP使用AspectJWeaver实现类与方法匹配
<Spring AOP利用代理模式实现对象运行时功能扩展>
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220728222221106.png" alt="image-20220728222221106" style="zoom:50%;" />

```html
<AOP配置过程>
依赖AspectJ		（maven导入依赖）
实现切面类/方法	（具体java实现的类或方法）
配置Aspect Bean	（xml配置）
定义PointCut	（界定作用范围）
配置Advice	(通知)
```



#### JoinPoint核心方法

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220728224609265.png" alt="image-20220728224609265" style="zoom:50%;" />

```java
//MethodAspect.java
//getArgs 获取方法调用时传入的参数
    public void printExecutionTime(JoinPoint joinPoint) { 
    Object[] args = joinPoint.getArgs();
    System.out.println("---->参数个数: "+args.length);
    for (Object arg:args){
        System.out.println("---->参数: "+arg);}}
```

```java
//SpringApplication.java
public class SpringApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserService userService = context.getBean("userService", UserService.class);

        userService.createUser();
        //测试通过getArgs() 方法，在MethodAspect.java切面类中获取传入的参数并打印 （getArgs 获取方法调用时传入的参数）
        userService.generateRandomPassword("hello",8);}}
```



#### PointCut切点表达式

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220728231113775.png" alt="image-20220728231113775" style="zoom:50%;" />

```xml
        <!-- execution(public * com.imooc..*.*(..)) 说明切面作用在com.imooc包下的所有类的所有方法上-->
        <!--        <aop:pointcut id="pointcut" expression="execution(public * com.imooc..*.*(..))"/>-->
        <!--        只对所有Service类生效-->
        <!--        <aop:pointcut id="pointcut" expression="execution(* com.imooc..*Service.*(..))"/>-->
        <!--        只对没有返回值的void类生效-->
        <!--        <aop:pointcut id="pointcut" expression="execution(void com.imooc..*Service.*(..))"/>-->
        <!--        只对create开头的方法进行捕获-->
        <!--        <aop:pointcut id="pointcut" expression="execution(* com.imooc..*Service.create*(..))"/>-->
        <!--        只对无参数的方法进行捕获-->
        <!--        <aop:pointcut id="pointcut" expression="execution(* com.imooc..*Service.*())"/>-->
        <!--        只对两个参数的方法进行捕获-->
        <aop:pointcut id="pointcut" expression="execution(* com.imooc..*Service.*(*,*))"/>
        <!--        对两个参数并且第一个参数是String类型的方法进行捕获-->
        <aop:pointcut id="pointcut" expression="execution(* com.imooc..*Service.*(String,*))"/>
```



#### 五种通知类型

```
所谓通知是指： 在什么时机去执行切面的方法
四种通知: 前置、后置、返回后、异常通知
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220729234945760.png" alt="image-20220729234945760" style="zoom:50%;" />

```
After Advice 无论执行成功与否都会执行，像try catch的finally
```

```
特殊的"通知"-引介增强

```

