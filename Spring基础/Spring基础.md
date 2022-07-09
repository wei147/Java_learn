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

