package com.wei.oa_spring.service.Impl;

import com.wei.oa_spring.model.dao.UserMapper;
import com.wei.oa_spring.model.pojo.User;
import com.wei.oa_spring.service.UserService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@WebMvcTest(UserServiceImpl.class)
public class UserServiceImplTest extends TestCase {

    @Resource
    UserService userService;
    @Resource
    UserMapper userMapper;

    @Test
    public void testSelectById() {
        System.out.println(" i am come ===============");
        User user = userMapper.selectByPrimaryKey(1L);
        System.out.println(user);
    }

    @Test
    public void testLogin() {
        System.out.println("qqqq++++++++++++++++++=====");
    }

    public void testSelectNodeByUserId() {
    }
}