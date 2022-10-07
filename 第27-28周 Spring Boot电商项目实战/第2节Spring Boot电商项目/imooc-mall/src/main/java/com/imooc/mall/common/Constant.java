package com.imooc.mall.common;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 常量值
 * <p>
 * 放常量值的地方
 */
@Component //想让Spring 帮我们去注入这个Value,那还需要在这上面加上@Component注解
public class Constant {
    //大写变成小写 edit下面的 toggle case
    public static final String IMOOC_MALL_USER = "imooc_mall_user";
    public static final String SALT = "yidou_8&.3@";    //用MD5的盐值

    //上传文件的地址
    //这样写的 FILE_UPLOAD_DIR并没有取到  why? 这个变量和普通变量不一样,它是静态的带String,对于这种变量用普通的方式进行处理是注入不进去的,解决方案如下: 给它写一个方法
//    @Value("${file.upload.dir}")
//    public static String FILE_UPLOAD_DIR;

    public static String FILE_UPLOAD_DIR;

    //用set方法去把静态变量进行赋值,这样可以成功获取到在配置文件中定义的路径的值 application.properties
    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir){
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface  productListOrderBy{
        Set<String> PRICE_ASC_DESC= Sets.newHashSet("price desc","price asc");
    }
}
