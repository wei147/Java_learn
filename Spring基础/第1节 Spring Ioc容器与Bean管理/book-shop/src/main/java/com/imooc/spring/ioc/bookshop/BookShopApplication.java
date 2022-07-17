package com.imooc.spring.ioc.bookshop;

import com.imooc.spring.ioc.bookshop.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BookShopApplication {
    public static void main(String[] args) {
        String[] configLocations = new String[]{
                "classpath:ApplicationContext-dao.xml", "classpath:ApplicationContext-service.xml"
        };
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:ApplicationContext-*.xml");
        //service调用Dao的过程
        BookService bookService = context.getBean("bookService", BookService.class);
        bookService.purchase();
    }
}
