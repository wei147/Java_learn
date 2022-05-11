package com.imooc.oa.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class MD5UtilsTest {

    @Test
    public void md5Digest() {
        System.out.println(MD5Utils.md5Digest("test8we*"));
        //098f6bcd4621d373cade4e832627b4f6 原数据test 还是有可能被破解的    https://www.cmd5.com/ 能被还原出来
        //78818cce49a499a6f7e561c11c9773b1 源数据 test8we* 像这个就破解不了
    }
}