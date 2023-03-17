package com.imooc.mall.model.query;

import java.util.List;

/**
 *
 * 查询商品列表的Query
 */
public class ProductListQuery {

    private String keyword;

    //目录信息往往是一个列表
    /**
     * [对于查询目录的in处理]
     * 目录处理：如果查某个目录下的商品，不仅是需要查出来该目
     * 录的,还需要查出来子目录的所有商品
     * 所以这里要拿到某一个目录Id下的所有子目录id的List
     *
     * 即最开始传入一个id(从ProductListReq),到我们去查询的时候已经拼装成一个list(经ProductListQuery查询)了
     */
    private List<Integer> categoryIds;


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
