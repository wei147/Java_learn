package com.wei.oa_spring.utils;

import com.wei.oa_spring.common.Constant;
import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具
 * <p>
 * 说明:其实MD5它是一种哈希算法,它会把一种字符串通过哈希的方式转换为另外一个难以辨认的字符串,
 * 如果我们把哪个字符串给存下来,它是无法通过生成后的字符串去反推之前的原始字符串的。它本质是一种哈希运算,
 * 那么由于它无法反推,所以它不具备解密的能力,这就意味着实际上它并不是一种严格意义上的加密算法,
 * 严格意义上它是一种哈希算法,所以为了更严谨这里叫做MD5工具,
 */
public class MD5Utils {
    public static String getMd5Str(String strValue) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        //加上较为复杂的盐值 https://www.cmd5.com/ 这个网站也无法破解 (穷举法)
        return Base64.encodeBase64String(md5.digest((strValue + Constant.SALT).getBytes()));
    }

    //用这个方法测试生成的MD5的值
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String str = getMd5Str("test");
        System.out.println(str);
    }
}
