## OA系统项目





| 内容               | 说明                               | 重要程度 |
| ------------------ | ---------------------------------- | -------- |
| 需求说明和环境准备 | 讲解系统需求、业务规则、数据库设计 | 3星      |
| RBAC权限控制       | 基于RBA完整实现后台系统权限管理    | 五星     |
| 多级请假审批流程   | 讲解多级审批流程底层业务实现细则   | 五星     |



#### 慕课网办公OA平台

```办公自动化系统是什么
办公自动化系统（Office Automation）是替代传统办公的解决方案
OA系统是利用软件技术构建的单位内部办公平台，用于辅助办公
利用OA系统可将办公数据数字化，可极大提高办公流程执行效率
```

```html
<需求介绍>
慕课网办公OA系统要求采用多用户B/S架构设计开发		（浏览器/服务器的形式） browser/server
HR为每一位员工分配系统账户，员工用此账户登录系统
公司采用分级定岗，从1-8依次提升，不同岗位薪资水平不同

6级（含）以下员工为业务岗，对应人员执行公司业务事宜		（业务岗就是具体干活的，编码工作）
7-8级为管理岗，其中7级为部门经理，8级为总经理
业务岗与管理岗员工可用系统功能不同要求允许灵活配置
    
 <需求介绍-请假流程>
公司所有员工都可以使用”请假申请”功能申请休假
请假时间少于72小时，部门经理审批后直接通过
请假时间大于72小时，部门经理审批后还需总经理进行审批
     
部门经理只允许批准本部门员工申请
部门经理请假需直接由总经理审批
总经理提起请假申请，系统自动批准通过
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220429152631706.png" alt="image-20220429152631706" style="zoom:50%;" />



#### 创建Maven Web工程

```
框架&框架
MySQL 8				Mybatis3.5
Alibaba Druid		Servlet3.1		
Freemarker 2.3		LayUl2.5
```



#### 开发MyBatisUtils

在mybatis-config.xml写好的配置是如何被加载解析的呢？	那就需要一个Utils工具类对其进行解析、初始化工作

```xml
 <!--test.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="test">
    <select id="sample" resultType="String">
        select 'success'
    </select>
</mapper>
```



```xml
 <!--mybatis-config.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
    <settings>
        <!--开启驼峰命名转换 form_id ->formId -->
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
    <!--dev 为开发环境的意思，可自由指代-->
    <environments default="dev">
        <!--开发环境配置-->
        <environment id="dev">
            <!--事务管理器采用JDBC方式-->
            <transactionManager type="JDBC"></transactionManager>
            <!--利用MyBatis自带连接池管理连接-->
            <dataSource type="POOLED">
                <!--JDBC连接属性-->
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url"
                          value="jdbc:mysql://localhost:3306/imooc-oa?useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=Asia/Shanghai&amp;allowPublicKeyRetrieval=true"/>
                <property name="username" value="root"/>
                <property name="password" value="1234"/>
            </dataSource>
        </environment>
    </environments>
    <!--记得配置这一项不然识别不了test.xml文件-->
    <mappers>
        <mapper resource="mappers/test.xml"></mapper>
    </mappers>
</configuration>
```

```java
//工具类
package com.imooc.oa.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

/**
 * 在mybatis-config.xml写好的配置使用该工具类对其进行加载解析、初始化工作
 */

public class MybatisUtils {
    //利用static（静态）属于类不属于对象，且全局唯一
    private static SqlSessionFactory sqlSessionFactory = null;

    //利用静态块在初始化类时实例化sqlSessionFactory
    static {
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            //初始化错误时，通过抛出异常ExceptionInInitializerError通知调用者 （初始化异常）
            throw new ExceptionInInitializerError(e);
        }
    }


    /**
     * 执行Select 查询语句的代码块
     *
     * @param func 要执行查询语句的代码块
     * @param func
     * @return 查询结果
     * 注意：这里的Function是package java.util.function; 包含的是函数式接口（Lambda是基于此进行开发）
     * Function<T,R>对应有一个输入参数且需要返回数据的功能代码
     * @return
     */
    public static Object executeQuery(Function<SqlSession, Object> func) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            Object obj = func.apply(sqlSession);
            return obj;
        } finally {
            //用try...finally的设计保证了，一定要执行关闭连接 （回收连接池）
            sqlSession.close();
        }
    }

    /**执行insert/update/delete写操作SQL
     * 新增、修改、删除等写入操作就需要对事务进行控制，作为事务来说，如果我们代码处理成功则需要将事务进行提交(commit),如果失败则需要进行回滚
     *在原有executeQuery/查询的基础上额外增加了对事务的控制
     * @param func 要执行的写操作代码块
     * @return 写操作后返回的结果
     */
    public static Object executeUpdate(Function<SqlSession, Object> func) {
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        sqlSession.commit();
        try {
            Object obj = func.apply(sqlSession);
            return obj;
            //如果有RuntimeException 运行时异常,则进行回滚
        } catch (RuntimeException e) {
            sqlSession.rollback();
            throw e;
        } finally {
            //用try...finally的设计保证了，一定要执行关闭连接 （回收连接池）
            sqlSession.close();
        }
    }
}
```

```java
//测试类
package com.imooc.oa.utils;
import org.junit.Test;

public class MybatisUtilsTestor {
    @Test
    public void testcase1() {
        String result = (String) MybatisUtils.executeQuery(sqlSession -> {
            String out = sqlSession.selectOne("test.sample");
            //这里的return out; 返回的是MybatisUtils.java里的Object obj，
            // 所以如果需要String类型的话就需要强制类型转换
            return out;
        });
        System.out.println(result);
    }

    @Test
    public void testcase2() {
        String result = (String) MybatisUtils.executeQuery(sqlSession ->sqlSession.selectOne("test.sample"));
        System.out.println(result);
    }
}
```



#### MyBatis整合Druid连接池

今日没懂的点：java重写，override。this的用法，super，

```java
package com.imooc.oa.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

public class DruidDataSourceFactory extends UnpooledDataSourceFactory {
    public DruidDataSourceFactory() {
        this.dataSource = new DruidDataSource();
    }

