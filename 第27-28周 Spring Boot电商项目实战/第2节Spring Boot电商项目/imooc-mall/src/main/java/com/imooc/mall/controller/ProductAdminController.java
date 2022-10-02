package com.imooc.mall.controller;

import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 后台商品管理Controller
 */
@Controller
public class ProductAdminController {

    @Resource
    ProductService productService;

    @PostMapping("admin/product/add")
    @ResponseBody
    //addProduct也是需要我们自己去定义一个request的实体类,因为我们会对它做校验
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductReq addProductReq) {

        productService.add(addProductReq);
        //有一个参数拿不到————图片
        return ApiRestResponse.success();
    }

    //这里需要传入两个参数: 1.为了在图片地址中保存我们的地址,比如说url、ip等,  2.文件类型。而且这个file要加上注解方便它识别
    @PostMapping("admin/upload/file")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file){

    }
}
