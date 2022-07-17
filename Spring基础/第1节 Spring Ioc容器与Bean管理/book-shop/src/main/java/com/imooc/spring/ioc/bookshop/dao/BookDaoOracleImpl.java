package com.imooc.spring.ioc.bookshop.dao;

public class BookDaoOracleImpl implements BookDao{

    @Override
    public void insert() {
        System.out.println("向oracle book表插入一条数据");
    }
}