    @Override
    //重写父类方法
    //这里的DataSource是package javax.sql;提供的一个接口，
    // 这个接口要求所有的数据源都要实现这个接口。因为这里使用的是Druid所以返回的数据源接口
    //具体类型是DruidDataSource
    public DataSource getDataSource() {
        try {
            //上文中是this.dataSource = new DruidDataSource();对象，
            // 所以这里的也要强制类型转换
            ((DruidDataSource) this.dataSource).init();  //初始化Druid数据源
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this.dataSource;
    }
}

```

```xml
            <!--利用MyBatis自带连接池管理连接-->
            <!--<dataSource type="POOLED">-->
            <!--MyBatis框架在执行过程中就会从DataSourceFactory中获取数据源-->
            <dataSource type="com.imooc.oa.datasource.DruidDataSourceFactory">
                <!--JDBC连接属性-->
                <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
                <property name="url"
                          value="jdbc:mysql://localhost:3306/imooc-oa?useUnicode=true&amp;characterEncoding=UTF-8&amp;serverTimezone=Asia/Shanghai&amp;allowPublicKeyRetrieval=true"/>
                <property name="username" value="root"/>
                <property name="password" value="1234"/>
                <property name="initialSize" value="10"/>
                <property name="maxActive" value="20"/>

            </dataSource>
```



#### 整合Freemarker

```xml
 <!--通过maven导入-->       
<dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
            <version>2.3.31</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>3.1.0</version>
            <!--依赖只参与编译测试，不进行发布 （只在编译的时候需要用到，因为tomcat已经自带了）-->
            <scope>provided</scope>
        </dependency
```

```html
//test.flt
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>${result}</h1>
</body>
</html>
```

```xml
   <!--web.xml-->
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
         version="3.1">

