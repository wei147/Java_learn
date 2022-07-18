## Spring基础

#### Spring Ioc

#### 第一阶段

```html
<课程介绍>
Spring快速入门		 Spring XML配置
对象实例化配置		  依赖注入配置
注解与ava Config	  Spring单元测试
```

#### Spring快速入门

##### Ioc控制反转

```html
<Ioc控制反转>
loC控制反转，全称Inverse of Control,,是一种设计理念
由代理人来创建与管理对象，消费者通过代理人来获取对象
1oC的目的是降低对象之间直接耦合
```

加入Ioc容器将对象统一管理，让对象关联变为弱耦合

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220707001441739.png" alt="image-20220707001441739" style="zoom:50%;" />

苹果这个创建者由最初顾客变为了果商。（冷冻仓库也称为Ioc容器）

#### DI依赖注入

在运行时来完成对象的创建和绑定的工作

```html
IoC是设计理念，是现代程序设计遵循的标准，是宏观目标
DI(Dependency Injection)是具体技术实现，是微观实现	（注：DI是Ioc的实现）
DI在Java中利用<反射>技术实现对象注入(Injection)	(注：java的依赖注入通过反射实现)
```



#### 介绍Spring

Spring

```html
<Spring的含义>
Spring可从狭义与广义两个角度看待
狭义的Spring是指Spring框架(Spring Fremework)
广义的Spring是指Spring生态体系

<狭义的Spring框架>
Spring框架是企业开发复杂性的<一站式>解决方案
Spring框架的核心是<IoC容器>与<AOP面向切面编程>		(注：Ioc是基础)
Spring loC负责创建与管理系统对象，并在此基础上扩展功能
    
<传统开发方式>
 对象直接引用导致对象硬性关联，程序难以扩展维护
    
 <Spring Ioc容器>
 loC容器是Spring生态的地基，用于统一创建与管理对象依赖
     
 <Spring loC容器职责>
对象的控制权交由<第三方>统一管理(IoC控制反转)
利用Java<反射>技术实现<运行时>对象创建与关联(D依赖注入)
基于配置提高应用程序的可维护性与扩展性
```

##### 广义的Spring生态体系

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220707234726570.png" alt="image-20220707234726570" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220708000105877.png" alt="image-20220708000105877" style="zoom: 33%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220708000712085.png" alt="image-20220708000712085" style="zoom:33%;" />



#### 分析传统编码方式的不足

##### Spring Ioc初体验

```
案例：
妈妈在早餐后给三个孩子分发餐后水果
盘子里装有三个苹果：红富士/青苹果/金帅
孩子们口味不同：莉莉喜欢甜的/安迪喜欢酸的/露哪喜欢软的

q：孩子如何得到喜欢的苹果？如何用程序代码实现
```

```java
//Apple.java
package com.imooc.spring.ioc.entity;

public class Apple {
    private String title;
    private String color;
    private String origin;  //产地

    // 为了满足java bean的要求，还需要生成默认的构造方法
    public Apple() {
    }

    //为了方便对象实例化，将其构造方法自动生成。
    public Apple(String title, String color, String origin) {
        this.title = title;
        this.color = color;
        this.origin = origin;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getColor() {return color;}

    public void setColor(String color) {this.color = color; }

    public String getOrigin() {return origin;}

    public void setOrigin(String origin) {this.origin = origin;}
}

```



```java
//Child.java
package com.imooc.spring.ioc.entity;

public class Child {
    private String name;
    private Apple apple;

    //默认的构造方法
    public Child() {
}

    //对于Child的实例化也需生成带参的构造方法
    public Child(String name, Apple apple) {
        this.name = name;
        this.apple = apple;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public Apple getApple() {return apple;}

    public void setApple(Apple apple) {this.apple = apple;}

    public void eat(){System.out.println(name+"吃到了"+apple.getOrigin()+"种植的"+apple.getTitle());}
}
```

