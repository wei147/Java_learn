package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

/**
 * 目录Controller
 */
@Controller
public class CategoryController {
    public ApiRestResponse addCategory(HttpSession session) {
        //1.要先做登录验证, 2.而且是管理员才能进行操作
    }
}
