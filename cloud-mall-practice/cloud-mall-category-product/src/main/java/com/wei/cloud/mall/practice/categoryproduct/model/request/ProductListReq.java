package com.wei.cloud.mall.practice.categoryproduct.model.request;

/**
 * 新增商品的一个请求类
 */
public class ProductListReq {

    private String keyword;

    private Integer categoryId; //有时候需要获取某一个目录下的所有商品

    private String orderBy; //排序

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "ProductListReq{" +
                "keyword='" + keyword + '\'' +
                ", categoryId=" + categoryId +
                ", orderBy='" + orderBy + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}