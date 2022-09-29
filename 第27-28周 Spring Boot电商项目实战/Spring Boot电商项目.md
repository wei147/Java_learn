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



#### 技术选型

```
Spring Boot2.2.1RELEASE
Mybatis 3.4.6(优点)
	使用MyBatis有以下优点:
		1.封装了jdbc的大部分操作,减少了开发量而且它是半自动的不是全自动的,这使得开发人员可以很清楚灵活的去控制以及编写sql语句,这在项目庞大的时候尤其是在后续需要对性能进行优化的时候是非常重要的
		2.另外使用Java代码可以让java代码和SQL语句分离维护成本低并且学习成本不高。如果我们之前对数据库语句比较熟悉的话就很容易上手而不需要额外再记忆很多的特殊语句语法  (Mybatis VS JPA)
Maven 3.6.1
log4j2 2.12.1
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220920012303170.png" alt="image-20220920012303170" style="zoom:50%;" />

```
确保技术前进步伐: 选择的这个技术不应该过于老旧、不应该停止维护。如果我们选择的技术/框架已经停止维护了,没有前进步伐的话,在遇到问题的话没有人可以帮你解决

技术为业务服务
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220920013007142.png" alt="image-20220920013007142" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220920013508399.png" alt="image-20220920013508399" style="zoom:50%;" />

#### 项目初始化

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220920013635868.png" alt="image-20220920013635868" style="zoom:50%;" />

```xml
1.初始化第一步改pom.xml文件,改对应的Spring Boot版本
2.整合MyBatis generator 插件用来自动生成数据库文件
            <!--mybatis.generator插件-->
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
                <configuration>
                    <verbose>true</verbose>
                    <!--是否覆盖原有的文件-->
                    <overwrite>true</overwrite>
                </configuration>
            </plugin>
	然后是新增src/main/resources/generatorConfig.xml文件(复制过来)需要修改的地方:
3.双击该插件就能自动生成一系列文件(有mapper接口以及对应mapper.xml文件、实体类)。在这里值得大家注意的是,我们之前的配置的Free Mybatis plugin(idea插件) 生效了,生效的标志是在mapper接口的左侧有绿色的箭头,点击之后可以跳转到对应的mapper.xml文件中

至此完成了数据库逆向的工作
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220920165814084.png" alt="image-20220920165814084" style="zoom:50%;" />

#### 打通数据库链路

```
那下一步我们希望能通过一些方法来调用mapper接口的方法,看看是不是真的打通了数据库,那我们就来编写Controller层和Service层,,
```



#### 配置log4j2日志组件

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220920221930304.png" alt="image-20220920221930304" style="zoom:50%;" />

```xml
排除Logback依赖
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <!--排除特定的依赖(以便排除冲突) 排除日志组件logback-->
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

引入log4j2依赖
        <!--log4j2 的依赖 (要先排除自带的logback组件,可以免除版本的指定,会自动匹配Spring boot对应版本)-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>

配置log4j2 放入log4j2.xml
```



#### AOP统一处理Web请求日志

```
下一步就是也就是项目初始化的最后一个环节————AOP统一处理Web请求日志
```

```
为什么需要AOP统一处理Web请求日志 ?
	有了这个aop统一处理之后,实际上是对系统健壮性的一种保证,在真实的项目中几乎都会有这样的功能,我们会用filter把每一个请求都打印出来。这样的良好习惯可以提高我们开发和调试的效率,我们将要做的目的就是创建filter并且把我们的请求的信息和返回信息给打印出来,,
```

```xml
引入aop依赖
        <!--要想引入aop的功能,首先要引入一个依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

有了依赖以后,需要建一个过滤器来对我们请求进行拦截和打印,新建mall/filter/WebLogAspect.java,
```

```java
//WebLogAspect.java
package com.imooc.mall.filter;
import java.util.Arrays;

/**
 * 打印请求和响应信息
 */
@Aspect
@Component
public class WebLogAspect {

    //定义好log类来记录日志
    private final Logger log = LoggerFactory.getLogger(WebLogAspect.class);
    //编写将要拦截的内容和拦截点

