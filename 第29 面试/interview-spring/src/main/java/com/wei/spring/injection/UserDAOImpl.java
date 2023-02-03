package com.wei.spring.injection;

public class UserDAOImpl implements UserDAO {
    public UserDAOImpl() {
        System.out.println(this + ":已创建");
    }

    @Override
    public void insert() {
        System.out.println(this + ":正在调用UserDAOImpl()方法");
    }
}
