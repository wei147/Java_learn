## 第2节 Spring Cloud开发课程查询功能

#### 课程项目整体介绍

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221127003213442.png" alt="image-20221127003213442" style="zoom:50%;" />



#### Spring Cloud核心组件介绍

##### Spring Cloud简介

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221127003810876.png" alt="image-20221127003810876" style="zoom:40%;" />



#### 项目技术设计

```
我们的目标是实现一个最小粒度的慕课网的课程查询项目,一共包含两个模块 分别是课程列表模块和课程价格模块,,,,
```

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221127222333706.png" alt="image-20221127222333706" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221127222446930.png" alt="image-20221127222446930" style="zoom:40%;" />

<hr>

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221127223921349.png" alt="image-20221127223921349" style="zoom:50%;" />

```
在项目整体设计中,主要包含了项目介绍,接口设计、数据流向和表设计。这些在未来工作中应该是必不可少的,也许简历可以加上这个?
```



#### 新建多模块项目

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221130115005555.png" alt="image-20221130115005555" style="zoom:80%;" />

#### 课程列表模块开发

```
每一个模块实际上是一个Spring Boot项目
```

<hr>

```java
//application.properties
server.port=8081
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/course_practice?serverTimezone=Asia/Shanghai&characterEncoding=utf-8&useSSL=true
spring.datasource.username=root
spring.datasource.password=1234

#日志配置 建议复制
logging.pattern.console=%clr\
  (%d{${LOG_DATEFORMAT_PATTERN:HH:mm:ss.SSS}}){faint} \
  %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- })\
  {magenta} %clr(---){faint} %clr([%15.15t]){faint} \
  %clr(%-40.40logger{39}){cyan} %clr(:){faint} \
  %m%n${LOG_EXCEPTION_CONVERSION_WORD:%wEx}

#把我们用下划线连接的转成驼峰式的 (避免有些实体类中的某些属性与数据表中的字段不匹配,拿不到值)
mybatis.configuration.map-underscore-to-camel-case=true

spring.application.name=course-list
```

```java
//CourseMapper.java  mapper接口
package com.imooc.course.dao;
import com.imooc.course.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程的Mapper类
 */
@Mapper
@Repository
public interface CourseMapper {
    //寻找所有上架的课程
    @Select("select * from course where valid=1")
    List<Course> findValidCourses();}
```

```java
//CourseListService.java 
package com.imooc.course.service;
import com.imooc.course.entity.Course;
import java.util.List;

/**
 * 课程服务实现类
 */
public interface CourseListService {
    List<Course> getCourseList();}
```

```java
//具体实现类
package com.imooc.course.service.Impl;
import com.imooc.course.dao.CourseMapper;
import com.imooc.course.entity.Course;
import com.imooc.course.service.CourseListService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * 课程服务实现类
 */
@Service("courseListService")
public class CourseListServiceImpl implements CourseListService {

    @Resource
    CourseMapper courseMapper;

    @Override
    public List<Course> getCourseList() {
        return courseMapper.findValidCourses();}}
```

```java
package com.imooc.course.controller;
import com.imooc.course.entity.Course;
import com.imooc.course.service.CourseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.List;

/**
 * 课程列表的Controller
 */
@RestController
public class CourseListController {

    @Resource
    CourseListService courseListService;
    //提供一个课程列表的服务

    @GetMapping("/courses")
    public List<Course> courseList() {
        return courseListService.getCourseList();}}
```

#### 常见错误的排查

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221130231853199.png" alt="image-20221130231853199" style="zoom:100%;" />

```html
会发现courseId为null拿不到数据库的数据,为什么?
	因为实体类中的courseId与数据库的字段 course_id对不上。需要在application.properties配置文件中配置
        #把我们用下划线连接的转成驼峰式的
        mybatis.configuration.map-underscore-to-camel-case=true
```

以上完成第一个模块的编写。主要所做的功能是在一个多模块的Maven项目中新建几个子模块并且把这个子模块进行了开发

##### 课程列表模块开发-总结

<img src="C:\Users\w1216\AppData\Roaming\Typora\typora-user-images\image-20221130233140945.png" alt="image-20221130233140945" style="zoom:50%;" />



#### 课程价格模块开发

