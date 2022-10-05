package com.imooc.mall.controller;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.common.ApiRestResponse;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.UpdateProductReq;
import com.imooc.mall.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * 后台商品管理Controller
 */
@RestController
public class ProductAdminController {

    @Resource
    ProductService productService;

    @PostMapping("admin/product/add")
    //addProduct也是需要我们自己去定义一个request的实体类,因为我们会对它做校验
    public ApiRestResponse addProduct(@Valid @RequestBody AddProductReq addProductReq) {

        productService.add(addProductReq);
        //有一个参数拿不到————图片
        return ApiRestResponse.success();
    }

    //这里需要传入两个参数: 1.为了在图片地址中保存我们的地址,比如说url、ip等,  2.文件类型。而且这个file要加上注解方便它识别
    @PostMapping("admin/upload/file")
    public ApiRestResponse upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) {
        //给图片生成uuid的名字,但后缀是不变的。获取后缀名
        String fileName = file.getOriginalFilename(); //获取原始图片名字
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称UUID
        UUID uuid = UUID.randomUUID();
        String newFileName = uuid.toString() + suffix;
        //创建文件
        File fileDirectory = new File(Constant.FILE_UPLOAD_DIR);//文佳夹
        File destFile = new File(Constant.FILE_UPLOAD_DIR + newFileName);//目标文件
//判断文件夹是否存在
        if (!fileDirectory.exists()) {
            //如果创建失败
            if (!fileDirectory.mkdir()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.MKDIR_FAILED);
            }
        }
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //getRequestURL() 得到的是一个StringBuffer,而new URI()方法需要传入一个String类型。所以加上""就可以转为String
            return ApiRestResponse.success(getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/images/" + newFileName);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ApiRestResponse.error(ImoocMallExceptionEnum.UPLOAD_FAILED);
    }

    //用于获取ip和端口号
    private URI getHost(URI uri) {
        URI effectiveURI;
        try {
            effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(),
                    null, null, null);
        } catch (URISyntaxException e) {
            //如果新建的过程中失败了,那么就把它设置为null
            effectiveURI = null;
        }
        //获得我们想要的那部分的信息的uri,而把多余的信息剔除掉
        return effectiveURI;
    }

    @ApiOperation(value = "后台更新商品")
    @PostMapping("admin/product/update")
    public ApiRestResponse updateProduct(@Valid @RequestBody UpdateProductReq updateProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(updateProductReq, product);
        productService.update(product);
        return ApiRestResponse.success();
    }

    @ApiOperation(value = "后台删除商品")
    @PostMapping("admin/product/delete")
    public ApiRestResponse deleteProduct(@RequestParam Integer id) {
        productService.delete(id);
        return ApiRestResponse.success();
    }

    @ApiOperation(value = "后台批量上下架接口")
    @PostMapping("admin/product/batchUpdateSellStatus") //批量更新销售状态
    public ApiRestResponse batchUpdateSellStatus(@RequestParam Integer[] ids, @RequestParam Integer sellStatus) { //sellStatus决定上架还是下架
        productService.batchUpdateSellStatus(ids, sellStatus);
        return ApiRestResponse.success();
    }

    @ApiOperation(value = "后台商品列表接口")
    @PostMapping("admin/product/list") //批量更新销售状态
    public ApiRestResponse list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo pageInfo = productService.listForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }
}
