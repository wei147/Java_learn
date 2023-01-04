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

```xml
1.新建maven项目 cloud-mall-practice
2.删除自带的src文件夹
3.新建module cloud-mall-eureka-server
4.导入依赖 pom.xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.wei</groupId>
    <artifactId>cloud-mall-practice</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>cloud-mall-eureka-server</module>
    </modules>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.12.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--swagger2依赖-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger-ui</artifactId>
            <version>2.9.2</version>
        </dependency>
    </dependencies>

    <!--表示Spring Cloud的版本  (dependencyManagement用于统一管理整个依赖。在这里声明之后,在子项目中就不需要声明了)-->
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Greenwich.SR5</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <!--作为spring boot而言,想要和maven结合必备的插件-->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
                <configuration>
                    <verbose>true</verbose>
                    <overwrite>true</overwrite>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
    </properties>
</project>
```



#### Eure Server开发

```xml
1.新建包 com/wei/cloud/mall/prictice/eureka
2.pom.xml 导入依赖和一些设置
    <parent>
        <artifactId>cloud-mall-practice</artifactId>
        <groupId>com.wei</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>cloud-mall-eureka-server</artifactId>
    <!--规定版本-->
    <version>1.0-SNAPSHOT</version>
    <!--规定打包方式-->
    <packaging>jar</packaging>

    <name>
        cloud-mall-eureka-server
    </name>
    <description>Spring Cloud Eureka Server</description> <!--描述信息,应该是没什么用的?-->

    <dependencies>
        <!--eureka-server的依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!--作为spring boot而言,想要和maven结合必备的插件-->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

```java
3.application.properties配置文件
    
spring.application.name=eureka-server
server.port=8000
#主机名
eureka.instance.hostname=localhost
#是否需要去同步其他节点的信息 (这里的server是单节点不需要去其他地方获取更多的信息。设置为false可以减少不必要的开销)
eureka.client.fetch-registry=false
#取消把自己注册到服务上 (2022年12月9日23:41:54 这里设置为true会报错)
eureka.client.register-with-eureka=false
#我们本身Eureka Server所在的地址。其他模块都需要依赖这个地址才能进行服务的注册与发现
eureka.client.service-url.defaultZone=https://${eureka.instance.hostname}\
  :${server.port}/eureka/
```

```java
4.Eureka sever 启动类
package com.wei.cloud.mall.prictice.eureka;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server的启动类,提供服务注册与发现
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }}
```



#### 用户模块介绍和设计

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221209234639573.png" alt="image-20221209234639573" style="zoom:40%;" />

```
"不要相信前端传过来的任何数值",因为那些都是可以被伪造的
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221209235051687.png" alt="image-20221209235051687" style="zoom:50%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221209235248830.png" alt="image-20221209235248830" style="zoom: 67%;" />



#### 用户模块初始化



#### 公共模块的主要功能

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221210174325346.png" alt="image-20221210174325346" style="zoom:50%;" />

```
公共模块提供的主要功能不是对外提供接口而是一些类的复用(不要spring boot启动类的编写)。

下图是公共类的转移工作
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221210181556111.png" alt="image-20221210181556111" style="zoom:80%;" />

#### 用户模块各层级的重构 part1

```
不是隶属在同一个模块中的,不能直接进行引用的。 比如common模块和User模块。需要做一下引入的工作才能使用
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221213011430901.png" alt="image-20221213011430901" style="zoom:67%;" />

```
user模块的转移工作
```

#### 用户模块各层级的重构 part2

```
解决包路径的报错问题
```

#### 用户模块测试

```java
如果com/wei/cloud/mall/practice/user/model/pojo/User.java 没有实现Serializable接口会报错
    @Repository
public class User implements Serializable {

{
    "timestamp": "2022-12-14T17:43:00.463+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Cannot serialize; nested exception is org.springframework.core.serializer.support.SerializationFailedException: Failed to serialize object using DefaultSerializer; nested exception is java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [com.wei.cloud.mall.practice.user.model.pojo.User]",
    "path": "/login"
}
```

