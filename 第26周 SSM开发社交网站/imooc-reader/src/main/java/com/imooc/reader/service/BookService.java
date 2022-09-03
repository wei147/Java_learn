package com.imooc.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;

/**
 * 图书服务
 * IPage是由MyBatis-Plus提供分页对象。这个分页对象中不仅包含了当前查询出来的页数据;也包含了一系列的的与分页相关的信息。详情ctrl进去(好像没有中文注释了?哈哈)
 * 生成对应实现类的小技巧 : 选中BookService类名,在上面按 alt + 回车
 */
public interface BookService {

    /**
     * 分页查询图书
     *
     * @param categoryId 分页编号
     * @param order    排序方式
     * @param page     页号
     * @param rows     每页记录数
     * @return 分页对象
     */
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer rows);
}
