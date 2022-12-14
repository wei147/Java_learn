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

用户模块各层级的重构 part2

```
解决包路径的报错问题
```

