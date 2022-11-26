## 第32周 Spring Cloud基础

### 第一节 微服务基础

#### 第32周介绍

##### Spring Cloud基础 周介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221123005955955.png" alt="image-20221123005955955" style="zoom:40%;" />

 <hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221123010324963.png" alt="image-20221123010324963" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221123010536897.png" alt="image-20221123010536897" style="zoom:40%;" />

#### 微服务基础章节介绍

```
微服务的两大门派:  Spring Clould 和 Dubbo
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221123221616009.png" alt="image-20221123221616009" style="zoom:40%;" />



#### 什么是微服务

##### 微服务热度

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221123221947038.png" alt="image-20221123221947038" style="zoom:40%;" />

##### 单体应用的痛点

```
从单体应用去引出微服务

单体应用存在的问题
	部署效率低下		(项目一旦庞大的话,部署打包需要的时间长)
	团队协作开发成本高	(项目成员多不好协作)
	系统高可用性差	(项目中一个环节出现问题可能会导致其他模块不可用的情况)
	
于是我们就产生了服务化的思想, 我们如何如何解决这些问题?
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221123222546748.png" alt="image-20221123222546748" style="zoom:40%;" />

##### 什么是服务化

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221123223217262.png" alt="image-20221123223217262" style="zoom:40%;" />

##### 什么是微服务

```
服务和服务间通过http通信,不再是本地方法调用
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221123224310920.png" alt="image-20221123224310920" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221123224411568.png" alt="image-20221123224411568" style="zoom:40%;" />

#### 微服务的特点

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221123230904768.png" alt="image-20221123230904768" style="zoom:50%;" />



##### 会带来团队组织架构的调整

```
从原来单体应用水平团队组织结构 变成 微服务架构的垂直团队组织架构

现在的一个支付业务它就包含完整的开发技术包括数据库、包括测试、前后端。 在大公司较为常见
```



#### 微服务的优缺点

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221124113952627.png" alt="image-20221124113952627" style="zoom:40%;" />

```
技术栈丰富不仅包括语言、还包括开发工具还包括数据库存储还包括中间件等技术。而且微服务还有一个额外的好处就在非常有利于去引入或者实验新的、更先进的技术
```

##### 微服务的缺点

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221124114759047.png" alt="image-20221124114759047" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221124114707815.png" alt="image-20221124114707815" style="zoom:50%;" />

```
视情况而定,微服务不一定比单体应用好
```



#### 微服务的两大门派

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221124115451693.png" alt="image-20221124115451693" style="zoom:40%;" />

```
关于Spring Cloud感谢奈飞提供的组件 哈哈哈
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221124115726897.png" alt="image-20221124115726897" style="zoom:40%;" />

```
上图中的Dubbo是不提供该功能,但是Dubbo可以整合其他第三方开源软件
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221124120111686.png" alt="image-20221124120111686" style="zoom:50%;" />

```
Dubbo是国内开源框架,阿里巴巴
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221124120337524.png" alt="image-20221124120337524" style="zoom:50%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221124120736519.png" alt="image-20221124120736519" style="zoom:50%;" />



#### 微服务拆分

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221126010206886.png" alt="image-20221126010206886" style="zoom:40%;" />

<hr>

```
不适合拆分的情况,比如公司内部使用的OA系统用单体应用就可以了,服务拆分的维护成本较高。还有就是微服务利用的是网络之间的通信,延迟较高
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221126010534999.png" alt="image-20221126010534999" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221126011235570.png" alt="image-20221126011235570" style="zoom:40%;" />

```
横向拆分和纵向拆分往往会结合使用
```



#### 微服务扩展

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221126011702074.png" alt="image-20221126011702074" style="zoom:40%;" />

```
y轴就是微服务
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221126011913348.png" alt="image-20221126011913348" style="zoom:40%;" />

#### 微服务重要模块

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221126012505087.png" alt="image-20221126012505087" style="zoom:50%;" />

```
在微服务中一个非常重要的内容就是熔断与降级,它其实是一种保险措施,服务一多以后保不齐有一个服务就会发生故障,那么为了保证整体的可用性,可能某个服务提供不了服务了,但是应该有一个兜底策略。所以这就是熔断与降级要解决的一个问题。

最后一个非常重要的模块就是网关模块,当服务多了之后,用户不可能每一个请求都打到不同地址上去的,因为他也不知道你们这些地址是什么,服务太多了,所以我们就需要一个统一的网关来提供给我们的用户,这样我们的用户就可以把所有的请求都找到我们的网关,而进一步由我们的网关来进行下一步的分发。不仅如此,网关还能起到一些额外的作用,比如说可以进行统一的转化、可以进行权限校验、还可以进行过滤器的设置,这些就是网关的职责。
```



#### 章节总结

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221126013848836.png" alt="image-20221126013848836" style="zoom:40%;" />

