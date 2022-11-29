package com.wei.oa_spring.service;

import com.wei.oa_spring.model.pojo.Node;
import com.wei.oa_spring.model.pojo.User;

import java.util.List;

public interface UserService {

    User selectById();

    User login(String username,String password);

}