    //该方法指定拦截点
    @Pointcut("execution(public * com.imooc.mall.controller.*.*(..))")  //拦截controller包下面所有的
    public void webLog() {

    }

    //在这个拦截点的前和后分别进行拦截
    //在此之前的是请求信息

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {  //joinPoint记录的是类的信息,比如说方法信息
        //收到请求,记录请求内容
        //除此之外还想得到请求信息,所以这里利用RequestContextHolder
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //用log类来记录日志
        log.info("URL : " + request.getRequestURI().toString());  //请求url
        log.info("HTTP_METHOD : " + request.getMethod()); //方法 get/post
        log.info("IP : " + request.getRemoteAddr());      //ip
        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());  //获取到一些类的信息加上签名信息
        //参数可能不止1个,需要用到数组,然后用toString() 将其转为字符串
        log.info("ARGS(参数) : " + Arrays.toString(joinPoint.getArgs()));
    }


    //在此之后的是响应信息
    @AfterReturning(returning = "res", pointcut = "webLog()")
    public void doAfterReturning(Object res) throws JsonProcessingException {
        //处理完请求,返回内容
        //ObjectMapper()是由jackson提供的把对象转为json的工具
        log.info("RESPONSE : " + new ObjectMapper().writeValueAsString(res));
    }}
```



#### 用户模块-整体介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220921224921071.png" alt="image-20220921224921071" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220921225745066.png" alt="image-20220921225745066" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220921230039783.png" alt="image-20220921230039783" style="zoom:50%;" />

```
https://shimo.im/docs/K3WhpQ33RcqvkdyD/read  接口文档网址
```



#### API统一返回对象

```java
因为UserController的需要,  需要一个API统一返回对象
新建common包(放一些通用的类), 新建 /mall/common/ApiRestResponse.java以及紧随其后的异常枚举 /mall/exception/ImoocMallExceptionEnum.java 
    
本节课概要:
1.API统一返回对象这个概念还是挺强的。可以避免写大量重复代码(像SMM项目中就需要进一步加入这个了)
2.其中1是基于大量构造函数的才得以实现的,巧妙的运用。还有success方法的重载
3.泛型的利用是一个新知识点,后续根据不同对象返回不同响应是基于泛型T实现的。还有异常枚举也是一个新的知识点(Enum类)
```

```java
//1.ApiRestResponse.java
package com.imooc.mall.common;

import com.imooc.mall.exception.ImoocMallExceptionEnum;

/**
 * 通用返回对象
 */
public class ApiRestResponse<T> {
    private Integer status;

    private String msg;

    //这里的T为泛型,这里的data对象很可能是不固定的,用泛型比较合适
    private T data;

    //状态正常是10000
    private static final int OK_CODE = 10000;

    //状态正常的情况下 msg 是success
    private static final String OK_MSG = "SUCCESS";

    //生成三个参数的构造函数
    public ApiRestResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ApiRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    //为什么要建无参的构造函数?  意味我们不需要传递任何信息,即默认的信息,成功的时候(这里会调用有两个参数的构造方法)
    public ApiRestResponse() {
        this(OK_CODE, OK_MSG);
    }

    //这个方法返回一个通用的响应对象,成功的时候将会调用
    public static <T> ApiRestResponse<T> success() {
        //这里会去调用无参构造方法
        return new ApiRestResponse<>();
    }

    //这里是对上面success方法的重载
    public static <T> ApiRestResponse<T> success(T result) {
        ApiRestResponse<T> response = new ApiRestResponse<>();
        //response中要把response放进去就要set方法(于是生成get和set方法)
        response.setData(result);
        //这样一来,response不仅带上ok_code和ok_msg还带上了data
        return response;
    }

    //考虑到可能调用会失败,所以写一个失败的方法
    public static <T> ApiRestResponse<T> error(Integer code, String msg) {
        return new ApiRestResponse<>(code, msg);
    }

