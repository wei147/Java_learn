package com.imooc.spring.aop.dao;

/**
 * 用户表Dao
 */
public class UserDao {
    public void insert(){
        if (1==1){
            throw new RuntimeException("用户已存在");
        }
        System.out.println("新增用户数据");
    }
}
