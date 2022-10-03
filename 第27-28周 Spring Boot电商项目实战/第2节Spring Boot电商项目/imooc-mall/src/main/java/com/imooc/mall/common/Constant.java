package com.imooc.mall.common;

import org.springframework.beans.factory.annotation.Value;

/**
 * 常量值
 * <p>
 * 放常量值的地方
 */
public class Constant {
    //大写变成小写 edit下面的 toggle case
    public static final String IMOOC_MALL_USER = "imooc_mall_user";
    public static final String SALT = "yidou_8&.3@";    //用MD5的盐值

    //上传文件的地址
    @Value("${file.upload.dir}")
    public static String FILE_UPLOAD_DIR;
}