```java
//Application.java
package com.imooc.spring.ioc.entity.com.imooc.spring.ioc;

import com.imooc.spring.ioc.entity.Apple;
import com.imooc.spring.ioc.entity.Child;

public class Application {
    public static void main(String[] args) {
        Apple apple1 = new Apple("红富士", "红色", "欧洲");
        Apple apple2 = new Apple("青苹果", "绿色", "中亚");
        Apple apple3 = new Apple("金帅", "黄色", "中国");

        //苹果对象被创建好之后需要和孩子产生关系
        Child lily = new Child("莉莉",apple1);
        Child wei = new Child("WEI",apple2);
        Child YanFei = new Child("烟绯",apple3);

        lily.eat();
        wei.eat();
        YanFei.eat();

        //弊端：
        //1.比如苹果的信息字段是写死的，只能通过修改源码进行修改（苹果信息可能随着季节变化），这样就必须要重新上线重新发布
        //2.对象的数量是固定写死的，写了三个只能创建三个对象
        //3.最重要一点，对象是硬关联：孩子和苹果的关系已经是确定了，这个确定关系是在程序编译时就完成的。例如：烟绯长大了想尝试红富士，那么就必须要修改源代码
        //而这一切背后的根源，就是在我们程序中使用了new关键字——在编译时就将对象和对象之间进行了强制的绑定。如何解决这个问题Spring Ioc容器就应运而生了。
        // 作为Spring它最根本的目的就是让我们通过配置的形式完成对对象的实例化以及对象和对象之间的依赖关系
        //下一讲，用IOC重构代码
    }
}
```



#### Spring Ioc容器与Bean管理

| 内容              | 说明                          | 重要程度 |
| ----------------- | ----------------------------- | -------- |
| Spring框架介绍    | Spring loC、DI和AOP等核心概念 | ※※※※※    |
| Spring loCs容器   | Spring实例化与管理对象        | ※※※※※    |
| 集合对象注入      | 注入List、Set、Map集合对象    | ※※※※※    |
| 底层原理          | Spring Bean的生命周期         | ※※※※※    |
| 注解与Java Config | Spring注解分类和常用注解应用  | ※※※※※    |

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220704154620861.png" alt="image-20220704154620861" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220704155214015.png" alt="image-20220704155214015" style="zoom: 50%;" />



#### Spring Ioc初体验 1

```
<spring-context文件说明>
spring-core：spring最核心、最核心的代码
spring-beans：spring对bean，也就是java对象管理的模块。像ioc容器中对对象实例化、设置关联通过该模块起作用
spring-expression：spring内置的表达式模块
spring-jcl：spring与日志进行交互的模块
spring-aop：对应了面向切面编程的模块
spring-context：上下文，通过application context对象可以让我们通过代码来对spring ioc 容器进行创建
以上六个底层依赖完成了spring ioc容器的工作
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--Spring ioc的核心配置文件。所有对象的创建以及关联的设置都是在这个xml中来进行的 -->
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    <!-- 在Ioc容器启动时，自动由spring实例化Apple对象，取名为sweetApple放入到容器中 -->
    <bean id="sweetApple" class="com.imooc.spring.ioc.entity.Apple">
        <property name="title" value="红富士"></property>
        <property name="origin" value="欧洲"></property>
        <property name="color" value="红色"></property>
    </bean>
    <bean id="sourApple" class="com.imooc.spring.ioc.entity.Apple">
        <property name="title" value="青苹果"></property>
        <property name="origin" value="中亚"></property>
        <property name="color" value="绿色"></property>
    </bean>
    <bean id="softApple" class="com.imooc.spring.ioc.entity.Apple">
        <property name="title" value="金帅"></property>
        <property name="origin" value="中国"></property>
        <property name="color" value="黄色"></property>
    </bean>
</beans>
```

```java
//SpringApplication.java 已更新看下边
```

#### Spring Ioc初体验 2

