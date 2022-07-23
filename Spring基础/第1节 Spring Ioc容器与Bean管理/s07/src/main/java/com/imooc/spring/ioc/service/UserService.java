package com.imooc.spring.ioc.service;

import com.imooc.spring.ioc.dao.IUserDao;
import com.imooc.spring.ioc.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//用户的业务逻辑类: 提供了与用户操作的核心代码
@Service
public class UserService {
    //装配注解放在不同位置上有根本不同
    // @Autowired
    //spring Ioc容器会自动通过反射技术将属性private修饰符自动改为public，直接进行赋值（在运行时动态完成）
    //如果放在属性上，不再执行set方法
    @Autowired
    private IUserDao udao;

    public UserService() {
        System.out.println("正在创建UserService: " + this);
    }

    public IUserDao getUdao() {
        return udao;
    }

    //通常不会生成set方法，而是会直接在属性上增加对应的装配注解
//    @Autowired
//    //如果装配注解放在set方法上，则自动按类型/名称对set方法参数进行注入
//    public void setUdao(UserDao udao) {
//        System.out.println("setUdao" + udao);
//        this.udao = udao;
//    }
}
