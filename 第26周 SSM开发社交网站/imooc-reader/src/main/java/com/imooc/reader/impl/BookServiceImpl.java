package com.imooc.reader.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)    //目前里边的每一个方法默认不开启事务
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;

    /**
     * 分页查询图书
     *
     * @param categoryId 分页编号
     * @param order      排序方式
     * @param page       页号
     * @param rows       每页记录数
     * @return 分页对象
     */
    @Override
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer rows) {
        Page<Book> p = new Page<Book>(page, rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
        //这个判断代表了从前台传入了有效的分页编号
        if (categoryId != null && categoryId != -1) {
            queryWrapper.eq("category_id", categoryId);
        }
        if (order != null) {
            //排序的规则有两种
            //按照评价人数来进行排序
            if (order.equals("quantity")) {
                queryWrapper.orderByDesc("evaluation_quantity");    //按照指定字段进行降序排列
                //按照具体的评分进行降序排列
            } else if (order.equals("score")) {
                queryWrapper.orderByDesc("evaluation_score");
            }
        }
        //还没有任何需要筛选的条件,所以直接将queryWrapper对象放入到第二个参数。相当于对原始的所有数据进行分页查询了
        IPage<Book> pageObject = bookMapper.selectPage(p, queryWrapper);

        return pageObject;
    }
}
