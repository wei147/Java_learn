package com.imooc.oa.service;

import com.imooc.oa.entity.Node;
import com.imooc.oa.entity.User;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService userService = new UserService();

    @Test
    public void checkLogin() {
        userService.checkLogin("w9", "1234");
    }

    @Test
    public void checkLogin1() {
        userService.checkLogin("m8", "1234");
    }

    @Test
    public void checkLogin2() {
        User user = userService.checkLogin("m8", "test");
        //这里打印的user对象，即是user表。里边有字段具体值
        System.out.println(user);

    }

    @Test
    public void selectNodeByUserId() {
        List<Node> nodeList = userService.selectNodeByUserId(2L);
        System.out.println(111);
//        String text = nodeList.get(0).getNodeName();
//        System.out.println(text);
    }
}