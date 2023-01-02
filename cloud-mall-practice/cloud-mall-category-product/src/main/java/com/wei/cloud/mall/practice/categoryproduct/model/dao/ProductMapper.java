package com.wei.cloud.mall.practice.categoryproduct.model.dao;

import com.wei.cloud.mall.practice.categoryproduct.model.pojo.Product;
import com.wei.cloud.mall.practice.categoryproduct.model.query.ProductListQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    Product selectByName(String name);

    int batchUpdateSellStatus(@Param("ids") Integer[] ids, @Param("sellStatus") Integer sellStatus);

    List<Product> selectListForAdmin();

    //给用户看的
    List<Product> selectList(@Param("query") ProductListQuery query);
}