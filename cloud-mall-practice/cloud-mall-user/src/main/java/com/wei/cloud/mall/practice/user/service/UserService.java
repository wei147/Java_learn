package com.wei.cloud.mall.practice.user.service;

import com.wei.cloud.mall.practice.user.model.pojo.User;
import com.wei.mall.practice.common.exception.ImoocMallException;

public interface UserService {
    User getUser();

    void register(String username,String password) throws ImoocMallException;

    User login(String username, String password) throws ImoocMallException;

    void updateUserInformation(User user) throws ImoocMallException;

    boolean checkAdminRole(User user);
}