    //这是一个传入异常枚举的error方法
    public static <T> ApiRestResponse<T> error(ImoocMallExceptionEnum ex) {
        return new ApiRestResponse<>(ex.getCode(), ex.getMsg());
    }

    //为了打印的时候更清晰,再自动的生成一个toString方法,
    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
    public Integer getStatus() {return status;}
    public void setStatus(Integer status) {this.status = status;}
    public String getMsg() {return msg;}
    public void setMsg(String msg) {this.msg = msg;}
    public T getData() {return data;}
    public void setData(T data) {this.data = data;}
    public static int getOkCode() {return OK_CODE;}
    public static String getOkMsg() {return OK_MSG;}}
```

```java
//ImoocMallExceptionEnum.java 
package com.imooc.mall.exception;

/**
 * 异常枚举
 */
public enum ImoocMallExceptionEnum {
    NEED_USER_NAME(10001, "用户名不能为空");

    /**
     * 异常码
     */
    Integer code;

    /**
     * 异常信息
     */
    String msg;

    ImoocMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;}
    public Integer getCode() { return code;}
    public void setCode(Integer code) {this.code = code;}
    public String getMsg() {return msg; }
    public void setMsg(String msg) { this.msg = msg;}}
```



#### 注册接口开发





#### 自定义异常类

```java
package com.imooc.mall.exception;

/**
 * 统一异常
 */
public class ImoocMallException extends Exception {
    private final Integer code;
    private final String message;

    //这里不写构造函数赋值的话,上边会报错,可能是和final这个关键字有关
    public ImoocMallException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    //有更方便的办法 : 和刚才统一处理异常的思路一致,我们可以直接传入一个异常的枚举,
    // 然后在里面调用上一个构造函数,这样一来我们就可以用一个枚举来构造出异常了
    public ImoocMallException(ImoocMallExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());}

    public Integer getCode() {return code;}

    @Override
    public String getMessage() {
        return message;}}
```

```java
//UserServiceImpl.java   上面的异常类应用在这里,Service层的抛出异常,比如重名异常、插入失败异常
@Override
public void register(String username, String password) throws ImoocMallException {
    //查询用户名是否存在,不允许重名
    User result = userMapper.selectByName(username);
    if (result != null) {
        //由于每一层的分工是非常明确的,,不应该在Service去触碰那些和最终返回相关的内
        // 容,那些是Controller层的职责。所以我们在此用一个巧妙的方法———抛出异常的方法来解决这个问题
        throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
    }
    //写到数据库
    User user = new User();
    user.setUsername(username);
    user.setPassword(password);

    int count = userMapper.insertSelective(user);//选择性插入
    if (count == 0) {
        throw new ImoocMallException(ImoocMallExceptionEnum.INSERT_FAILED);
    }
}
```

```json
//密码长度异常响应信息是这样的,而用户重名异常返回的格式有些不一样,所以我们希望把它统一起来,我们应该用一个统一异常处理的办法来把这些信息异常起来(像timestamp和error)。这样不仅可以安全而且可以让返回非常统一。方案:下一节的统一异常处理
{
    "status": 10003,
    "msg": "密码长度不能小于6位",
    "data": null
}


{
    "timestamp": "2022-09-22T16:16:52.818+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "不允许重名,注册失败",
    "path": "/register"
}
```

```
http://localhost/register?username=  &password=123899   无法判断空格是不允许的?  这样可以通过后端的检测(不应该是这样)
```



#### GlobalExceptionHandler编写(解决异常返回的格式有些不一样,系统异常)

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220923004646580.png" alt="image-20220923004646580" style="zoom:50%;" />

```java
//
package com.imooc.mall.exception;
/**
 * 处理统一异常的handler
 */
//这个ControllerAdvice注解的作用就是拦截这些异常单的
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //目前有两种类型的异常需要拦截: 1.系统异常  2.业务异常

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default Exception : ",e);
        return ApiRestResponse.error(ImoocMallExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(ImoocMallException.class)
    @ResponseBody
    public Object handleImoocMallException(ImoocMallException e) {
        log.error("ImoocMallException : ",e);
        //这里传进来的是什么就正常打印出去
        return ApiRestResponse.error(e.getCode(),e.getMessage());}}
