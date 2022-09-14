package com.imooc.reader.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)//junit4在运行时，会自动初始化ioc容器
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) //说明配置文件在什么地方
public class Test {
//    @org.junit.Test
//    public void test01() {
//        int nums = new int[]{1, 2, 3};
//        for (int i = 0; i < 5; i++) {
//            nums = nums[i]+i;
//        }
//        System.out.println(x);
//    }

}

