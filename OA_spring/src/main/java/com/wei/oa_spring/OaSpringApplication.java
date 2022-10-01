package com.wei.oa_spring;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//需要在这里也加上注解才能找到mapper文件
@MapperScan(basePackages = "com.wei.oa_spring.model.dao")
public class OaSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(OaSpringApplication.class, args);
    }

}
