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
五种通知: 前置、后置、返回后、异常通知
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220729234945760.png" alt="image-20220729234945760" style="zoom:50%;" />

```
After Advice 无论执行成功与否都会执行，像try catch的finally
```

```
特殊的"通知"-引介增强  （现阶段了解即可）
引介增强(Introductionlnterceptor)是对类的增强，而非方法	（本质上是一个拦截器?）
引介增强允许在运行时为目标类增加新属性或方法
引介增强允许在运行时改变类的行为，让类随运行环境动态变更
```

```java
//MethodAspect.java    
// ret代表目标方法执行的返回值
    public void doAfterReturning(JoinPoint joinPoint,Object ret){
        System.out.println("<-------返回后通知: "+ret);}

    //Throwable 是所有异常的父类。为了捕获目标方法所抛出的异常
    public void doAfterThrowing(JoinPoint joinPoint,Throwable th){
        System.out.println("<-------异常通知: "+th.getMessage());}

    //after() 无法获取到目标方法运行时所产生的返回值或者是内部抛出的异常
    public void doAfter(JoinPoint joinPoint){
        System.out.println("<-----触发后置通知");
        String methodNameTest = joinPoint.getSignature().getName();
//        System.out.println(methodNameTest+"      after方法");}
```

```xml
//applicationContext.xml
<!-- 定义切面类-->
<aop:aspect ref="methodAspect">
    <!-- before通知(Advice)，代表在目标方法运行前先执行methodAspect.printExecutionTime()方法 前置通知-->
    <!--下方打印日志的前后顺序和配置的前后顺序是保持一致的-->
    <aop:before method="printExecutionTime" pointcut-ref="pointcut"></aop:before>
    <aop:after method="doAfter" pointcut-ref="pointcut"></aop:after>
    <aop:after-returning method="doAfterReturning" returning="ret"
                         pointcut-ref="pointcut"></aop:after-returning>
    <aop:after-throwing method="daAfterThrowing" pointcut-ref="pointcut" throwing="th"></aop:after-throwing>
</aop:aspect>
```



#### 详解环绕通知

##### 利用AOP进行方法性能筛选

```
需求：在每一个dao和service上进行时间的检查，如果单个方法的时间超过1秒，我们就认为这个方法执行太慢需要优化

环绕通知能完成前面四种通知的全部功能
```

```java
package com.imooc.spring.aop.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MethodChecker {
    //ProceedingJoinPoint 是JoinPoint的升级版，在原有功能外，还可以控制目标方法是否执行
    public Object check(ProceedingJoinPoint pjp) throws Throwable {
        try {
            long startTime = new Date().getTime();  //得到起始时间    （这里可以视为方法执行前可以执行的操作）
            Object ret = pjp.proceed();//执行目标方法  （Object对象其本质是目标方法执行后的返回值）  
            long endTime = new Date().getTime();    //方法执行后的结束时间    （这里以下可以视为方法执行后可以执行的操作）
            long duration = endTime - startTime;    //执行时间
            if (duration >= 1000) {
                String className = pjp.getTarget().getClass().getName();
                String methodName = pjp.getSignature().getName();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
                String now = sdf.format(new Date());
                System.out.println("=======" + now + ":" + className + "." + methodName + "(" + duration + "ms)=======");
            }
            return ret;
        } catch (Throwable throwable) {
            // 为什么这里要使用 throw throwable; 将异常抛出去呢？
            // 因为在系统中未来运行时可能并不只有一个通知，如果在当前的环绕通知中对这个异常进行了消化，
            // 那就意味着其他后续的处理都不会捕捉到这个异常，就可能会产生一些意料之外的问题。
            // 所以一般使用throw对捕获到的异常进行抛出
            System.out.println("Exception message: "+throwable.getMessage());
            throw throwable;}}}
```

```xml
<bean id="methodChecker" class="com.imooc.spring.aop.aspect.MethodChecker"></bean>
<aop:config>
    <aop:pointcut id="pointcut" expression="execution(* com.imooc..*.*(..))"/>
    <aop:aspect ref="methodChecker">
        <!--环绕通知-->
        <aop:around method="check" pointcut-ref="pointcut"></aop:around>
    </aop:aspect>
</aop:config>
```