```

```json
//处理成功
{
    "status": 10004,
    "msg": "不允许重名,注册失败",
    "data": null
}
```



#### Java异常体系

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220923160409911.png" alt="image-20220923160409911" style="zoom:50%;" />

```java
首先RuntimeException一旦发生一定是我们程序员的问题。

Error以及RuntimeException实际上都是我们编译器无法提前预测的,我们把这种异常统称为非受检查异常,
uncheckedExcepiton


在Exception下面除了RuntimeException外的所有Exception,这种异常我们把它称为受检查异常,CheckedException

比如FileNotFoundException(没有找到对应的文件),既然是受检查异常这就意味着我们可以在程序中提前对他们进行处理,所以某个方法会抛出这样的异常,编译器就要求我们对这种可能出现的异常情况做及时的处理,这样一来就增加了我们java程序的健壮性
    
java异常体系主要有两种分类:第一个是从error和Exception角度去分的。error主要是jvm发生一些不可逆转的大错误,而Exception我们有一定的挽救可能,在Exception中主要分为runtimeException和其他的所有,,

非受检查异常: RuntimeException 和error共同构成
受检查异常: 在Exception下面除了RuntimeException外的所有Exception
```



#### 对密码进行MD5保护

```java
//mall/util/MD5Utils.java  用于加密密码
package com.imooc.mall.util;
import com.imooc.mall.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具
 * <p>
 * 说明:其实MD5它是一种哈希算法,它会把一种字符串通过哈希的方式转换为另外一个难以辨认的字符串,
 * 如果我们把哪个字符串给存下来,它是无法通过生成后的字符串去反推之前的原始字符串的。它本质是一种哈希运算,
 * 那么由于它无法反推,所以它不具备解密的能力,这就意味着实际上它并不是一种严格意义上的加密算法,
 * 严格意义上它是一种哈希算法,所以为了更严谨这里叫做MD5工具,
 */
public class MD5Utils {
    public static String getMd5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //加上较为复杂的盐值 https://www.cmd5.com/ 这个网站也无法破解 (穷举法)
        return Base64.encodeBase64String(md5.digest((strValue + Constant.SALT).getBytes()));}

    //用这个方法测试生成的MD5的值
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String str = getMd5Str("test");
        System.out.println(str);}}
```

```java
//UserServiceImpl.java

//写到数据库
User user = new User();
user.setUsername(username);
try {
    //对密码进行加密
    user.setPassword(MD5Utils.getMd5Str(password));
} catch (NoSuchAlgorithmException e) {
    e.printStackTrace();
}
//user.setPassword(password);
```

```java
package com.imooc.mall.common;
/**
 * 常量值
 * <p>
 * 放常量值的地方
 */
public class Constant {
    public static final String SALT = "yidou_8&.3@";    //用MD5的盐值}
```

```
至此,注册接口开发完成
```



#### 登录接口开发

```
Http协议本身是无状态的协议,也就是说,也就是说它无法记录用户的访问或者登录状态,而且每次请求都是独立的没有关联,一次请求就是一次请求
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220924145338676.png" alt="image-20220924145338676" style="zoom:50%;" />

```
先在实现类(UserServiceImpl)中写好方法,接着在实体类中的方法上加上@Override,然后再利用idea的修复功能即可以实现自动生成Service类(UserService)中的方法
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220924222412699.png" alt="image-20220924222412699" style="zoom:50%;" />

```java
//UserServiceImpl.java
    @Override
    public User login(String username, String password) throws ImoocMallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMd5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(username, password);
        if (user == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.WRONG_PASSWORD);
        }
        return user;}
