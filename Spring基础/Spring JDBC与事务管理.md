## Spring JDBC与事务管理



<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20220803213804785.png" alt="image-20220803213804785" style="zoom:50%;" />

#### 介绍Spring JDBC

```html
<Spring JDBC>
Spring JDBC是Spring框架用于处理关系型数据库的模块
Spring JDBC对JDBC API进行封装，极大简化开发工作量
JdbcTemplate,是Spring JDBC核心类，提供数据CRUD方法
    
问题 : 有Mybatis为什么还需要Spring JDBC？
    答 : 两者面向的对象是不一样的。Mybatis作为一个orm框架，它封装的程度较高，适合于中小企业进行软件的敏捷开发，让程序员能快速的完成与数据库的交互工作，但是我们学过Mybatis都知道这里涉及到一系列的比如xml的配置以及各种各样的操作的细节，实际上封装的程度还是比较高的，封装程度高就意味着我们执行效率相对较低。 但是Spring JDBC只是对原始的JDBC的api进行的简单封装，对一线的互联网大厂无论是数据量还是用户的并发量都是非常高的，这时如果使用MyBatis可能因为微小的性能上的差距就会导致整体应用变慢，因此作为一线大厂很少使用市面上成熟的框架，更多时候喜欢使用像Spring JDBC这样轻量级的封装框架，在这个基础上结合自己企业的特点来进行二次的封装。可以说Spring JDBC它的执行效率比MyBatis是要高的，同时因为有了spring底层ioc容器的存在，也不至于让程序像原生jdbc那样难以管理。sping jdbc是介于orm框架和原生jdbc之间的一个折中的选择。
```

```
<Spring JDBC的使用步骤>
Maven工程引入依赖<spring-jdbc>
applicationContext.xml配置<DataSource>数据源		//哪种数据库  用户名和密码是什么
在Dao注入<JdbcTemplate>对象，实现数据CRUD（增删改查）
```



#### Spring JDBC配置过程

##### JdbcTemplate实现增删改查

```

```