    <servlet>
        <!--FreemarkerServlet 用于读取解析ftl文件-->
        <servlet-name>freemakrer</servlet-name>
        <servlet-class>freemarker.ext.servlet.FreemarkerServlet</servlet-class>
        <!--定义模板的存储路径-->
        <init-param>
            <param-name>TemplatePath</param-name>
            <param-value>WEB-INF/ftl</param-value>
        </init-param>
        <init-param>
            <!--default_encoding用于设置读取ftl文件时采用的字符集,进而避免中文乱码的产生-->
            <param-name>default_encoding</param-name>
            <param-value>UTF-8</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>freemakrer</servlet-name>
        <url-pattern>*.ftl</url-pattern>
    </servlet-mapping>
</web-app>
```

```java
//TestServlet.java 配置好tomcat用debug模式进行测试
package com.imooc.oa.test;

import com.imooc.oa.utils.MybatisUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "TestServlet", urlPatterns = "/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 映射地址是/test (urlPatterns = "/test"),最后会将请求转发至 "/test.ftl" ,
         * 再由模板引擎进行渲染输出（test.ftl里的${result}）
         */
        String result = (String) MybatisUtils.executeQuery(sqlSession -> sqlSession.selectOne("test.sample"));
//        String result = "wei";
        System.out.println(result);
        request.setAttribute("result", result);
        request.getRequestDispatcher("/test.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
```



#### RBAC 介绍与核心表

RBAC - 基于角色的访问控制

```markdown
Role-Based Access Control （角色权限控制）
基于角色权限控制(RBAC)是面向企业安全策略的访问控制方式
RBAC核心思想是将控制访问的资源与角色(Rol)进行绑定
系统的用户(User)与角色(Role)再进行绑定，用户便拥有对应权限

<h4>重点：所有的资源都与角色进行绑定</h4>
url对应的是访问资源
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220502180427997.png" alt="image-20220502180427997" style="zoom:50%;" />



#### 基于LayUI开发登录页

##### 官网：https://www.layuion.com/

```html
     /*登录页html login 知识点：LayUI的组件引用以及相对居中布局*/
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>慕课网办公OA系统</title>
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
    <style>
        body {
            background-color: #f2f2f2;
        }

        .oa-container {
            /*background-color: white;*/
            position: absolute;
            width: 400px;
            height: 350px;
            top: 50%;
            left: 50%;
            padding: 20px;
            margin-left: -200px;
            margin-top: -175px;
        }

        #username, #password {
            /*text-align: center;*/
            /*font-size: 24px;*/
        }
    </style>
</head>
<body>
<div class="oa-container">
    <h1 style="text-align: center;margin-bottom: 20px" ;>慕课网办公OA系统</h1>
    <form class="layui-form">
        <div class="layui-form-item">
            <label class="layui-form-label">username</label>
            <div class="layui-input-block">
                <input type="text" id="username" placeholder="请输入用户名" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">password</label>
            <div class="layui-input-block">
                <input type="text" id="password" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登录</button>
        </div>
    </form>
</div>
</body>
</html>
```



#### 实现用户登录



##### 何为DAO？

```
DAO(Data Access Object)是一个数据访问接口，数据访问：顾名思义就是与数据库打交道。夹在业务逻辑与数据库资源中间。
```

### MVC 	 model，view，controller	模型，视图，控制器

dao写好了，下一步是什么呢？ 需要开发 service

今日不解：super关键字，构造方法

```xml
<!--  查询语句-->
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
，
<mapper namespace="usermapper">
<!--    参数类型是String  返回值类型是一个entity下的User实体类-->
<!--    按用户名获取用户对象-->
    <select id="selectByUsername" parameterType="String"
            resultType="com.imooc.oa.entity.User">
        select * from sys_user where username = #{value}
    </select>
</mapper>
```

```java
//类似一个中间人？（供调用） 可供执行查询，并返回一个查询结果的对象
package com.imooc.oa.dao;

import com.imooc.oa.entity.User;
import com.imooc.oa.utils.MybatisUtils;

/**
 * 用户表Dao
 */
public class UserDao {
    /**
     * 按用户名查询用户表
     * @param username  用户名
     * @return  User对象包含对应的用户信息，null代表对象不存在
     */
    //方法名和刚刚定义的sql Id保持一致
    public User selectByUsername(String username){
        User user = (User) MybatisUtils.executeQuery(sqlSession -> sqlSession.selectOne("usermapper.selectByUsername",username));
        return user;

    }
}
```

```java
//自定义异常，检查登录结果
package com.imooc.oa.service;
import com.imooc.oa.dao.UserDao;
import com.imooc.oa.entity.User;
import com.imooc.oa.service.exception.BusinessException;

public class UserService {
    private UserDao userDao = new UserDao();
//    检查查询结果
    public User checkLogin(String username, String password){
        User user = userDao.selectByUsername(username);
        if (user == null){
            //抛出用户不存在异常     （抛出异常会中断程序）
            throw new BusinessException("L001","用户名不存在");
        }
        if (!password.equals(user.getPassword())){
            throw new BusinessException("L002","密码错误");
        }
            return user;
    }
}
```

```java
//调用自定义异常，检查登录结果
package com.imooc.oa.service;

import com.imooc.oa.entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService userService = new UserService();

    @Test
    public void checkLogin() {
        userService.checkLogin("w9","1234");
    }

    @Test
    public void checkLogin1() {
        userService.checkLogin("m8","1234");
    }

    @Test
    public void checkLogin2() {
        User user = userService.checkLogin("m8","test");
        //这里打印的user对象，即是user表。里边有字段具体值
        System.out.println(user);

    }
}
```

### MVC 	 model，view，controller	模型，视图，控制器

dao写好了，下一步是什么呢？ 需要开发 service，service之后呢 controller

```markdown
控制器采用service类完成。主要的职责是 ：接收来自于页面的用户的输入，以及调用业务逻辑并且返回结果
```



```java
//LoginServlet.java 
package com.imooc.oa.controller;

import com.alibaba.fastjson2.JSON;
import com.imooc.oa.entity.User;
import com.imooc.oa.service.UserService;
import com.imooc.oa.service.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//value    String[]   等价于 urlPatterns 属性，与该属性不能同时使用
//urlPatterns  String[]   指定Servlet url的匹配模式，等价于<url-parttern>(应该是可以在xml中的配置)
@WebServlet(name = "LoginServlet", urlPatterns = "/check_login")

public class LoginServlet extends HttpServlet {
    Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //响应中的字符集
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //接收用户输入
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String,Object> result = new HashMap<>();
        try {
            //调用业务逻辑
            User user = userService.checkLogin(username, password);
            result.put("code","0");
            result.put("message","success");
        } catch (BusinessException ex) {
            logger.error(ex.getMessage(), ex);
            result.put("code",ex.getCode());
            result.put("message",ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            result.put("code",ex.getClass().getSimpleName());   //类名作为编码？
            result.put("message",ex.getMessage());
        }
        //将map转化为json字符串并返回
        String json = JSON.toJSONString(result);
        //将json字符串打印
        response.getWriter().println(json);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
```

```html
//login.html 用ajax向/check_login发送请求        
<div class="layui-form-item">
            <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="login">登录</button>
        </div>
    </form>
</div>
<script src="/resources/layui/layui.js"></script>
<!--在script块中要对表单数据进行提交处理-->
<script>
    <!--submit(login)与lay-filter="login"的对应-->
    layui.form.on('submit(login)', function (formdata) {  //data参数包含了当前表单的数据
        console.log(formdata);
        //作为ajax校验，下方的return 必须是return false。如果是return true就会按照标准的表单形式进行数据的提交并且返回响应，这样的ajax就会失效
        layui.$.ajax({
            url: "/check_login",
            data: formdata.field,
            type: "post",
            //dataType 代表了服务器返回的数据类型是什么
            dataType: "json",
            success: function (json) {
                console.log(json);
                if (json.code == "0") {
                    //layui内置的弹出层，会有一个小提示
                    layui.layer.msg("登录成功");
                } else {
                    layui.layer.msg(json.message);
                }
            }
        })
        return false;   //submit提交事件返回true则表单提交，false则阻止表单提交
    })
</script>
```



#### 动态显示功能菜单

##### “用户首页中根据角色的不同，要显示不同的功能”

```html
<!-- index.html -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>慕课网办公OA系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="/resources/layui/css/layui.css">
</head>

<body class="layui-layout-body">
<!-- Layui后台布局CSS -->
<div class="layui-layout layui-layout-admin">
    <!--头部导航栏-->
    <div class="layui-header">
        <!--系统标题-->
        <div class="layui-logo" style="font-size:18px">慕课网办公OA系统</div>
        <!--右侧当前用户信息-->
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:void(0)">
                    <!--图标-->
                    <span class="layui-icon layui-icon-user" style="font-size: 20px">
                    </span>
                    <!--用户信息-->
                    姓名[部门-职务]
                </a>
            </li>
            <!--注销按钮-->
            <li class="layui-nav-item"><a href="#">注销</a></li>
        </ul>
    </div>
    <!--左侧菜单栏-->
    <div class="layui-side layui-bg-black">
        <!--可滚动菜单-->
        <div class="layui-side-scroll">
            <!--可折叠导航栏-->
            <ul class="layui-nav layui-nav-tree">
                <!--父节点-->
                <li class="layui-nav-item layui-nav-itemed">
                    <a href="javascript:void(0)">模块1</a>
                    <dl class="layui-nav-child module" data-node-id="1"></dl>
                </li>
                <!--子节点-->
                <dd class="function" data-parent-id="1">
                    <a href="javascript:void(0)" target="ifmMain">功能1</a>
                </dd>
                <dd class="function" data-parent-id="1">
                    <a href="javascript:void(0)" target="ifmMain">功能2</a>
                </dd>
                <dd class="function" data-parent-id="1">
                    <a href="javascript:void(0)" target="ifmMain">功能3</a>
                </dd>
                <li class="layui-nav-item layui-nav-itemed">
                    <a href="javascript:void(0)">模块2</a>
                    <dl class="layui-nav-child module" data-node-id="2"></dl>
                </li>
                <dd class="function" data-parent-id="2">
                    <a href="javascript:void(0)" target="ifmMain">功能3</a>
                </dd>
                <dd class="function" data-parent-id="2">
                    <a href="javascript:void(0)" target="ifmMain">功能4</a>
                </dd>
                <dd class="function" data-parent-id="2">
                <!-- target="ifmMain" target属性的作用就是说明在哪个iframe中显示对应的连接内容-->
                    <a href="javascript:void(0)" target="ifmMain">功能5</a>
                </dd>
            </ul>
        </div>
    </div>
    <!--主体部分采用iframe嵌入其他页面-->
    <div class="layui-body" style="overflow-y: hidden">
        <iframe name="ifmMain" style="border: 0px;width: 100%;height: 100%"></iframe>
    </div>
    <!--版权信息-->
    <div class="layui-footer">
        Copyright © imooc. All Rights Reserved.
    </div>
</div>
<!--LayUI JS文件-->
<script src="/resources/layui/layui.js"></script>
<script>
    //将所有功能根据parent_id移动到指定模块下
    layui.$(".function").each(function () {
        var func = layui.$(this);
        var parentId = func.data("parent-id");
        layui.$("dl[data-node-id=" + parentId + "]").append(func);
    })
    //刷新折叠菜单
    layui.element.render('nav');
</script>
</body>
</html>
```



#### 动态显示功能菜单

```sql
-- 如何获取编号为1的用户拥有哪些功能？--
-- DISTINCT 去掉重复的数据--
SELECT DISTINCT n.*
from 
sys_role_user ru , sys_role_node rn,sys_node n
where
ru.role_id = rn.role_id and user_id = 1 and rn.node_id = n.node_id
ORDER BY n.node_code desc


-- 多表联结尚未掌握 --
SELECT DISTINCT
	sys_node.* 
FROM
	sys_role_user,
	sys_role_node,
	sys_node 
WHERE
	sys_role_user.role_id = sys_role_node.role_id 
	AND user_id = 1 
	AND sys_role_node.node_id = sys_node.node_id 
ORDER BY
	sys_node.node_code
```

```xml
<!--rbac.xml --!>
<mapper namespace="rbacmapper">
    <!--    按照用户id获取指定的功能节点-->
    <select id="selectNodeByUserId" resultType="com.imooc.oa.entity.Node" parameterType="Long">
        --      如何获取编号为1的用户拥有哪些功能？--
        --      DISTINCT 去掉重复的数据--
        SELECT DISTINCT n.*
        from sys_role_user ru,
             sys_role_node rn,
             sys_node n
        where ru.role_id = rn.role_id
          and user_id = #{value}
          and rn.node_id = n.node_id
        ORDER BY n.node_code
    </select>
```

按开发套路 xml写完，着手准备dao了

mybatis查询的数据个别字段是null ？麻

问题是LoginServlet 压根拿不到userId啊，就没法通过session把userId传给 IndexServlet

LoginServlet 是因为UserService的问题，UserService拿不到userId

UserService拿不到是因为UserDao拿不到		[草（一种植物），最后修改mysql表字段解决了。大概率是下划线打成中文了，匹配不到]

![image-20220509185704860](C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220509185704860.png)

多表关联。。不会

```java
//LoginServlet.java中的doPost方法
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //响应中的字符集
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    //接收用户输入
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    Map<String, Object> result = new HashMap<>();
    try {
        //调用业务逻辑
        User user = userService.checkLogin(username, password);
        //这里加入了session是为了让IndexServlet拿到userId查询，具体用户数据
        HttpSession session = request.getSession();
        //向session存入登录用户信息，属性名：login_user
        session.setAttribute("login_user", user);
        result.put("code", "0");
        result.put("message", "success");
        //加入redirect_url，方便在html代码中window.localtion.href到index页面
        result.put("redirect_url", "/index");
```

```java
//IndexServlet.java
@WebServlet(name = "IndexServlet", urlPatterns = "/index")
public class IndexServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //IndexServlet 怎么拿到LoginServlet 登录验证通过的user信息呢？ 通过session
        HttpSession session = request.getSession();
        //得到当前登录用户对象
        User user = (User)session.getAttribute("login_user");
        //获取登录用户可用功能模块列表
        List<Node> nodeList = userService.selectNodeByUserId(user.getUserId());
        //将当前的结点列表放入请求中 （放入请求属性）
        request.setAttribute("node_list", nodeList);
        //最后利用请求转发  （请求派发至ftl进行展示）
        request.getRequestDispatcher("/index.ftl").forward(request, response);

        //shift + f9 打开调试，选择restart server  重新部署发布
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
```



```java
//RbacDao.java 通过userId查询用户信息
package com.imooc.oa.dao;

import com.imooc.oa.entity.Node;
import com.imooc.oa.utils.MybatisUtils;

import java.util.List;

public class RbacDao {
    //   每一个查询结果都是一个Node节点 为什么用list，因为返回多个数据所以用list进行接收
    public List<Node> selectNodeByUserId(Long userId) {
        return  (List) MybatisUtils.executeQuery(sqlSession -> sqlSession.selectList("rbacmapper.selectNodeByUserId", userId));

    }
}
```



```javascript
//login.html	登录验证通过后，跳转到index页面
success: function (json) {
    console.log(json);
    if (json.code == "0") {     //登录验证成功
        //layui内置的弹出层，会有一个小提示
        // layui.layer.msg("登录成功");
        //跳转url
        window.location.href=json.redirect_url;
    } else {
        layui.layer.msg(json.message);
    }
}
```

```html
//动态的显示左侧导航栏
<!--左侧菜单栏-->
<div class="layui-side layui-bg-black">
    <!--可滚动菜单-->
    <div class="layui-side-scroll">
        <!--可折叠导航栏-->
        <ul class="layui-nav layui-nav-tree">
            <#list  node_list as node>
                <#if node.nodeType ==1>
                <#--父节点-->
                    <li class="layui-nav-item layui-nav-itemed">
                        <a href="javascript:void(0)">${node.nodeName}</a>
                        <dl class="layui-nav-child module" data-node-id="${node.nodeId}"></dl>
                    </li>
                </#if>
                <#if node.nodeType == 2>
                <#--子节点-->
                    <dd class="function" data-parent-id="${node.parentId}">
                        <a href="javascript:void(0)" target="ifmMain">${node.nodeName}</a>
                    </dd>
                </#if>
            </#list>
        </ul>
    </div>
</div>
```



Xml配置下实现Mapper接口（实用的MyBatis开发技巧）

interface 接口类是怎么回事



开发流程：先写好对应的数据库实体类与数据库表对应，二是Dao，三是书写查询语句xml，四是查询该表数据，五是写部门service服务将四查询到的数据传到前端页面

##### 流程

1. 写好对应的数据库实体类与数据库表对应

   ```java
   package com.imooc.oa.entity;
   public class Department {
       private Long departmentId;
       private String departmentName;
   	//getter和setter方法
   }
   ```

2. Dao接口（沟通数据库和实体）

   ```java
   /*DAO(Data Access Object)是一个数据访问接口，数据访问：顾名思义就是与数据库打交道。夹在业务逻辑与数据库资源中间。*/
   package com.imooc.oa.dao;
   import com.imooc.oa.entity.Department;
   
   public interface DepartmentDao {
       public Department selectById(Long departmentId);
   }
   ```

3. xml 查询数据库语句以及关联Dao和第一步对应的实体类

   ```xml
   <!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <!--namespace与包名类名一致-->
   <mapper namespace="com.imooc.oa.dao.DepartmentDao">
       <!--id与方法名对应
       parameterType与方法参数类型对应
       resultType与方法返回类型对应-->
       <select id="selectById" parameterType="Long"
               resultType="com.imooc.oa.entity.Department">
           select *
           from adm_department
           where department_id = #{value}
       </select>
   </mapper>
   ```

4. 创建service

   ```java
   /**
    * 部门服务
    */
   public class DepartmentService {
       /**
        * 按编号id得到部门对象
        * @param departmentId 部门编号
        * @return  部门对象。null代表部门不存在
        */
       public Department selectById(Long departmentId) {
           return (Department) MybatisUtils.executeQuery(sqlSession ->
                   sqlSession.getMapper(DepartmentDao.class).selectById(departmentId)
           );
       }
   }
   ```

5. 查询到的数据传到前端页面

   ```java
   session.setAttribute("current_department",department);
   ```

   ```html
                   <a href="javascript:void(0)">
                       <!--图标-->
                       <span class="layui-icon layui-icon-user" style="font-size: 20px">
                       </span>
                       <!--用户信息 前端页面引用-->
                       ${current_employee.name}[${current_department.departmentName}-${current_employee.title}]
                   </a>
   ```



#### 基于MD5算法对密码加密

##### MD5摘要算法

```
MD5信息摘要算法广泛使用的密码散列函数
MD5可以产生出一个128位的散列值用于唯一标识源数据
项目中通常使用MD5作为敏感数据的加密算法
```

##### MD5特点

```
压缩性，MD5生成的摘要长度固定
抗修改，源数据哪怕有一个字节变化，MD5也会有巨大差异
不可逆，无法通过MD5反向推算源数据
```

##### Apache Commons Codec

```
Commons-Codec是Apache提供的编码/解码组件
通过Commons-Codec可轻易生成源数据的MD5摘要
MD5摘要方法：String md5=DigestUtils.md5Hex(源数据)
```

```java
//生成MD5
import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    public static String md5Digest(String source){
        return DigestUtils.md5Hex(source);
    }
}
```

```java
public class MD5UtilsTest {
    @Test
    public void md5Digest() {
        System.out.println(MD5Utils.md5Digest("test8we*"));
        //098f6bcd4621d373cade4e832627b4f6 原数据test 还是有可能被破解的    https://www.cmd5.com/ 能被还原出来
        //78818cce49a499a6f7e561c11c9773b1 源数据 test8we* 像这个就破解不了
    }
}
```



#### 敏感数据加盐混淆

```java
    /**
    MD5Utils.java
     * 对源数据加盐混淆后生成MD5摘要
     *
     * @param source 源数据
     * @param salt   盐值
     * @return Md5摘要
     */
    public static String md5Digest(String source, Integer salt) {
        char[] ca = source.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            ca[i] = (char) (ca[i] + salt);
        }
        String target = new String(ca);
//        System.out.println(target);
        String md5 = DigestUtils.md5Hex(target);
        return md5;
    }

    public static void main(String[] args) {
//        MD5Utils.md5Digest("test",1);   // 打印了 uftu，即 test的阿斯克码的基础上加1。t+1 = u
//        MD5Utils.md5Digest("wei",1);
        System.out.println(md5Digest("test", 188));
        System.out.println("==========");
        for (int i = 188;i<198;i++){
            System.out.println(i+"的盐值是 ："+md5Digest("test",i));
        }
    }
```

```java
//在实体类User中加 salt 字段对应数据库的salt
package com.imooc.oa.entity;

public class User {
    private Long userId;    //user_id是在mysql数据表中的定义名
    private String username;
    private String password;
    private Long employeeId;
    private Integer salt;

    public Long getUserId() {
        return userId;
    }
```

```java
//对前台输入的密码加盐混淆后生成MD5，与保存在数据库中的MD5密码进行比对
String password_md5 = MD5Utils.md5Digest(password, user.getSalt());
if (!password_md5.equals(user.getPassword())) {
    throw new BusinessException("L002", "密码错误");
}
```



#### 实现注销操作

把登录的信息给予清除

```java
/**
Logout.java
 * #### 实现注销操作
 * 把登录的信息给予清除
 */
//  urlPatterns = "/Logout"
@WebServlet(name = "LogoutServlet", value = "/Logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //两种清除session的方式    removeAttribute要传入session名参数 login_user。    invalidate更常用
        //request.getSession().removeAttribute();
        request.getSession().invalidate();
        //重定向跳转到登录页面
        response.sendRedirect("/login.html");
    }
}
```

```html
<!--注销按钮 index.ftl-->
<li class="layui-nav-item"><a href="/Logout">注销</a></li>
```



#### 请假流程数据库设计

##### 开发多级审批流程

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220513210002273.png" alt="image-20220513210002273" style="zoom:50%;" />

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220513210354410.png" alt="image-20220513210354410" style="zoom:50%;" />

##### 重点：工作流程在数据中怎么表示？		这是一个问题

##### 设计约束

- 每一个请假单对应一个审批流程
- 请假单创建后，按业务规则生成部门经理、总经理审批任务
- 审批任务的经办人只能审批自己辖区内的请假申请
- 所有审批任务"通过"，代表请假已经批准
- 任意审批任务“驳回”操作，其余审批任务取消，请假申请被驳回
- 请假流程中任意节点产生的操作都要生成对应的系统通知

```
数据表
adm_leave_form	请假申请表
adm_process_flow 处理流程（任务流程）
sys_notice	系统提醒表
```



#### 实现Dao与数据交互

```
首先作为交互部分最基础的就是实体类了 entity
接着实体类写完就到 dao上面	（dao存储的是一系列数据交互的接口）LeavenFromDao接口
dao的接口实现是依托于MyBatis来实现的，所以还要书写xml	（mysql插入语句可以从navicat中复制。ctrl + r 即可替换选中的字符）	Ctrl shift t 创建新的测试类

```

```java
//用LeaveFromDaoTest.java进行测试连接的时候发现 [mybatis显示修改数据库成功, 但没有修改数据库的原因 (mybatis可以查询但无法修改数据库)],即没有在navicat中看到新增的插入数据。
//        SqlSession sqlSession = sqlSessionFactory.openSession(false); //原来课程是这个.当然也可能是记错了
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        sqlSession.commit();
```

```xml
// notice.xml 
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.imooc.oa.dao.NoticeDao">
    <!--useGeneratedKeys="true"使用数据库的自动生成键键   keyProperty="formId"实体中哪个的属性名是主键 keyColumn="form_id" 数据库字段哪个是主键-->
    <insert id="insert" parameterType="com.imooc.oa.entity.Notice"
            useGeneratedKeys="true" keyProperty="noticeId" keyColumn="notice_id">
        INSERT INTO sys_notice(receiver_id, content, create_time)
        VALUES (#{receiverId},
            #{content}, #{createTime});
    </insert>

</mapper>
```

```java
//向sys_notice插入数据	
package com.imooc.oa.dao;

import com.imooc.oa.entity.Notice;
import com.imooc.oa.utils.MybatisUtils;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class NoticeDaoTest {

    @Test
    public void insert() {
        MybatisUtils.executeUpdate(sqlSession -> {
            NoticeDao dao = sqlSession.getMapper(NoticeDao.class);
            Notice notice = new Notice();
            notice.setNotice(4L);
            notice.setReceiverId(5L);
            notice.setContent("您的请假已被部门经理XXX批准，审批意见：同意");
            notice.setCreateTime(new Date());
            dao.insert(notice);
            return null;
        });
    }
}
```



#### 实现请假申请业务逻辑

```
//1.持久化form表单数据，8级以下员工表单状态为processing,8级（总经理）状态为approved
//2.增加第一条流程数据，说明表单已提交，状态为complete
//3.分情况创建其余流程数据
//3.1   7级以下员工，生成部门经理审批任务，请假时间大于72小时，还需生成总经理审批任务
//3.2   7级员工，生成总经理审批任务
//3.3   8级员工，生成总经理审批任务，系统自动通过
```

```java
//在EmployeeDao中新增 根据传入员工对象获取上级主管对象
/**
 * 根据传入员工对象获取上级主管对象
 * @param employee 员工对象
 * @return  上级主管对象
 */
public Employee selectLeader(@Param("emp") Employee employee);
```

```xml
<!-- employee.xml 一些说明：emp.level的emp是从EmployeeDao传过来的。如果级别小于7的话 -->
<select id="selectLeader" parameterType="com.imooc.oa.entity.Employee"
        resultType="com.imooc.oa.entity.Employee">
    SELECT *
    from adm_employee
    where
    <if test="emp.level &lt; 7">
        level = 7 and department_id = #{emp.departmentId}
    </if>
    <if test="emp.level == 7">
        level = 8
    </if>
    <if test="emp.level == 8">
        employee_id = #{emp.employeeId}
    </if>
</select>
```

```java
//LeaveFormService.java 
package com.imooc.oa.service;

import com.imooc.oa.dao.EmployeeDao;
import com.imooc.oa.dao.LeaveFromDao;
import com.imooc.oa.dao.ProcessFlowDao;
import com.imooc.oa.entity.Employee;
import com.imooc.oa.entity.LeaveForm;
import com.imooc.oa.entity.ProcessFlow;
import com.imooc.oa.utils.MybatisUtils;

import java.util.Date;

/**
 * 请假单流程服务
 */
public class LeaveFormService {
    /**
     * 创建请假单
     *
     * @param form 前端输入的请假单数据
     * @return 持久化后的请假单对象
     */
    public LeaveForm createLeaveForm(LeaveForm form) {
        LeaveForm savedForm = (LeaveForm) MybatisUtils.executeUpdate(sqlSession -> {
            //1.持久化form表单数据，8级以下员工表单状态为processing,8级（总经理）状态为approved
            EmployeeDao employeeDao = sqlSession.getMapper(EmployeeDao.class);
            Employee employee = employeeDao.selectById(form.getEmployeeId());
            if (employee.getLevel() == 8) {
                form.setState("approved");
            } else {
                form.setState("processing");
            }
            LeaveFromDao leaveFromDao = sqlSession.getMapper(LeaveFromDao.class);
            leaveFromDao.insert(form);

            //2.增加第一条流程数据，说明表单已提交，状态为complete
            ProcessFlowDao processFlowDao = sqlSession.getMapper(ProcessFlowDao.class);
            ProcessFlow flow1 = new ProcessFlow();
            flow1.setFormId(form.getFormId());
            flow1.setOperatorId(employee.getEmployeeId());
            flow1.setAction("apply");
            flow1.setCreateTime(new Date());
            flow1.setOrderNo(1);
            flow1.setState("complete");
            flow1.setIsLast(0); //作为当前的这个流程，是否为最后一个节点 （0 不是）
            processFlowDao.insert(flow1);   //将第一个流程数据放入其中
            //3.分情况创建其余流程数据
            //3.1   7级以下员工，生成部门经理审批任务，请假时间大于72小时，还需生成总经理审批任务
            if (employee.getLevel() < 7) {
                Employee dmanager = employeeDao.selectLeader(employee);     //这里得到的是部门经理    dmanager
                ProcessFlow flow2 = new ProcessFlow();
                flow2.setFormId(form.getFormId());
                flow2.setOperatorId(dmanager.getEmployeeId());
                flow2.setAction("audit");
                flow2.setCreateTime(new Date());
                flow2.setOrderNo(2);
                flow2.setState("process");
                long diff = form.getEndTime().getTime() - form.getStartTime().getTime();    //得到单位是毫秒的值
                float hours = diff / (1000 * 60 * 60) * 1f;     //转为小时
                if (hours >= BusinessConstants.MANAGER_AUDIT_HOURS) {
                    flow2.setIsLast(0);
                    processFlowDao.insert(flow2);
                    Employee manager = employeeDao.selectLeader(dmanager);
                    ProcessFlow flow3 = new ProcessFlow();
                    flow3.setFormId(form.getFormId());
                    flow3.setOperatorId(manager.getEmployeeId());
                    flow3.setAction("audit");
                    flow3.setCreateTime(new Date());
                    flow3.setState("ready");
                    flow3.setOrderNo(3);
                    flow3.setIsLast(1);
                    processFlowDao.insert(flow3);
                } else {
                    flow2.setIsLast(1);
                    processFlowDao.insert(flow2);
                }

            } else if (employee.getLevel() == 7) {    //部门经理
                //3.2   7级员工，生成总经理审批任务
                Employee manger = employeeDao.selectLeader(employee);
                ProcessFlow flow = new ProcessFlow();
                flow.setFormId(form.getFormId());
                flow.setOperatorId(manger.getEmployeeId());
                flow.setAction("audit");
                flow.setCreateTime(new Date());
                flow.setState("process");
                flow.setOrderNo(2);     //任务流程中的第二个节点
                flow.setIsLast(1);
                processFlowDao.insert(flow);

            } else if (employee.getLevel() == 8) {
                //3.3   8级员工，生成总经理审批任务，系统自动通过
                ProcessFlow flow = new ProcessFlow();
                flow.setFormId(form.getFormId());
                flow.setOperatorId(employee.getEmployeeId());
                flow.setAction("audit");
                flow.setResult("approved"); //通过
                flow.setReason("自动通过");
                flow.setCreateTime(new Date());
                flow.setAuditTime(new Date());
                flow.setOrderNo(2);
                flow.setIsLast(1);
                processFlowDao.insert(flow);
            }
            return form;
            // 注：将文字描述的代码放入代码块中，这样可以保证所有的程序操作要么一次性全部提交成功，
            // 要么全部回滚
        });
        return savedForm;
    }
}
```



#### 实现请假单申请控制器

三部曲：

1. 接收各项请假单数据
2. 根据业务逻辑的方法来组织输入的数据，并且调用对应的方法
3. 当方法调用成功或者调用失败以后，那我们要组织响应的结果，通过response进行输出
3. 引入form.html 和弹出框插件



#### 完整实现请假申请功能

```
sweetalert2.all.min.js 弹出框插件
```

2022年5月19日09:11:19 获取网页数据出错

（上边的问题已明了，因为要先获取session。那么就需要进行登录）

如果需要与服务器进行交互的话，要将form.html改为form.ftl脚本



2022年5月22日22:08:43 leave_form表单的reason字段没有获取到。以及process_flow流程表的逻辑不对。如果是请两天假应该只需要生成两条流程数据而不是三条

2022年5月23日11:03:55 上面的问题已解决。自己没有认真填写测试数据



#### 实现待审批请假单列表

```mysql
SELECT
	f.* 
FROM
	adm_leave_from f,
	adm_process_flow pf 
WHERE
	f.form_id = pf.form_id 
	AND pf.state = 'process' 
	AND pf.operator_id = 2
	
	//如何在这个基础上查询员工和部门的信息
SELECT
	f.*,
	e.`name`,
	d.* 
FROM
	adm_leave_from f,
	adm_process_flow pf,
	adm_employee e,
	adm_department d 
WHERE
	f.form_id = pf.form_id 
	AND f.employee_id = e.employee_id 
	AND e.department_id = d.department_id 
	AND pf.state = 'process' 
	AND pf.operator_id = 2
```



```xml
//leave_form.xml
<!--多参数查询 通过Map传递参数查询以及接收结果-->
<!--查询的是多表的字段，得到的字段也是从多个表获取的。并没有任何哪个实体类能承载全部的字段。所以用map-->
<select id="selectByParams" parameterType="java.util.Map" resultType="java.util.Map">
    SELECT f.*,
           e.`name`,
           d.*
    FROM adm_leave_from f,
         adm_process_flow pf,
         adm_employee e,
         adm_department d
    WHERE f.form_id = pf.form_id
      AND f.employee_id = e.employee_id
      AND e.department_id = d.department_id
      AND pf.state = #{pf_state}
      AND pf.operator_id = #{pf_operator_id}
</select>
```



```java
//LeaveFromDao.java
//    public List<Map> selectByParams( Map params); 不推荐
//通过@Param 说明在他们xml中的参数名是什么
public List<Map> selectByParams(@Param("pf_state") String pfState, @Param("pf_operator_id") Long operatorId);
```

```java
//测试类	LeaveFromDaoTest.java
@Test
public void selectByParams() {
    MybatisUtils.executeQuery(sqlSession -> {
        LeaveFromDao dao = sqlSession.getMapper(LeaveFromDao.class);
        List<Map> list = dao.selectByParams("process", 2L);
        System.out.println(list);   //这里打印的字段名是和数据表的字段名一致的（原始字段名）。因为是通过多表查询的，后面使用也要用该字段名 {start_time=2022-05-22 00:00:00.0, reason=我有事,要请假, create_time=2022-05-22 23:18:33.0, department_id=2, employee_id=4, department_name=研发部, form_id=26, end_time=2022-06-23 00:00:00.0, name=宋彩妮, form_type=1, state=processing}, {start_time=2022-05-23 00:00:00.0, reason=有事，我请一天假, create_time=2022-05-23 10:59:57.0, department_id=2, employee_id=4, department_name=研发部, form_id=27, end_time=2022-05-23 23:00:00.0, name=宋彩妮, form_type=1, state=processing}
        return list;
    });
}
```

```java
//    这是经过LeaveFromDaoTest.java 测试之后才写在这里的
    public List<Map> getLeaveFormList(String pfState, Long operatorId) {
        return (List<Map>) MybatisUtils.executeQuery(sqlSession -> {
            LeaveFromDao dao = sqlSession.getMapper(LeaveFromDao.class);
            List<Map> formList = dao.selectByParams(pfState, operatorId);
            return formList;
        });
    }
```

```java
if (methodName.equals("create")) {
    //创建请假单的处理
    this.create(request, response);
} else if (methodName.equals("list")) {
    //通过localhost/list 即可拿到服务器响应的数据（前提是先登录拿到session里的用户操作人信息，process字段和员工id）
    this.getLeaveFormList(request, response);
}

/**
 * 查询需要审核的请假单列表
 * @param request
 * @param response
 * @throws IOException
 */
private void getLeaveFormList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //通过session 接收各项请假单数据
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("login_user");
    List<Map> formList = leaveFormService.getLeaveFormList("process", user.getEmployeeId());
    Map result = new HashMap();
    result.put("code", "0");    //返回0 代表服务器响应成功
    result.put("msg", "");
    result.put("count", formList.size());   //count 所有数据的总数
    result.put("data", formList);
    String json = JSON.toJSONString(result);
    System.out.println(json);
    //通过响应进行输出
    response.getWriter().println(json);
}
```



"引入audit.ftl,即审核页面。害。不好意思，今天啥也没看" 2022年5月25日23:54:05

2022年5月26日23:23:37 如何完成两个组件间的消息传递？

2022年5月27日00:10:30 内嵌框架怎么完成？哈哈哈这个不解了

2022年5月28日00:48:46

```xml
//在process_flow.xml中新增
<update id="update" parameterType="Long">
    UPDATE adm_process_flow
    SET form_id    = #{formId},
        operator_id=#{operatorId},
        action     = #{action},
        result=#{result},
        where process_id = #{processId}
</update>
<select id="selectByFormId" parameterType="Long" resultType="com.imooc.oa.entity.ProcessFlow">
    select *
    from adm_process_flow
    where form_id = #{value}
    order by order_no
</select>
```



```java
//ProcessFlowDao.java
public void update(ProcessFlow processFlow);

public List<ProcessFlow> selectByFormId(Long formId);
```



```java
public void audit(Long formId, Long operatorId, String result, String reason) {
    ProcessFlow processFlow = new ProcessFlow();
    MybatisUtils.executeUpdate(sqlSession -> {
        //1.无论同意/驳回，当前任务状态更变为complete
        ProcessFlowDao processFlowDao = sqlSession.getMapper(ProcessFlowDao.class);//得到实现类
        List<ProcessFlow> flowList = processFlowDao.selectByFormId(formId);
        if (flowList.size() == 0) {
            throw new BusinessException("PF001", "无效的审批流程");
        }
        //获取当前任务ProcessFlow对象
        List<ProcessFlow> processFlowList = flowList.stream().filter(p -> p.getProcessId() == operatorId && p.getState().equals("process")).collect(Collectors.toList());//filter() 对流数据进行筛选 p 代表每一个流程数据 当前节点的经办人和当前用户的编号是匹配的 最后筛选完成之后再生成一个新的list
        ProcessFlow process = null;
        if (processFlowList.size() == 0) {
            throw new BusinessException("PF002", "未找到待处理任务");
        } else {
            process = processFlowList.get(0);
            process.setState("complete");   //如果找到符合筛选条件的，将process改为complete （经过操作后）
            process.setResult(result);
            process.setReason(reason);
            process.setAuditTime(new Date());
            processFlowDao.update(process); //完成更新工作
        }
        //2.如果当前任务是最后一个节点，代表流程结束，更新请假单状态为对应的approved/refused
        //3.如果当前任务不是最后一个节点且审批通过，那下一个节点的状态从ready变为process
        //4.如果当前任务不是最后一个节点且审批驳回，则后续所有任务状态变为cancel，请假单状态变为refused

    });
}
```



2022年5月29日00:35:40 这里更新一个，好家伙全部都改了

![image-20220529003602033](C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220529003602033.png)

2022年5月29日16:52:53 上边的问题已解决。原因是没有限定form_id,那么sql语句就会将其全部都更新成一样的



#### 实现系统消息业务逻辑

开发系统通知功能

疑问1：插入 notice 的insert方法·要在原始的业务逻辑中什么时候产生？



2022年6月3日22:10:18 好几天没怎么学了，好懈怠	这是OA系统的倒数第二节	

#### 完整实现系统消息功能

关于如何将写入的信息读取出来 notice

```xml
//notice.xml 
<!--    按时间倒序排列，即最新的信息在最上边.只对最近一百条数据进行加载-->
<select id="selectByReceiverId" parameterType="Long" resultType="com.imooc.oa.entity.Notice">
    select *
    from sys_notice
    where receiver_id = #{value }
    order by create_time desc limit 0,100
</select>
```

```java
//NoticeService.java	提供服务 比如查询？并可以通过servlet获取该service服务的数据
package com.imooc.oa.service;
import com.imooc.oa.dao.NoticeDao;
import com.imooc.oa.entity.Notice;
import com.imooc.oa.utils.MybatisUtils;

import java.util.List;

public class NoticeService {
    public List<Notice> getNoticeList(Long receiverId) {
        return (List) MybatisUtils.executeQuery(sqlSession -> {
            NoticeDao noticeDao = sqlSession.getMapper(NoticeDao.class);
            return noticeDao.selectByReceiverId(receiverId);
        });}}
```



```java
//NoticeServlet.java	程序 充当服务器的角色
@WebServlet(name = "NoticeServlet", urlPatterns = "/notice/list")
public class NoticeServlet extends HttpServlet {

    //创建Service 对象
    private NoticeService noticeService = new NoticeService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //完成数据查询的工作
        User user = (User) request.getSession().getAttribute("login_user");    //从当前会话中，将登录用户的信息提取出来
        List<Notice> noticeList = noticeService.getNoticeList(user.getEmployeeId()); //这里就可以查到这个员工最近要查看的一百条信息，返回的是一个集合
        Map result = new HashMap<>();
        result.put("code", "0");
        result.put("msg", "");
        result.put("count", noticeList.size());
        result.put("data", noticeList);
        String json = JSON.toJSONString(result);   //转为json字符串
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(json);  //通过响应向客户端返回，进行输出
    }}
```



#### 课程总结

不同角色显示的功能不同是怎么做到的？rbac权限管理	（基于角色的控制访问）

怎么实现切换node节点就自动向数据库发起查询更新 



##### 系统项目开发设计比编码重要。如何有效的对数据进行控制
