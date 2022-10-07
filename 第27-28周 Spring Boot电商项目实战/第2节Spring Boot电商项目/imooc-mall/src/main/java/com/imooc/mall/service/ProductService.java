package com.imooc.mall.service;

import com.github.pagehelper.PageInfo;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.pojo.Product;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.request.AddProductReq;
import com.imooc.mall.model.request.ProductListReq;
import com.imooc.mall.model.vo.CategoryVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
}
