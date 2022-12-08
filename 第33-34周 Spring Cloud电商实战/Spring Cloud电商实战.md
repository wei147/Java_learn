## Spring Cloud电商实战

#### 周介绍

```
重构Spring Boot电商项目

单体应用 VS 微服务,有些方法在单体运动行得通但微服务就不行了(可以预见)
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221205145453034.png" alt="image-20221205145453034" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221205145851492.png" alt="image-20221205145851492" style="zoom:40%;" />

```
单体应用可以共享Session,微服务是怎么解决这个问题的?用网关的RequestContext(上下文)解决? 感觉有点类似Session的存取方式,一样是map存储
```

#### 项目整体介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221206230905659.png" alt="image-20221206230905659" style="zoom:50%;" />

```

```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221206231510444.png" alt="image-20221206231510444" style="zoom:50%;" />

#### 模块拆分

```
模块拆分没有统一的标准。可以按服务功能间的紧密程度来拆分。公共模块是因为多个模块需要调用到以及后期利于维护的应用
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221208153505641.png" alt="image-20221208153505641" style="zoom:50%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221208153233284.png" alt="image-20221208153233284" style="zoom:40%;" />

#### 功能模块介绍

##### 功能介绍——前台

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221208220516421.png" alt="image-20221208220516421" style="zoom:50%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221208220620425.png" alt="image-20221208220620425" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221208220811686.png" alt="image-20221208220811686" style="zoom:40%;" />

##### 功能介绍——后台

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221208220936190.png" alt="image-20221208220936190" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221208221108540.png" alt="image-20221208221108540" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221208221215673.png" alt="image-20221208221215673" style="zoom:50%;" />

```
后台没有购物车模块,购物车模块是由用户所管理的
```



#### 项目初始化









