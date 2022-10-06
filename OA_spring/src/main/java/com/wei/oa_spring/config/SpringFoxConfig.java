package com.imooc.mall.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

    //访问http://localhost:8083/swagger-ui.html可以看到API文档
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
//        return new Docket(DocumentationType.OAS_30)// // 注意此处改动，需要将SWAGGER_2改成OAS_30
                .apiInfo(apiInfo())
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("慕慕生鲜")
                .description("")
                .termsOfServiceUrl("")
                .build();
    }
}