```
微服务的断点调试是没有的?

2022年12月15日02:01:07
http://localhost:8081/user/update?signature=长风破浪会有时
更新不了个性签名,我本地的没有Session啊,所以是怎么处理的?  
2022年12月15日02:08:57 上面那个问题可以回答了因为是同一个User模块的,所以用Session也是可以的。但是目前更新个性签名用不了。

2022年12月15日15:18:34 已明了上面的问题,
	之前的登录接口地址 http://127.0.0.1:8081/login?userName=wei&password=123456
	之前的更新个性签名接口 http://localhost:8081/user/update?signature=长风破浪会有时
一个是localhost 一个是127.0.0.1 导致登录接口存进去的User Session信息在更新个性签名接口这里拿不到。
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221215152321174.png" alt="image-20221215152321174" style="zoom:70%;" />

```
以上五个用户接口皆可用
```

#### 网关模块的开发

```
之所以要在这个时候编写网关模块,是因为编写了用户模块之后,如果想跳过网关进一步的去编写商品分类和商品模块的话是其实做不到的,因为在商品分类和商品模块编写过程中,特别是对后台进行操作,商品的上下架是需要管理员权限判断的,所以这个时候就需要开发网关,之后把用户模块注册到网关上,这样一来后续的模块想要去调用相关的服务就容易多了
```

```xml
//Zuul网关模块的依赖
    <dependencies>
        <!--eureka-client的依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>

        <!--zuul网关的依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>

        <!--这里对应的是common模块-->
        <dependency>
            <groupId>com.wei</groupId>
            <artifactId>cloud-mall-common</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>

        <!--这里对应的是user模块-->
        <dependency>
            <groupId>com.wei</groupId>
            <artifactId>cloud-mall-user</artifactId>
            <version>1.0-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
    </dependencies>
```

```java
//Zuul网关过滤器
package com.imooc.cloud.mall.practice.zuul.filter;
import om.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.wei.cloud.mall.practice.user.model.pojo.User;
import com.wei.mall.practice.common.common.Constant;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户鉴权过滤器
 */
@Component
public class UserFilter extends ZuulFilter { //首先会继承Zuul过滤器实现相关的方法
    @Override
    public String filterType() {
        //1.首先选择类型 前置过滤器
        return FilterConstants.PRE_TYPE; }

    @Override
    public int filterOrder() {
        //2.顺序保持为默认的0
        return 0; }

    @Override
    public boolean shouldFilter() {
        //3.是否应当启用过滤器? 什么时候应当启用,什么时候不该
        //(1).先通过上下文获取到url
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        //(2)获取到当前的网址
        String requestURI = request.getRequestURI();
        //(3)根据uri分情况决定通过还是不通过  (包含images图片和pay支付的,不经过过滤器。 购物车和订单模块则经过)
        if (requestURI.contains("images") || requestURI.contains("pay")) {
            return false;}
        if (requestURI.contains("cart") || requestURI.contains("order")) {
            return true;}

        return false;
    }

    @Override //run方法的含义是 一旦符合过滤器的规则(上面的 return true;),需要做哪些条件的鉴权
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null) {
            //这个方法的含义是 就不需要通过网关再去发送
            currentContext.setSendZuulResponse(false);
            //把将要返回的内容直接写下来。(直接返回给前端)
            currentContext.setResponseBody("{\n" +
                    "    \"status\": 10007,\n" +
                    "    \"msg\": \"NEED_LOGIN\",\n" +
                    "    \"data\": null\n" +
                    "}");
            //设定返回的状态码
            currentContext.setResponseStatusCode(200);
        }
        return null; }}
```

#### 管理员过滤器开发

```java
package com.imooc.cloud.mall.practice.zuul.filter;

import com.imooc.cloud.mall.practice.zuul.feign.UserFeignClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.wei.cloud.mall.practice.user.model.pojo.User;
import com.wei.cloud.mall.practice.user.service.UserService;
import com.wei.mall.practice.common.common.Constant;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 管理员鉴权过滤器
 */
@Component
public class adminFilter extends ZuulFilter { //首先会继承Zuul过滤器实现相关的方法

    @Resource
    UserFeignClient userFeignClient;

    @Override
    public String filterType() {
        //1.首先选择类型 前置过滤器
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        //2.顺序保持为默认的0
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        //3.是否应当启用过滤器? 什么时候应当启用,什么时候不该
        //(1).先通过上下文获取到url
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        //(2)获取到当前的网址
        String requestURI = request.getRequestURI();
        //(3)根据uri分情况决定通过还是不通过
        if (requestURI.contains("adminLogin")) {
            return false;
        }
        if (requestURI.contains("admin")) {
            return true;
        }
        return false;}

    @Override //run方法的含义是 一旦符合过滤器的规则(上面的 return true;),需要做哪些条件的鉴权
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null) {
            //这个方法的含义是 就不需要通过网关再去发送
            currentContext.setSendZuulResponse(false);
            //把将要返回的内容直接写下来。(直接返回给前端)
            currentContext.setResponseBody("{\n" +
                    "    \"status\": 10010,\n" +
                    "    \"msg\": \"NEED_LOGIN\",\n" +
                    "    \"data\": null\n" +
                    "}");
            //设定返回的状态码
            currentContext.setResponseStatusCode(200);
            return null;    //一但用户为空(还没有登录的情况下)就可以停止了,不进行后面的判断
        }
        Boolean adminRole = userFeignClient.checkAdminRole(currentUser);
        if (!adminRole ) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseBody("{\n" +
                    "    \"status\": 10011,\n" +
                    "    \"msg\": \"NEED_ADMIN\",\n" +
                    "    \"data\": null\n" +
                    "}");
        }
        return null;
    }}