```

```java
//UserController.java
@PostMapping("/login")   //因为参数是在请求中的,所以需要加上@RequestParam
@ResponseBody
public ApiRestResponse login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) throws ImoocMallException {
    //登录时所要用的关键任务 HttpSession session对象
    if (StringUtils.isEmpty(username)) {
        return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
    }
    //2.校验 password不能为空
    if (StringUtils.isEmpty(password)) {
        return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASS_WORD);
    }
    User user = userService.login(username, password);
    //保存用户信息时,不保存密码(为了安全起见这里的password设置为空,不会返回给用户)
    user.setPassword(null);
    session.setAttribute(Constant.IMOOC_MALL_USER, user);
    return ApiRestResponse.success(user);}
```

```
以上用户登录接口开发完毕
```



#### 用户模块剩余接口开发

```
小技巧: 在需要if判断的代码最后面.if 即可快速实现if判断代码模块
```





#### 总结用户模块

```java
//UserServiceImpl.java  新增更新用户签名接口、管理员登录接口
@Override
public void updateUserInformation(User user) throws ImoocMallException {
    //更新个性签名
    int updateCount = userMapper.updateByPrimaryKeySelective(user);
    if (updateCount>1){
        throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
    }}

@Override
public boolean checkAdminRole(User user){
    //1是普通用户  2是管理员
    return user.getRole().equals(2);}
```

```java
//UserController.java 新增更新个性签名、登出和管理员登录功能

/**
 * 更新个性签名
 * @param session
 * @param signature
 * @return
 * @throws ImoocMallException
 */
@PostMapping("/user/update")   //因为参数是在请求中的,所以需要加上@RequestParam
@ResponseBody
public ApiRestResponse updateUserInfo(HttpSession session, @RequestParam String signature) throws ImoocMallException {
    User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
    if (currentUser == null) {
        return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
    }
    User user = new User();
    //会通过id找到该用户将用户昵称进行更新
    user.setId(currentUser.getId());
    user.setPersonalizedSignature(signature);
    userService.updateUserInformation(user);
    return ApiRestResponse.success();
}

/**
 * 登出,清除session
 *
 * @param session
 * @return
 * 只需要在Controller层清除session信息就可以, 不需要到Service
 */
@PostMapping("/user/logout")
@ResponseBody
public ApiRestResponse logout(HttpSession session) {
    //清除Session
    session.removeAttribute(Constant.IMOOC_MALL_USER);
    return ApiRestResponse.success();
}

/**
 * 管理员登录接口
 * @param username
 * @param password
 * @param session
 * @return
 * @throws ImoocMallException
 */
@PostMapping("/adminLogin")   //因为参数是在请求中的,所以需要加上@RequestParam
@ResponseBody
public ApiRestResponse adminLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) throws ImoocMallException {
    //登录时所要用的关键任务 HttpSession session对象
    if (StringUtils.isEmpty(username)) {
        return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
    }
    //2.校验 password不能为空
    if (StringUtils.isEmpty(password)) {
        return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASS_WORD);
    }
    User user = userService.login(username, password);
    //验证是否是管理员
    if (userService.checkAdminRole(user)) {
        //是管理员,执行操作
        //保存用户信息时,不保存密码(为了安全起见这里的password设置为空,不会返回给用户)
        user.setPassword(null);
        session.setAttribute(Constant.IMOOC_MALL_USER, user);
    } else {
        return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
    }
    return ApiRestResponse.success(user);}
```



#### 用户模块自测、重难点总结

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220925190118976.png" alt="image-20220925190118976" style="zoom:50%;" />

```java
三个重难点:
1.统一响应对象 就是我们的ApiRestResponse,有了这个类之后,我们的每一次返回都是统一格式的非常方便,,,
2.登录状态保持 使用session,登录的时候保持住,登出的时候会清除
3.统一异常处理,我们在Service层抛出异常之后,如果对异常不进行处理的话,会造成安全隐患同时前端也很难辨认不同的数据格式,于是对异常进行了统一处理,返回变得整齐划一
```



#### 商品分类模块介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220925191524001.png" alt="image-20220925191524001" style="zoom:50%;" />

```
天猫和京东商品分类采用的都是三级目录
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220926234713451.png" alt="image-20220926234713451" style="zoom:50%;" />

#### 开发添加分类接口

```
//plain ordinary java object	
```

