package com.wei.qrcode.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {

    public static String FILE_UPLOAD_DIR;

    //用set方法去把静态变量进行赋值,这样可以成功获取到在配置文件中定义的路径的值 application.properties
    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }
}