```

```java
//Feign 实现从网关模块调用user模块的方法
package com.imooc.cloud.mall.practice.zuul.feign;

import com.wei.cloud.mall.practice.user.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "cloud-mall-user")  //value就是对应的模块名
public interface UserFeignClient {

    /**
     * 校验是否是管理员
     * @param user
     * @return
     */
    @PostMapping("/checkAdminRole")
    public Boolean checkAdminRole(@RequestBody User user);}
```

```java
//网关启动类
package com.imooc.cloud.mall.practice.zuul;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.EnableZuulServer;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 网关启动类
 */
@SpringCloudApplication  //这里是SpringCloudApplication 为什么不是SpringBootApplication?
@EnableZuulProxy
@EnableFeignClients
public class ZuulGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args); }}
```

#### Session共享机制

##### 登录功能分析

###### 之前的单体应用

```
把User信息存Session,后面直接拿就行。微服务通过网关则不可以这样
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221217231605082.png" alt="image-20221217231605082" style="zoom:40%;" />

###### 现在的微服务应用

```

```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221217231933566.png" alt="image-20221217231933566" style="zoom:50%;" />

```java
//cloud-mall-practice/cloud-mall-zuul/src/main/resources/application.properties
spring.application.name=cloud-mall-zuul

#将登录的user信息直接存到redis中 (Session的store-type存储方式设置为redis)
spring.session.store-type=redis
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=

#并没有敏感的headers需要过滤(网关便不会过滤)。
zuul.sensitive-headers=
#网关超时时间
zuul.host.connect-timeout-millis=15000
#Zuul整体的前缀
zuul.prefix=/
#user模块的路由
zuul.routes.cloud-mall-user.path=/user/**
#service-id是模块的名字
zuul.routes.cloud-mall-user.service-id=cloud-mall-user
```

```java
//cloud-mall-practice/cloud-mall-user/src/main/resources/application.properties
spring.application.name=cloud-mall-user

#将登录的user信息直接存到redis中 (Session的store-type存储方式设置为redis)
spring.session.store-type=redis
spring.redis.host=localhost
spring.redis.port=6379
spring.redis.password=
```

```java
在user和zuul启动类加上注解
@EnableRedisHttpSession //redis存储登录用户Session
```

```
2022年12月21日20:29:54 测试完成。user模块的5个接口皆可用
```



#### 商品分类与商品模块初始化

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221221203137197.png" alt="image-20221221203137197" style="zoom:50%;" />

```
一边先是从controller开始、service、serviceImpl、model(dao、pojo)
```

#### 商品分类开发与测试

```
2022年12月31日23:18:45 这么久才又接着正式看网课,一是阳了,二是自己刚回家可能状态不太对。

商品分类模块的开发与测试完成

```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221231232632623.png" alt="image-20221231232632623" style="zoom:67%;" />

```java
值得注意点,通过网关统一校验用户是否是管理员的时候,是通过Feign来调用UserController中的方法来实现

    //UserController中的具体实现类
    /**
     * 校验是否是管理员
     * @param user
     * @return
     */
    @PostMapping("/checkAdminRole")
    @ResponseBody
    public Boolean checkAdminRole(@RequestBody User user) {
        return userService.checkAdminRole(user);
    }

//UserFeignClient 类似接口一般。实现类在UserController中。后续网关中调用这个接口就可以了
package com.imooc.cloud.mall.practice.zuul.feign;
import com.wei.cloud.mall.practice.user.model.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "cloud-mall-user")  //value就是对应的模块名
public interface UserFeignClient {

    /**
     * 校验是否是管理员
     * @param user
     * @return
     */
    @PostMapping("/checkAdminRole")
    public Boolean checkAdminRole(@RequestBody User user);}
```



#### 商品模块介绍

#### 商品模块迁移和调整

```
2023年1月2日16:15:01 完成大部分的迁移和调整
```

#### 商品模块开发和梳理

#### 图片端口的特殊处理

```java
//application.properties
#因为这里的ip和端口号分别是本地和经过网关的。所以端口号是8083
file.upload.ip = 127.0.0.1
file.upload.port = 8083
```



#### 阶段性重难点和常见错误总结

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230103220547899.png" alt="image-20230103220547899" style="zoom:50%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230103215302499.png" alt="image-20230103215302499" style="zoom:50%;" />

```
Session在微服务的情况下就不能轻易的获取到了。所以我们可以利用共享Session的方法,把Session存储到redis中(redis对于各个模块来说都是同一个redis),取到的数据也是同一份。所以有了redis的帮助就能实现共享,这样一来不同模块在存储和读取的时候,也就实现了统一。


利用feign实现模块与模块间的调用
```



#### 购物车与订单模块介绍

```
最后一个主流程模块
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20230104230410248.png" alt="image-20230104230410248" style="zoom:40%;" />

```
还存在单体不存在的问题
```

