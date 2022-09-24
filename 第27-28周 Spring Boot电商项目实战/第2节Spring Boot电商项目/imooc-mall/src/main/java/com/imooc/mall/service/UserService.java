package com.imooc.mall.service;

import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.model.pojo.User;

public interface UserService {
    User getUser();

    void register(String username,String password) throws ImoocMallException;

    User login(String username, String password) throws ImoocMallException;

    void updateUserInformation(User user) throws ImoocMallException;
}