```java
@Resource

@Autowired
疑问: 这两个哪个用得多
```

```java
//CategoryServiceImpl.java
package com.imooc.mall.service.impl;

/**
 * 目录分类Service实现类
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Resource
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
//        category.setName(addCategoryReq.getName());
        //copyProperties它就会把两个类里面如果字段类型一样,字段名一样的话,他就会拷贝过去,省得我们一个一个去写
        //copyProperties(source,target)  第一个参数source从哪里拷贝 第一个参数target拷贝到哪里
        BeanUtils.copyProperties(addCategoryReq, category);

        //如果有重名分类名不允许再添加
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if (categoryOld != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if ((count == 0)) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);
        }}}
```

```java
//CategoryController
package com.imooc.mall.controller;

/**
 * 目录Controller
 */
@Controller
public class CategoryController {
    @Resource
    UserService userService;
    @Resource
    CategoryService categoryService;

    @PostMapping("admin/category/add")
    @ResponseBody
    //加了@RequestBody之后,我们的Spring就可以从我们的body中,去把这个AddCategoryReq类给对应起来
    public ApiRestResponse addCategory(HttpSession session,@RequestBody AddCategoryReq addCategoryReq) {
        //1.要先做登录验证, 2.而且是管理员才能进行操作
        if (addCategoryReq.getName() == null || addCategoryReq.getType() == null ||
                addCategoryReq.getParentId() == null || addCategoryReq.getOrderNum() == null) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.PARA_NOT_NULL);
        }
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        //校验是否已经登录
        if (currentUser == null) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        //校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole) {
            //是管理员,执行操作
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        } else {
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        }}}
```

```java
/**
 * 统一异常
 */
//public class ImoocMallException extends Exception {
// 这里改成RuntimeException就不用在后续方法中抛出异常或者try catch了.(对于RuntimeException这样的Exception是可以不做额外处理的)
public class ImoocMallException extends RuntimeException {
    private final Integer code;
    private final String message;
```

```java
//之前的继承 Exception
public void add(AddCategoryReq addCategoryReq) throws ImoocMallException{
    ....
    Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
    if (categoryOld !=null) {
        throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED );
    }
    
    
//改为继承RuntimeException后
public void add(AddCategoryReq addCategoryReq){
    ....
Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
    if (categoryOld !=null) {
        throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED );
    }
```



#### @Valid注解优雅校验入参

```java
//关于怎么去优化这个判断 (参数校验的工作)    
if (addCategoryReq.getName() == null || addCategoryReq.getType() == null ||
                addCategoryReq.getParentId() == null || addCategoryReq.getOrderNum() == null) {
            return ApiRestResponse.error(ImoocMallExceptionEnum.PARA_NOT_NULL);
        }
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220928131603928.png" alt="image-20220928131603928" style="zoom:50%;" />

```
第一条相当于是一个总开关,而当开关开启之后,二三四可以分别的起作用,
```

```
idea小技巧:输入ltil 可以快速生成 for循环
```

```java
//CategoryController.java    
@PostMapping("admin/category/add")
    @ResponseBody
    //加了@RequestBody之后,我们的Spring就可以从我们的body中,去把这个AddCategoryReq类给对应起来
    public ApiRestResponse addCategory(HttpSession session,@Valid @RequestBody AddCategoryReq addCategoryReq) {
        //1.要先做登录验证, 2.而且是管理员才能进行操作
        //下面的if判断校验交给@Valid来实现了,在AddCategoryReq中有参数校验
//        if (addCategoryReq.getName() == null || addCategoryReq.getType() == null ||
//                addCategoryReq.getParentId() == null || addCategoryReq.getOrderNum() == null) {
//            return ApiRestResponse.error(ImoocMallExceptionEnum.PARA_NOT_NULL);
//        }
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
```

```java
//mall/exception/GlobalExceptionHandler.java    //把异常处理为对外暴露的提示

//处理方法的参数不合规的异常(情况)
    @ExceptionHandler(MethodArgumentNotValidException.class)    //异常的类型
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : ", e);
        return handleBindingResult(e.getBindingResult());
    }

    //在绑定出问题的时候,我们来把这个异常处理成一个返回的ApiRestResponse,,,
    private ApiRestResponse handleBindingResult(BindingResult result) {
        //把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {   //是否包含错误
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();   //getDefaultMessage 拿到错误信息
                list.add(message);
            }
            if (list.size() == 0) {
                return ApiRestResponse.error(ImoocMallExceptionEnum.REQUEST_PARAM_ERROR);
            }}
        return ApiRestResponse.error(ImoocMallExceptionEnum.REQUEST_PARAM_ERROR.getCode(), list.toString());}
