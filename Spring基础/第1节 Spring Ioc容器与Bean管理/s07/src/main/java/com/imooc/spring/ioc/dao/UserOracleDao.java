package com.imooc.spring.ioc.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary    //如果在ioc容器中出现多个相同类型的对象的话，那么默认采用 @Primary 所描述的对象   （优先注入，解决类型冲突的问题）
public class UserOracleDao implements IUserDao{
    public UserOracleDao() {
        System.out.println("正在创建UserOracleDao: "+this);
    }
}
