package com.imooc.spring.ioc.bookshop.dao;

//实现类 实现bookDao
public class BookDaoImpl implements BookDao{
    @Override
    public void insert() {
        System.out.println("向MySql Book表插入一条数据");
    }
}
