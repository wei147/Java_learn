# Netty+SpringBoot 开发即时通讯系统



### 介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415001315076.png" alt="image-20230415001315076" style="zoom:50%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415001449828.png" alt="image-20230415001449828" style="zoom:43%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415001645064.png" alt="image-20230415001645064" style="zoom:40%;" />



### 第2章 为什么要自研一套即时通讯系统 ?

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415002829472.png" alt="image-20230415002829472" style="zoom:43%;" />

#### 如何自研一套即时通讯系统

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415003337069.png" alt="image-20230415003337069" style="zoom:50%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415003734435.png" alt="image-20230415003734435" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415003830652.png" alt="image-20230415003830652" style="zoom:50%;" />

```
如果需要打造一款即时通讯系统我们需要做哪些?

首先需要一个接入层去维护我们客户端的长连接和消息收发,在协议的选择上可以优先选择TCP。再者需要选择一个合理的应用层协议,比如说是选择mqpp、还是私有协议。接入层还要做好用户Session的维护,因为接入层和传统web开发不一样,接入层是有状态的服务,而传统的http是无状态的服务

其次是逻辑层,逻辑层要处理消息收发的核心逻辑,配合接入层和存储层做到消息的不丢、不漏、不串

最后是存储层,存储层需要有合理的设计。为逻辑层提供数据服务。
```



### 第3章 基础数据开发-用户关系群组

#### 介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230415005028512.png" alt="image-20230415005028512" style="zoom:40%;" />

```
大致有三个模块。用户模块、关系链、群组。

业务系统和数据表会参考腾讯云即时通信
用户模块:
关系链 :分为强好友和弱好友两种方式。我加了谁和谁加了我两个维度的查询
群组 : 即时通许中最为复杂的模块。不光是基础业务的复杂。消息模块更是重中之重

```

#### 业务系统的基石 - 用户模块业务分析&数据库设计





