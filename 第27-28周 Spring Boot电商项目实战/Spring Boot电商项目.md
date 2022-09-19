## 第2节 Spring Boot电商项目



#### 电商项目整体介绍

##### 课程整体介绍

```
在完成了项目的介绍、演示、准备、设计、开发和部署之后,相当于我们就完成了整个开发的流程,最后是项目的总结(回顾难点和重点)。后期在面试前,还想对项目进行快速复习的话,那么可以直接看到项目总结的部分。
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220917005145325.png" alt="image-20220917005145325" style="zoom:50%;" />

#### 为什么要做电商系统 ?

```
电商系统有什么好处?
一套电商系统所包含的模块是比较丰富的,通常包括商品、订单、用户,这里还会包括购物车模块和商品分类模块,这样一来不仅能学到每一个模块的开发过程而且还可以对模块之间的关系和交互有更深层次的了解。电商系统它能非常好的抽象我们在实际工作中的场景因为...
而从模块展开的话,它能牵扯出更多的知识点,电商系统对功能和技术栈的要求相对而言还是比较高的,,
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220917211233890.png" alt="image-20220917211233890" style="zoom:50%;" />

#### 项目亮点

```
采用前后端分离的开发模式,我们的前端是由React框架开发的,目前主流的前端开发框架,而后端只负责接口的开发,我们所负责的是数据的处理、整合而不会涉及到页面的展示排版

前后端开发能带来更多的好处,因为通常如果我们把前后端放一起开发的话,比如说我们写jsp,那么这些实际上是要在Java服务器中运行的而无法使用nginx等对它进行性能的提升,这样的话我们网站整体的性能是上不去的,运行速度比较慢吞,因为它是同步的加载,不像我们使用前后端分离的技术它是可以异步加载的方式来提高整体的效率
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220917212624671.png" alt="image-20220917212624671" style="zoom:50%;" />



#### 功能模块介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220917212822174.png" alt="image-20220917212822174" style="zoom:50%;" />

##### 前台模块

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220917213104101.png" alt="image-20220917213104101" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220917213314967.png" alt="image-20220917213314967" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220917213429001.png" alt="image-20220917213429001" style="zoom:50%;" />

##### 后台模块

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220917213613531.png" alt="image-20220917213613531" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220917213805680.png" alt="image-20220917213805680" style="zoom:50%;" />

#### 项目演示-前台



#### 项目演示-后台



#### 项目开发所需工具准备

```
idea插件:
Maven Helper
Free MyBatis plugin（这个没有搜索到,通过课件安装了）

接口测试工具: postman
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220918234651409.png" alt="image-20220918234651409" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220918234711444.png" alt="image-20220918234711444" style="zoom:50%;" />

#### 数据表设计

##### 数据库设计与项目初始化

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220919180857256.png" alt="image-20220919180857256" style="zoom:50%;" />

```
在之前使用的是Logback,这里使用的是Log4j2日志组件,,,
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220919181243401.png" alt="image-20220919181243401" style="zoom:50%;" />

##### 表设计

```
根据我们的规范,是数据库的表名去表示无论是用户还是商品的时候,统一不加s,使用单数而不是复数。第二点需要注意的是 我们需要给这个user统一加上前缀,why？ 实际上大项目里面经常会有不同的团队去共用同一个数据库,那么在这种情况下,这里的表会非常多,为了防止表重名和更清晰,需要把我们表加上一个统一的前缀;第二个好处是防止和关键字重名
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220919221916765.png" alt="image-20220919221916765" style="zoom:50%;" />

```
这里容许冗余字段的存在,即运费这个字段默认为0,在后续项目中暂时不会用到,后面功能更新可能会用到。
同时容许发货时间、支付时间和交易完成时间默认值为null,因为我们订单创建的时候不代表它未来一定会发货 一定会支付 一定会交易完成,所以我们不应该把它设置成当前时间


imooc_mall_order_item表(订单项目表),实际上我们一个订单下面会有多个商品,假如我们一个订单买面包蟹又买了帝皇蟹,那么这个时候会生成几条记录?实际上会生成三条记录 第一条记录是记录这个订单的(比如说这个订单是否发货是否支付就放在我们order表里),而还有两条记录是放在order_item表里面,分别就是我们的帝皇蟹和面包蟹
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220919223905449.png" alt="image-20220919223905449" style="zoom:50%;" />

```
上图中的order_no是用来定位归属地的,它属于哪个订单num,它就会在这里附上那个值,我们说过order_item的作用是用于记录商品的,所以一定会有商品的id(product_id)。既然有了商品id为什么还需要去记录商品名称、商品图片和商品单价呢? 
因为有了商品id不是可以随时去查吗? 但实际上有这些字段(商品名字、图片、价格)的目的是为了记录快照。因为可能我们下单之后,这个商品的信息是会被更改的,那么如果被更改的话,你再回去看这个订单,看到的应该是下单时候的状态,而不是更改后的,所以我们这里会把下单时的这个商品名字、图片、尤其是这个价格记录下来
```



