package com.wei.cloud.mall.practice.categoryproduct.service;

import com.github.pagehelper.PageInfo;
import com.wei.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.wei.cloud.mall.practice.categoryproduct.model.request.AddProductReq;
import com.wei.cloud.mall.practice.categoryproduct.model.request.ProductListReq;

/**
 * 商品Service
 */
public interface ProductService {

    void add(AddProductReq addProductReq);

    void update(Product updateProduct);

    void delete(Integer id);

    void batchUpdateSellStatus(Integer[] ids, Integer sellStatus);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    //像这种复杂查询的时候,我们通常还有一个技巧 就是去构建一个query对象。这个query对象专门用于查询的,
    // 因为往往查询的条件越多,我们一个一个的去拼接这种打散装的结构,他们就会更加的不聚合,所以在后期拓展的时候会显得代码凌乱
    //新建包 com/imooc/mall/model/query
    PageInfo list(ProductListReq productListReq);

    void updateStock(Integer productId, Integer stock);
}
