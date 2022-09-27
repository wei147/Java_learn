package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.request.AddCategoryReq;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

/**
 * 目录Controller
 */
@Controller
public class CategoryController {
    public ApiRestResponse addCategory(HttpSession session, AddCategoryReq addCategoryReq) {
        //1.要先做登录验证, 2.而且是管理员才能进行操作
        if (addCategoryReq.getName()==null){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NAME_NOT_NULL);
        }
    }
}
