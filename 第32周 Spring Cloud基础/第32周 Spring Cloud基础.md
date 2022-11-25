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





