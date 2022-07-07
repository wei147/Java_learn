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
 
```

##### 广义的Spring生态体系

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220707234726570.png" alt="image-20220707234726570" style="zoom:50%;" />

##### 







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