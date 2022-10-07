package com.imooc.mall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//需要在这里也加上注解才能找到mapper文件
@MapperScan(basePackages = "com.imooc.mall.model.dao")
@EnableSwagger2 //(自动生成Api文档)
@EnableCaching //使用Spring提供的cache功能(redis缓存)
public class ImoocMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImoocMallApplication.class, args);
    }

}