```java
//SpringApplication.java
package com.imooc.spring.ioc.entity.com.imooc.spring.ioc;

import com.imooc.spring.ioc.entity.Apple;
import com.imooc.spring.ioc.entity.Child;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.ApplicationContext;
/**
 * 所谓ioc容器就是通过配置的方式，让我们在不需要new关键字的情况下对对象进行创建
 * 对于Spring来说，创建对象是其最基础的工作。与此同时，它还有一个重要职能是维护对象的关联关系
 * 利用反射技术在程序运行时动态的进行设置。灵活的
 * 2022年7月11日00:25:47 利用spring ioc容器让我们对象和对象之间进行有效的解耦
 * 之前：对象关系通过代码来实现  现在：对象关系通过配置来实现
 * ioc何为控制反转：所谓控制反转是与我们程序主动创建相对的（通过new来创建）。现在是被动的从容器（所有对象、关系被ioc创建并管理）中提取。
 * 通过ioc这个第三者的介入，让程序的维护性和拓展性上升了一个层次
 */
public class SpringApplication {
    public static void main(String[] args) {
        //作为spring ioc容器要先启动才能对配置的bean进行实例化
        //含义是：去加载指定的xml文件来初始化Ioc容器
        //classpath 当前类路径下去查找文件
        //context指代了Spring ioc容器
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Apple sweetApple = context.getBean("sweetApple", Apple.class);
        System.out.println(sweetApple.getTitle());

        //从Ioc容器中提取beanId = Lily的对象
        Child lily = context.getBean("lily",Child.class);
//        System.out.println(lily.getApple().getTitle() + lily.getName());
        lily.eat();

        Child wei = context.getBean("wei",Child.class);
        wei.eat();

        Child YanFei = context.getBean("YanFei",Child.class);
        YanFei.eat();}}
```



#### 初始化Ioc容器

##### XML管理对象（Beans）

```
Bean的编码要求：java bean 必须要有默认构造函数以及属性私有并且通过get set方法设置属性
Spring ioc容器中管理的就是一个个具体的java bean
```