#### 利用注解配置Spring AOP

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns="http://www.springframework.org/schema/beans"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--初始化Ioc容器-->
    <context:component-scan base-package="com.imooc"></context:component-scan>
    <!--启用Spring AOP 注解模式-->
    <aop:aspectj-autoproxy/>
</beans>
```

```java
//UserService.java
package com.imooc.spring.aop.service;
import com.imooc.spring.aop.dao.UserDao;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 用户服务
 */
@Service
public class UserService {
    @Resource	//对属性进行注入 有两种方法：1.直接放在属性上(推荐) 2.如果有set方法，也直接放在set方法上
    private UserDao userDao;

    public void createUser(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();}
        System.out.println("执行员工入职业务逻辑");
        userDao.insert();}

    public String generateRandomPassword(String type , Integer length){
        System.out.println("按" + type + "方式生成"+ length  + "位随机密码");
        return "Zxcquei1";}

    public UserDao getUserDao() {
        return userDao;}

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;}}
```

```java
//MethodChecker.java
package com.imooc.spring.aop.aspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *如何配置切面类？
 */

@Component //1.标记当前类为组件，这样Ioc容器会对其进行实例化和管理
@Aspect    //2.说明当前类是切面类，可以用于功能的拓展
public class MethodChecker {
    //3.环绕通知的注解,环绕通知，参数为PointCut切点表达式
    @Around("execution(* com.imooc..*Service.*(..))")   //一般采用环绕通知进行更细粒度的控制
//    @After()
//    @Before()
//    @AfterReturning()
//    @AfterThrowing
    //ProceedingJoinPoint是JoinPoint的升级版,在原有功能外,还可以控制目标方法是否执行
    public Object check(ProceedingJoinPoint pjp) throws Throwable {
        try {
            long startTime = new Date().getTime();
            Object ret = pjp.proceed();//执行目标方法
            long endTime = new Date().getTime();
            long duration = endTime - startTime; //执行时长
            if(duration >= 1000){
                String className = pjp.getTarget().getClass().getName();
                String methodName = pjp.getSignature().getName();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
                String now = sdf.format(new Date());
                System.out.println("=======" + now + ":" + className + "." + methodName + "(" + duration + "ms)======");}
            return ret;
        } catch (Throwable throwable) {
            System.out.println("Exception message:" + throwable.getMessage());
            throw throwable;}}}
```



#### 代理模式与静态代理

##### Spring AOP实现原理

```html
面试题: 请你给我讲一下Spring AOP的底层实现原理是什么？

<Spring AOP实现原理>
Spring基于<代理模式>(设计模式中的代理模式)实现功能动态扩展，包含两种形式：
目标类拥有接口，通过JDK动态代理实现功能扩展
目标类没有接口，通过CGLib组件实现功能扩展

```

##### 代理模式-静态代理

```
代理模式-静态代理
代理模式通过代理对象对原对象的实现功能扩展

白话文：
客户类可以看成是有租房需求的租户
代理类可以看成是中介
委托类可以看成是房东
接口则可以看成是房子

中介和房东目的是一致的就是想把房子租出去，正是因为有相同的目的，所以他们就有了共同的接口————这个接口中提供了一个租房的方法，代理类和委托类都去实现租房的这个逻辑。
作为代理类它内部持有了委托类的对象，所以在代理类被实例化以后————也就是代理对象执行的过程中可以对原始的逻辑来产生额外的行为，比如说这个中介代理类在为客户看完房子之后除了交付给原始的房东指定的租金以外，他还要向客户另外收取一个月的代理费，这就是额外的扩展逻辑
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220801005437196.png" alt="image-20220801005437196" style="zoom: 50%;" />

###### 代理类的逻辑实现，通过代码演示：

```java
//静态代理是指必须手动创建代理类的代理模式使用方式。静态代理是最简单也是最麻烦的使用方式
JDK1.2以后，由于反射机制的引入，为我们自动创建代理类提供了可能。下一节动态代理。。。
```

