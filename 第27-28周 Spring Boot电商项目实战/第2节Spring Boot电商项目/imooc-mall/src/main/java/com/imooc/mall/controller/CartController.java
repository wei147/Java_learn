package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 购物车Controller
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    //添加商品到购物车
    @PostMapping("/add")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count){
        //需要用户登录以后才能添加到购物车
        return null;
    }
}
