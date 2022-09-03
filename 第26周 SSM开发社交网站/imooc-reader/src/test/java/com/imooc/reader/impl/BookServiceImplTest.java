package com.imooc.reader.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)//junit4在运行时，会自动初始化ioc容器
@ContextConfiguration(locations = {"classpath:applicationContext.xml"}) //说明配置文件在什么地方
public class BookServiceImplTest {

    @Resource
    private BookService bookService;

    @Test
    public void paging() {
        //查询第1页的数据每页10条 1-10    (如果是page:2,rows:10,查询到的是 11-20)
        //查询的步骤: 1.先是获取没有分页的时候数据总数是多少,只有数据总数才能计算出一共有多少页 [SELECT COUNT(1) FROM book ]
        // 2.总数再除以每页的记录数就能计算出本次查询一共有多少页 [SELECT book_id,book_name,...,evaluation_quantity FROM book LIMIT ?,? ]
        IPage<Book> pageObject = bookService.paging(2L,"quantity",2, 10);
        List<Book> records = pageObject.getRecords();
        System.out.println("=================================");
        for (Book b : records) {
            System.out.println(b.getBookId() + " : " + b.getBookName());
        }
        //获取总页数
        System.out.println("总页数 : " + pageObject.getPages());      //5
        //获取总记录数
        System.out.println("总记录数 : " + pageObject.getTotal());    //44

    }
}