```java
//接口类
package com.imooc.spring.aop.service;
public interface UserService {
    public void createUser();}
```

```java
//实现类
package com.imooc.spring.aop.service;

//实现UserService接口
public class UserServiceImpl implements UserService{
    @Override
    public void createUser() {
        System.out.println("执行创建用户业务逻辑");}}
```

```java
//代理类 1
package com.imooc.spring.aop.service;
import java.text.SimpleDateFormat;
import java.util.Date;

//静态代理是指必须手动创建代理类的代理模式使用方式

//代理类
//作为其代理类，其核心特点是:持有委托类的对象
public class UserServiceProxy implements UserService{   //作为代理类和委托类都需要实现相同的接口
    //持有委托类的对象
    private UserService userService;
    //重点:构建带参的构造方法
    public UserServiceProxy(UserService userService){   //这里需要传入具体的UserService实现。比如 UserServiceImpl
        //为内部的委托类赋值（相当于持有了委托类的对象）
        this.userService = userService;}

    @Override
    public void createUser() {

        System.out.println(" 我是二房东  ====="+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date())+"=====");
        //因为持有了委托类的对象，我们可以发起委托类的具体的职责。重点:在方法执行前还可以扩展其他的代码
        userService.createUser();}}
```

```java
//代理类 2
package com.imooc.spring.aop.service;
public class UserServiceProxy1 implements UserService{
    private UserService userService;

    public UserServiceProxy1(UserService userService){
        this.userService = userService;}
    @Override
    public void createUser() {
        System.out.println("我也是租户，他是二房东");
        userService.createUser();
        System.out.println("后置扩展功能");}}
```

```java
//启动入口
package com.imooc.spring.aop.service;

public class Application {
    public static void main(String[] args) {
        /**
         * 希望将这个方法的执行时间打印出来，该如何操作？（之前是使用spring aop 的环绕通知/切面类来做的。如果通过代理来做？）
         * 通过 UserServiceProxy 代理类来实现。
         * 还容许有二房东的存在，即这个租户自己又把房子租给别人。即嵌套使用
         * 静态代理是指必须手动创建代理类的代理模式使用方式
         */
//        UserServiceImpl userService = new UserServiceImpl();
        UserService userService = new UserServiceProxy(new UserServiceImpl());  //在没有修改最原始代码的情况下，对我们程序的行为进行了扩展
//        userService.createUser();

        UserService userService1 = new UserServiceProxy1(new UserServiceProxy(new UserServiceImpl()));
        userService1.createUser();}}
```



#### AOP底层原理-JDK动态代理

##### 代码讲解JDK动态代理