```

```java
//这个独立的类用的注解形式做参数校验,一旦有校验不通过(异常)会通过全局异常处理GlobalExceptionHandler中的ApiRestResponse统一返回校验不通过的响应,可以清晰明了的知道添加分类信息哪里有问题
//imooc.mall.model.request;
/**
 * 添加目录的一个请求类
 */
public class AddCategoryReq {

    //分类名字符不能大于5个小于2个且不能为空
    @Size(min = 2,max = 5)
    @NotNull(message = "name不能为null")
    private String name;

    //分类名层级数最大为3
    @NotNull(message = "type不能为null")
    @Max(3)
    private Integer type;

    @NotNull(message = "parentId不能为null")
    private Integer parentId;

    @NotNull(message = "orderNum不能为null")
    private Integer orderNum;
    ....
```



#### Swagger自动生成API文档

```xml
<!--swagger 依赖 (swagger用于自动生成API文档的)-->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>3.0.0</version>
</dependency>

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>3.0.0</version>
</dependency>
```

```java
//开启Swagger注解
package com.imooc.mall;

@SpringBootApplication
//需要在这里也加上注解才能找到mapper文件
@MapperScan(basePackages = "com.imooc.mall.model.dao")
@EnableSwagger2
public class ImoocMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImoocMallApplication.class, args);}}
```

```java
//一些配置 1
package com.imooc.mall.config;

@Configuration
public class SpringFoxConfig {

    //访问http://localhost:8083/swagger-ui.html可以看到API文档
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("慕慕生鲜")
                .description("")
                .termsOfServiceUrl("")
                .build();}}
```

```java
//一些配置 2
package com.imooc.mall.config;
/**
 * 配置地址映射
 */
@Configuration
public class ImoocMallWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
//                "classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**").addResourceLocations(
//                "classpath:/META-INF/resources/webjars/");
        
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);}}
```

```java
//用 @ApiOperation 开启文字说明     @ApiOperation("后台添加分类目录")
package com.imooc.mall.controller;

/**
 * 目录Controller
 */
