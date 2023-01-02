package com.wei.cloud.mall.practice.categoryproduct.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 和商品相关的常量类
 */
@Component
public class ProductConstant {

    public static String FILE_UPLOAD_DIR;

    //用set方法去把静态变量进行赋值,这样可以成功获取到在配置文件中定义的路径的值 application.properties
    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }
}
