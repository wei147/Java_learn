package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.filter.UserFilter;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.model.vo.CartVO;
import com.imooc.mall.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 购物车Controller
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    CartService cartService;

    //返回购物车列表
    @GetMapping("/list")
    @ApiOperation("购物车列表")
    public ApiRestResponse list() {
        //内部获取用户ID,防止横向越权 (横向越权是指 我是一个用户,别人也是一个用户,我去操作别人的,这就叫横向越权)
        //(纵向越权是指 我是一个普通用户,我想处理比我高的、管理员的一些内容)
        List<CartVO> cartVOList = cartService.list(UserFilter.currentUser.getId());
        return ApiRestResponse.success(cartVOList);
    }

    //添加商品到购物车
    @PostMapping("/add")
    @ApiOperation("添加商品到购物车")
    public ApiRestResponse add(@RequestParam Integer productId, @RequestParam Integer count) {
        //需要用户登录以后才能添加到购物车 已经有UserFilter去做用户登录校验了
        Integer userId = UserFilter.currentUser.getId();
        List<CartVO> cartVOList = cartService.add(userId, productId, count);
        return ApiRestResponse.success(cartVOList);
    }


    @PostMapping("/update")
    @ApiOperation("更新购物车列表")
    public ApiRestResponse update(@RequestParam Integer productId, @RequestParam Integer count) {
        Integer userId = UserFilter.currentUser.getId();
        List<CartVO> cartVOList = cartService.update(userId, productId, count);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/delete")
    @ApiOperation("删除购物车")
    public ApiRestResponse delete(@RequestParam Integer productId) {
        //不能传入userID,cartID,否则可以删除别人的购物车
        Integer userId = UserFilter.currentUser.getId();
        List<CartVO> cartVOList = cartService.delete(userId, productId);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/select")
    @ApiOperation("选择/不选择购物车的某商品")
    public ApiRestResponse select(@RequestParam Integer productId, @RequestParam Integer selected) {
        Integer userId = UserFilter.currentUser.getId();
        List<CartVO> cartVOList = cartService.selectOrNot(userId, productId, selected);
        return ApiRestResponse.success(cartVOList);
    }

    @PostMapping("/selectAll")
    @ApiOperation("全选择/全不选择购物车的某商品")
    public ApiRestResponse selectAllOrNot(@RequestParam Integer selected) {
        Integer userId = UserFilter.currentUser.getId();
        List<CartVO> cartVOList = cartService.selectAll(userId, selected);
        return ApiRestResponse.success(cartVOList);
    }
}
