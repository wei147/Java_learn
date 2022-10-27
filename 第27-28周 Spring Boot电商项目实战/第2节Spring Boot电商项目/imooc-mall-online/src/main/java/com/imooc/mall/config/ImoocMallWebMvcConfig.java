package com.imooc.mall.config;

import com.imooc.mall.common.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

        //如果是admin开头的文件会被路由到 resource下的/static/admin/下面,,
        registry.addResourceHandler("/admin/**")
                .addResourceLocations("classpath:/static/admin/");

        //关于上传图片的自定义静态资源映射目录  (就是说http://localhost/images/uuid.png 对应的就是本地 FILE_UPLOAD_DIR的文件路径)
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + Constant.FILE_UPLOAD_DIR);

        //以下都是swagger的静态资源映射? （自动生成api文档）
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/static/");
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);

    }
}