```java
//ProxyInvocationHandler.java
package com.spring.aop.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.SimpleDateFormat;
import java.util.Date;
//这个类要实现一个至关重要的接口 InvocationHandler

/**
 * InvocationHandler是JDK提供的反射类，用于在JDK动态代理中对目标方法进行增强
 * InvocationHandler 实现类与切面类的环绕通知类似
 */
public class ProxyInvocationHandler implements InvocationHandler {
    //因为ProxyInvocationHandler这里是面向所有类生效的，所以这里不能写具体类型（UserService）
    private Object target;
    private UserService userService;

    //这里需要传入目标对象 Object target
    public ProxyInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 在invoke() 方法对目标方法进行增强
     *
     * @param proxy  代理类对象   （通常是JDK代理动态自动生成的）
     * @param method 目标方法对象
     * @param args   目标方法实参
     * @return 目标方法运行后返回值  （返回值是return，通常是返回目标方法运行后的返回值）
     * @throws Throwable 目标方法抛出的异常
     */
    @Override
    //在学习反射 Method方法，用它（invoke）来调用目标方法
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //方法执行前输出的内容
        System.out.println("=====" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date()) + "=====");
        //method.invoke(目标对象方法,目标对象需要的实际参数)
        Object ret = method.invoke(target, args); //调用目标方法  返回值是Object。
        // 对比环绕通知: ProceedingJoinPoint.proceed() 和method.invoke()两者目的是一样的————“执行目标方法”。不同点: 一个经过了aop的封装，一个是使用java最底层的反射机制
        return ret;
    }

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();//具体的目标对象 实现类
        ProxyInvocationHandler invocationHandler = new ProxyInvocationHandler(userService);
        //作为这个对象invocationHandler本身只有一个职责就是对目标方法进行扩展。但是这个扩展需要基于一个代理类，而代理类如何创建？
        //动态创建代理类   （核心语句）  newProxyInstance(将类的加载器传至其中，将该类要实现的接口对象传入其中,如何对目标方法进行扩展) 在运行时就会基于userService这个接口产生与之对应的代理类。 动态创建的代理也实现了userservice
        UserService userServiceProxy = (UserService) Proxy.newProxyInstance(userService.getClass().getClassLoader(), userService.getClass().getInterfaces(), invocationHandler);   //newProxyInstance()用于基于接口创建指定的代理类

        //基于代理进行调用
        userServiceProxy.createUser();

        //创建了新的接口和对应的实现类，invocationHandler如何体现动态代理
        //动态代理,必须实现接口才可以运行（反之会报错）
        //实际学习过程中有大量的类都没有实现接口，这时那么该怎么做？ Spring为我们通过了另外一种解决方案：依赖于第三方组件CGLib实现对类的增强
        EmployeeService employeeService = new EmployeeServiceImpl();
        //employeeServiceProxy指向了被代理对象 EmployeeServiceImpl targetObject.createEmployee()
        EmployeeService employeeServiceProxy = (EmployeeService) Proxy.newProxyInstance(employeeService.getClass().getClassLoader(),
                employeeService.getClass().getInterfaces(), new ProxyInvocationHandler(employeeService));
        employeeServiceProxy.createEmployee();}}

```



#### JDK动态代理解析

#### AOP底层原理-CGLib

##### Spring AOP实现原理

```html
<Spring AOP实现原理>
Spring基于<代理模式>实现功能动态扩展，包含两种形式:
	目标类拥有接口，通过JDK动态代理实现功能扩展	（由于项目中的类大都没有实现接口，所以第二种情况使用比较多？）
	目标类没有接口,通过CGLib组件实现功能扩展
```

```
<CGLib实现代理类>
CGLib是运行时字节码增强技术	（Code Generation Library）
Spring AOP扩展无接口类使用CGLib
AOP会运行时生成目标继承类字节码的方式进行行为扩展
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220803004939834.png" alt="image-20220803004939834" style="zoom:50%;" />

```
userService$$EnhancerBySpringCGLib 通过类名可以知道: 通过CGLib实现类的增强，而CGLib的实现原理是在原始目标类的基础上进行继承然后重写每一个方法来实现的增强	（userService$$EnhancerByCGLib这个名词在Spring5.0之前没有spring）

JdkDynamicAopProxy JDK动态AOP代理 正是因为目标类实现了接口，所以Spring 优先选择jdk动态代理来实现功能的增强（没有实现接口就用上面的CGLib）

面试题 : Spring Aop的实现原理是什么？
	答: 要分为两种情况：1.如果目标类实现了接口，则spring优先底层使用JDK动态代理，来生成目标类的代理，从而实现功能的扩展	2.如果目标类没有实现接口，则自动使用CGLib来通过继承的方式对目标类进行扩展
```

#### 总结与回顾

```
<总结与回顾>
Spring AOP是在不修改源码的情况扩展程序的技术
Spring AOP的核心概念与配置过程
Spring AOP的实现原理

<Spring AOP与AspectJ的关系>
Eclipse AspectJ 一种基于Java平台的面向切面编程的语言
Spring AOP使用AspectJWeaver实现类与方法匹配
<Spring AOP利用代理模式实现对象运行时功能扩展>
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220803011921850.png" alt="image-20220803011921850" style="zoom: 33%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220803011955758.png" alt="image-20220803011955758" style="zoom: 33%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220803012220290.png" alt="image-20220803012220290" style="zoom:33%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220803012329217.png" alt="image-20220803012329217" style="zoom:33%;" />