@Controller
public class CategoryController {
...
    @ApiOperation("后台添加分类目录")
    @PostMapping("admin/category/add")
    @ResponseBody
    //加了@RequestBody之后,我们的Spring就可以从我们的body中,去把这个AddCategoryReq类给对应起来
    public ApiRestResponse addCategory(HttpSession session,@Valid @RequestBody AddCategoryReq addCategoryReq) {
       ..
```

```
访问 http://localhost/swagger-ui.html 即可看到自动生成的api文档
```



#### 更新目录接口开发

```java
//UpdateCategoryReq.java
package com.imooc.mall.model.request;
/**
 * 更新分类目录的一个请求类
 */
public class UpdateCategoryReq {
    @NotNull
    private Integer id; //更新分类目录必须要传入id,其他的可以为空
    //分类名字符不能大于5个小于2个
    @Size(min = 2,max = 5)
    private String name;

    //分类名层级数最大为3
    @Max(3)
    private Integer type;
    private Integer parentId;
    private Integer orderNum;
    ....get and set
```

```java
//CategoryServiceImpl.java  更新分类目录

//我写的没有考虑到重名的问题
//    @Override
//    public void update(UpdateCategoryReq updateCategoryReq) {
//        Category category = categoryMapper.selectByPrimaryKey(updateCategoryReq.getId());
//        BeanUtils.copyProperties(updateCategoryReq, category);
//        int count = categoryMapper.updateByPrimaryKeySelective(category);
//        if ((count == 0)) {
//            throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
//        }
//    }


@Override
public void update(Category updateCategory) {
    if (updateCategory.getName() != null) {
        Category categoryOld = categoryMapper.selectByName(updateCategory.getName());
        //如果传进来的id和数据库里的id不一样的话而且你们名字还是一样的话,此时便拒绝掉 (这里便是避免了重名操作,比如id为31的小黄鱼去改id=8的鱼类是不允许的)
        if (categoryOld != null && !categoryOld.getId().equals(updateCategory.getId())) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
    }
    int count = categoryMapper.updateByPrimaryKeySelective(updateCategory);
    if ((count == 0)) {
        throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAILED);
    }}
```

```java
//CategoryController.java  
@ApiOperation("后台更新分类目录")
@PostMapping("admin/category/update")
@ResponseBody
public ApiRestResponse updateCategory(HttpSession session, @Valid @RequestBody UpdateCategoryReq updateCategoryReq) {
    User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
    //校验是否已经登录
    if (currentUser == null) {
        return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
    }
    //校验是否是管理员
    boolean adminRole = userService.checkAdminRole(currentUser);
    if (adminRole) {
        //是管理员,执行操作
        Category category = new Category();
        BeanUtils.copyProperties(updateCategoryReq, category);
        categoryService.update(category);
        return ApiRestResponse.success();
    } else {
        return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);}}
```



#### 统一校验管理员身份

```java
package com.imooc.mall.filter;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.CategoryService;
import com.imooc.mall.service.UserService;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 管理员校验过滤器 (统一校验管理员身份)
 */
public class AdminFilter implements Filter {

    @Resource
    UserService userService;

    @Resource
    CategoryService categoryService;

    @Override

    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        //校验是否已经登录
        if (currentUser == null) {
//            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN); 方法是void,不允许return
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("{\n" +
                    "    \"status\": 10007,\n" +
                    "    \"msg\": \"NEED_LOGIN\",\n" +
                    "    \"data\": null\n" +
                    "}");
            out.flush();
            out.close();
            return;
        }
        //校验是否是管理员
        boolean adminRole = userService.checkAdminRole(currentUser);
        if (adminRole) {
            //这就代表我们会继续到下一个过滤器,相当于是校验通过
            filterChain.doFilter(servletRequest, servletResponse);
            //是管理员,执行操作
//            categoryService.add(addCategoryReq);
//            return ApiRestResponse.success();
        } else {
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("{\n" +
                    "    \"status\": 10009,\n" +
                    "    \"msg\": \"NEED_ADMIN\",\n" +
                    "    \"data\": null\n" +
                    "}");
            out.flush();
            out.close();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
```

```
有了filter之后,我们还需要把它配置上去,就有了AdminFilterConfig
```



```java
package com.imooc.mall.config;

import com.imooc.mall.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterRegistration;

/**
 * Admin 过滤器的配置 (com/imooc/mall/filter/AdminFilter.java)
 */
@Configuration
public class AdminFilterConfig {
    //想要配置一个Filter有两步:  1.把这个Filter给定义出来  2.是我们的这个Filter放到我们整个的过滤器的链路中去
    @Bean   //要加一个@Bean才能识别到
    public AdminFilter adminFilter() {
        return new AdminFilter();
    }

    @Bean(name = "adminFilterConf") //bean的名字不能设置成和类名一样否则就冲突了
    public FilterRegistrationBean adminFilterConfig() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(adminFilter());
        //设置需要拦截的、需要管理员权限的url
        filterRegistrationBean.addUrlPatterns("/admin/category/*");
        filterRegistrationBean.addUrlPatterns("/admin/product/*");
        filterRegistrationBean.addUrlPatterns("/admin/order/*");
        //给过滤器配置设置一个名字,以便于区分不同的配置
        filterRegistrationBean.setName("adminFilterConfig");
        return filterRegistrationBean;
    }
}
```