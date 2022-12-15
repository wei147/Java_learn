package com.wei.cloud.mall.practice.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * UserApplication 启动类
 */

//指定MyBatis搜索的路径
@MapperScan(basePackages = "com.wei.cloud.mall.practice.user.model.dao")
@EnableSwagger2
@SpringBootApplication
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
