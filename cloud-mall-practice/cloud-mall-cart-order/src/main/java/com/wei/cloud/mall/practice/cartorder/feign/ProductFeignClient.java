package com.wei.cloud.mall.practice.cartorder.feign;

import com.wei.cloud.mall.practice.categoryproduct.model.pojo.Product;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品的FeignClient
 */
//这里的value指定的对应模块是哪一个
@FeignClient(value = "cloud-mall-category-product")
public interface ProductFeignClient {
    //Feign的调用是内部的,不经过网关。
    //它是直接前请求到Eureka Server,然后拿到地址之后去访问的。(即便不经过网关,它都是可以正常运行的)
    @GetMapping("product/detailForFeign")
    Product detailForFeign(@RequestParam Integer id);

    // 为购物车模块提供更新库存服务。 内部使用
    @PostMapping("product/updateStock")
    void updateStock(@RequestParam Integer productId, @RequestParam Integer stock);
}
