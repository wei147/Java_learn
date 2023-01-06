package com.wei.cloud.mall.practice.categoryproduct.controller;

import com.github.pagehelper.PageInfo;
import com.wei.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.wei.cloud.mall.practice.categoryproduct.model.request.ProductListReq;
import com.wei.cloud.mall.practice.categoryproduct.service.ProductService;
import com.wei.mall.practice.common.common.ApiRestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 前台商品Controller
 */
@RestController
@CrossOrigin(origins = "*") //2022年10月27日01:24:54不知道为啥登录接口有跨域问题,加上这个配置试一下
public class ProductController {
    @Resource
    ProductService productService;

    @ApiOperation(value = "前台商品详情")
    @GetMapping("product/detail")
    public ApiRestResponse detail(@RequestParam Integer id) {
        Product product = productService.detail(id);
        return ApiRestResponse.success(product);
    }

    @ApiOperation(value = "前台商品列表")
    @GetMapping("product/list")
    public ApiRestResponse list(ProductListReq productListReq) {
        PageInfo list = productService.list(productListReq);
        return ApiRestResponse.success(list);
    }

    // 为购物车模块提供商品详情服务。 内部使用
    @ApiOperation(value = "前台商品详情")
    @GetMapping("product/detailForFeign")
    public Product detailForFeign(@RequestParam Integer id) {
        Product product = productService.detail(id);
        return product;
    }
}
