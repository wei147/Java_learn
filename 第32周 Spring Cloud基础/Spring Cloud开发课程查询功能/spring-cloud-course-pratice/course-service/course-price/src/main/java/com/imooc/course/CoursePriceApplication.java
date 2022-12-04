package com.imooc.course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 项目启动类
 */
@SpringBootApplication
@EnableFeignClients
//断路器的注解
@EnableCircuitBreaker
@MapperScan("com.imooc.course.dao")
public class CoursePriceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoursePriceApplication.class,args);

    }
}
