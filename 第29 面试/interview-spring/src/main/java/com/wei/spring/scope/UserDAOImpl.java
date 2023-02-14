package com.wei.spring.scope;

//@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
    //默认构造函数 在运行时会打印
    public UserDAOImpl() {
        System.out.println(this + ":已创建");
    }

    @Override
    public void insert() {
        System.out.println(this + ":正在调用UserDAOImpl.insert()方法");
    }
}
