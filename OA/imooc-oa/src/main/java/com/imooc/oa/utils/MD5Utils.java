package com.imooc.oa.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Utils {
    public static String md5Digest(String source) {
        return DigestUtils.md5Hex(source);
    }

    /**
     * 对源数据加盐混淆后生成MD5摘要
     *
     * @param source 源数据
     * @param salt   盐值
     * @return Md5摘要
     */
    public static String md5Digest(String source, Integer salt) {
        char[] ca = source.toCharArray();
        for (int i = 0; i < ca.length; i++) {
            ca[i] = (char) (ca[i] + salt);
        }
        String target = new String(ca);
//        System.out.println(target);
        String md5 = DigestUtils.md5Hex(target);
        return md5;
    }

    public static void main(String[] args) {
//        MD5Utils.md5Digest("test",1);   // 打印了 uftu，即 test的阿斯克码的基础上加1。t+1 = u
//        MD5Utils.md5Digest("wei",1);
        System.out.println(md5Digest("test", 188));
        System.out.println("==========");
        for (int i = 188;i<198;i++){
            System.out.println(i+"的盐值是 ："+md5Digest("test",i));
        }
    }
}
