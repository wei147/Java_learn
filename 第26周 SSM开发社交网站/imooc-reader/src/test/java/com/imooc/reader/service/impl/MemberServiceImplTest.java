package com.imooc.reader.service.impl;

import com.imooc.reader.entity.MemberReadState;
import com.imooc.reader.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)//junit4在运行时，会自动初始化ioc容器
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) //说明配置文件在什么地方
public class MemberServiceImplTest {
    @Resource
    private MemberService memberService;

    @Test
    public void createMember() {
        memberService.createMember("wei","123456","友人A");
    }

    @Test
    public void updateMemberReadState() {
        MemberReadState memberReadState = memberService.selectMemberReadState(1L, 5L);
        System.out.println("2222"+memberReadState);
        System.out.println("1111"+memberReadState.getReadState());
//        memberService.updateMemberReadState(1L,5L,1);
    }
}