```html
<三种配置方式>
	<基于XML配置Bean>
//创建Ioc容器并根据配置文件创建对象
ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
基于注解配置Bean
基于Java代码配置Bean
注：三种配置方式本质上是一样的，都是告诉spring ioc容器如何实例化、如何管理这些bean
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220711125227623.png" alt="image-20220711125227623" style="zoom:50%;" />

```
Core Container里的Context包含了 Ioc容器
```

如何通过带参构造方法创建对象呢？默认是无参的构造方法

##### 利用带参构造方法实例化对象

```
讲解三种XML实例化Bean的配置方式
1.基于（默认）构造方法实例化对象（重点）
2.基于静态工厂实例化对象
3.基于工厂实例化方法实例化对象
根据以上三种方式可分为两种：一种是通过构造方法。另一种是基于工厂来进行对象的创建
```

```xml
 <bean id="apple1" class="com.imooc.spring.ioc.entity.Apple" >
        <!--作为bean，如果不写任何信息的话，bean标签默认通过默认构造方法创建对象。     对应实体类public Apple() {-->
<!--        <property name="title" value="青苹果"></property>-->
     </bean>

    <bean id="apple2" class="com.imooc.spring.ioc.entity.Apple" >
        <!--没有constructor-arg则代表调用默认构造方法实例化-->
        <constructor-arg name="title" value="青苹果"></constructor-arg>
        <constructor-arg name="color" value="青"></constructor-arg>
        <constructor-arg name="origin" value="中亚"></constructor-arg>
<!--        这里price设置的是字符串，但会根据实体类中定义自动转变类型 字符串到数字的转换-->
        <constructor-arg name="price" value="19.9"></constructor-arg>
    </bean>

    <bean id="apple3" class="com.imooc.spring.ioc.entity.Apple" >
        <!--利用构造方法参数位置实现对象实例化-->
        <constructor-arg index="0" value="青苹果"></constructor-arg>
        <constructor-arg index="1" value="青"></constructor-arg>
        <constructor-arg index="2" value="中亚"></constructor-arg>
        <constructor-arg index="3" value="19.9"></constructor-arg>
    </bean>
```



##### 基于工厂实例化对象

```
工厂：即是工厂模式。工厂模式的根本用途是隐藏创建类的细节，通过一个额外的工厂类来组织、创建我们需要的对象
按照工厂的表现形式又可以分为静态工厂和工厂实例  
```

```java
//AppleFactoryInstance.java
/**
 * 工厂实例方法创建对象是指Ioc容器对工厂类进行实例化并调用对应的实例方法创建对象的过程
 */
public class AppleFactoryInstance {
    public Apple createSweetApple(){
        Apple apple = new Apple();
        apple.setTitle("金苹果");
        apple.setOrigin("格林大陆");
        apple.setColor("金色");
        return apple;}}
```

```java
//AppleStaticFactory.java
/**
 * 静态工厂通过静态方法创建对象，隐藏创建对象的细节
 */
public class AppleStaticFactory {
    //创建一个甜苹果对象，返回值是apple对象
    //静态方法：用于创建对象的方法是静态的
    public static Apple createSweetApple(){
        //logger.info("")   用工厂的方法能实现一些额外的功能
        Apple apple = new Apple();
        apple.setTitle("金苹果");
        apple.setOrigin("格林大陆");
        apple.setColor("金色");
        return apple;}}
```

```xml
<!--ApplicationContext.xml-->

<!--利用静态工厂获取对象-->
<bean id="apple4" class="com.imooc.spring.ioc.factory.AppleStaticFactory"
factory-method="createSweetApple">
</bean>

<!--利用工厂实例方法获取对象-->
<!--ioc初始化的过程中，会先对这个工厂实例化    -->
<bean id="AppleFactoryInstance" class="com.imooc.spring.ioc.factory.AppleFactoryInstance"></bean>
<bean id="apple5" factory-bean="AppleFactoryInstance" factory-method="createSweetApple"></bean>
```



#### 从Ioc容器获取Bean

```java
Apple apple1 = context.getBean("apple1", Apple.class); //更推荐使用
//或者 Apple apple1 = (Apple)context.getBean("apple1"); 	强制类型转换
System.out.println(apple1.getTitle());
```

##### 

```html
    <bean id="apple4" ></bean>
   <bean name="apple4"></bean>
<id与name属性相同点>
bean id与name都是设置对象在loC容器中唯一标识
两者在同一个配置文件中都不允许出现重复
两者允许在多个配置文件中出现重复，新对象覆盖旧对象
    
<id与name属性区别>
id要求更为严格，一次只能定义一个对象标识（推荐）
name更为宽松，一次允许定义多个对象标识
tips:id与name的命名要求有意义，按驼峰命名书写
    
name 和 id在单个文件中都是不容许重复的；但如果是在多个文件中这两者就有区别
<bean name="apple2,apple7" class="" >  name可以一次性设置多个标识，而id只能设置一个
```



#### 路径匹配表达式

```
加载配置文件时使用
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220713232152981.png" alt="image-20220713232152981" style="zoom: 33%;" />

```
实际上mave工程加载的时候，所谓的类路径指的是target下的classes目录		target/classes
而不是源代码中的 resources， resources中的资源文件在第一次编译运行时会自动发布到target/classes 目录中

classpath 指的就是target/classes
```

```java
//加载多配置文件
String[] configLocations = new String[]{
        "classpath:ApplicationContext.xml","classpath:ApplicationContext-1.xml"
};
//初始化Ioc容器并实例化对象
ApplicationContext context = new ClassPathXmlApplicationContext(configLocations);
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220713233117774.png" alt="image-20220713233117774" style="zoom: 50%;" />

```
classpath:config-*.xml 会涉及到一个配置文件同名id/name覆盖的问题，这里是按照阿斯克码的顺序加载，1,2,3,4..
```



#### 利用setter实现对象依赖注入

##### 对象依赖注入

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220715010944335.png" alt="image-20220715010944335" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220715014303499.png" alt="image-20220715014303499" style="zoom:33%;" />

```
由ioc容器统一的将这两个对象创建好以后，来动态的进行依赖的注入。依赖注入说白了就是将两个对象关联起来

<对象依赖注入>
依赖注入是指运行时将容器内对象利用<反射>赋给其他对象的操作
<基于setter方法注入对象>	(最常用)
	利用setter实现静态数值注入
	利用setter实现对象注入
基于构造方法注入对象
```



#### Ioc在实际项目中的重要用途

##### 体验依赖注入的优势

service 和 dao写好，如何让ioc对它进行管理呢？  需要分别在Dao和service两个xml文件进行配置

##### 利用改造方法实现对象依赖注入 

```java
    //利用改造方法实现对象依赖注入 
    public Child(String name, Apple apple) {
        System.out.println("构造方法参数apple: "+apple.getTitle());
        this.name = name;
        this.apple = apple;}
```

```xml
    <bean id="chen" class="com.imooc.spring.ioc.entity.Child">
        <constructor-arg name="name" value="小陈"></constructor-arg>
        <!-- 利用ref注入依赖对象-->
        <constructor-arg name="apple" ref="sweetApple"></constructor-arg>
    </bean>
```



#### 注入集合对象   （详情见s04）

```html
注入包含多数据的集合对象：list，set，map    
```

```xml
注入list
<bean id="..."class="...">
<property name="someList">
<list>
<value>具体值<value>
<ref bean="beanld"></ref>
</list>
</property>
</bean>
```

```xml
注入set
<bean id="..."class="...">
<property name="someSet">
<set>
<value>具体值<value>
<ref bean="beanld"></ref>
</set>
</property>
</bean>
```

注：set里面的不可重复（自动去重），list可重复

```xml
注入Map
<bean id="..."class="...">
<property name="someMap">
<map>
<entry key="k1"value="v1"></entry>
<entry key="k2"value-ref="beanld"></entry
</map>
</property>
</bean>
```

```xml
注入Properties   (属性类型)
<bean id="..."class="...">
<property name="someProperties">
<props>
<prop key="k1">v1</prop>
<prop key="k2">v2</prop>
</props>
</property>
</bean>
```



#### 查看容器内对象

```java
如何查看容器内的对象
//获取容器内所有beanId数组
String[] beanNames = context.getBeanDefinitionNames();
System.out.println(Arrays.toString(beanNames));//Arrays.toString 将数组转成String类型

//要获取一个匿名bean，使用的是类的全称          问题：有两个匿名bean，这里获取的是哪一个？
Computer computer = context.getBean("com.imooc.spring.ioc.entity.Computer", Computer.class);
System.out.println(computer.getBrand());    //这里获取的是第一个匿名的bean 惠普
//ioc容器匿名提取的规则：不加#序号 默认获取第一个。 "com.imooc.spring.ioc.entity.Computer#1",获取指定的bean

Computer computer1 = context.getBean("com.imooc.spring.ioc.entity.Computer#1", Computer.class);
System.out.println(computer1.getBrand());    //这里指定获取的是第二个匿名的bean 华硕

```

“将自动创建一个对象和对象名,快捷键ctrl+alt+V, introduce local variable”

```xml
    <bean class="com.imooc.spring.ioc.entity.Computer">
        <constructor-arg name="brand" value="惠普"></constructor-arg>
    </bean>

    <bean class="com.imooc.spring.ioc.entity.Computer">
        <constructor-arg name="brand" value="华硕"></constructor-arg>
    </bean>
匿名的bean也会被赋予默认的名字，并依次排列
[com.imooc.spring.ioc.entity.Computer#0, com.imooc.spring.ioc.entity.Computer#1, company]
```

会默认调用在实体类中写好的toString() 方法？ 因为toString() 方法本来是有的。 如果你在类里编写了toString，相当于覆盖了类中原有的toString， 即重写了toString() 方法

重写和重载有什么区别？

```java
         重载是在同一个类中，拥有相同的方法名，不同的参数列表、参数个数、参数类型，则视为重载。重载是一个类中多态性的表现，在编译时起作用（静态多态性）。
        
        重写就是子类对父类在原有的方法从新编译，是父类与子类之间的多态性，在运行时起作用。子类可以继承父类的所有方法，也可以继承父类中的某个方法。在方法名、返回类型、参数列表完全一致的情况下，这就是重写。子类的访问权限不能低于父类的。
    //这里重写了toString()方法
    @Override
    public String toString() {
        return "Computer{" +
                "brand='" + brand + '\'' +
                ", type='" + type + '\'' +
                ", sn='" + sn + '\'' +
                ", price=" + price +
                '}';
    }
```

关于getClass() 和getName()

```java
System.out.println("beanName: "+beanName);  //这里返回的是一个Object对象
System.out.println("beanName.getClass: "+beanName.getClass());   //得到Object对象的类对象
System.out.println("类型： "+context.getBean(beanName).getClass().getName()); //得到这个类对象的完整名称
```



#### bean scope属性详解

```html
scope：英文中有范围的意思

<bean scope属性>
bean scope属性用于决定对象何时被创建与作用范围
bean scope配置将影响容器内对象的数量
bean scope默认值singleton(单例)，指全局共享同一个对象实例
    
<scopel用法>
<bean id="bookDao"
class="com.imooc.spring.ioc.bookshop.dao.BookDaoOraclelmpl"
scope="prototype"/>
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220717232704262.png" alt="image-20220717232704262" style="zoom: 50%;" />

```
默认是单例模式 singleton
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220717233801000.png" alt="image-20220717233801000" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220719022923672.png" alt="image-20220719022923672" style="zoom:33%;" />

单例线程安全问题根源在于 属性在运行时会不断发生变化



#### bean scope的实际应用

