package com.imooc.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类
 */
@SpringBootApplication
@MapperScan("com.imooc.course.dao")
public class CourseListApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseListApplication.class,args);

    }
}
