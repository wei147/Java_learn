## 第26周 SSM开发社交网站

#### 周课介绍

```
已经学习了Mybatis、Spring、Spring MVC这些工作中必须掌握的框架技术，但是这里你可能会有一个问题 : 作为这些框架如何在真实的项目中去使用？ 本周开始将结合实战，通过慕课书评网这一个小项目带着大家如何利用已经学习的这些知识来开发一个现代互联网应用
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828180601833.png" alt="image-20220828180601833" style="zoom:50%;" />

```
作为慕课书评网，会从数据库建表、到创建创建工程、再到环境搭建、再到编码实现这个完整的流程。
讲解前台页面的实现以及后台数据管理功能。
可以了解到一个工程师真正接到一个任务的时候到底是从哪个角度入手，如何一步一步完成我们的开发工作。
对于慕课书评网是真正意义上的与实际工作最贴近的第一个项目，Fighting ！ 2022年8月28日18:12:00
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828181239006.png" alt="image-20220828181239006" style="zoom:50%;" />



#### 课程介绍与项目演示

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828181545408.png" alt="image-20220828181545408" style="zoom:50%;" />

```
!!! 除了以上知识点以外，还通过辅助材料的形式提供了 如何基于阿里云来实现短信认证以及如何基于腾讯云的滑块验证码来实现前置的人机登录检查。
```



#### SSM整合的意义

##### SSM整合配置

```
SSM整合的意义：因为每一个项目都是多个公司多个产品的组合使用。如果我们没有把它们形成一个整体，而是让每一个技术都是一个技术孤岛的话，从维护的角度非常困难的，因为每一个框架都有自己的配置文件和一系列的配置信息....

Spring/Spring MVC/MyBatis是业内最主流的框架搭配
why?
首先Spring MVC它提供了我们的控制器，也就是基于Spring的Web交互的能力，对我们开发Web应用做好了基础的准备。而最底层的Spring 这个框架它本意是对应用程序的对象来进行创建和管理，其他所有框架的核心对象都是由Spring来进行统筹协调的。而最后的MyBatis则是完成了与底层数据库增删改查的操作。从客户端所发来的请求，经由Spring MVC进行接收，然后再由Spring负责创建，最后将业务产生的数据再通过MyBatis向数据库进行写入或者查询。那么整体就形成了一个从web应用到底层数据库读写的完整框架组合
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828184206282.png" alt="image-20220828184206282" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828184511499.png" alt="image-20220828184511499" style="zoom:50%;" />



#### Spring与Spring MVC环境配置

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220828212137958.png" alt="image-20220828212137958" style="zoom:50%;" />



