package com.imooc.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Category;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;

    /**
     * 显示首页
     *
     * @return
     */
    @GetMapping("/")
    public ModelAndView showIndex() {
        List<Category> categoryList = categoryService.selectAll();
        ModelAndView mav = new ModelAndView("/index");
        mav.addObject("categoryList", categoryList);
        return mav;
    }

    /**
     * 分页查询图书列表
     *
     * @param categoryId 分页编号
     * @param order      排序方式
     * @param p          页号(从前台传过来的)
     * @return分页对象
     */
    @GetMapping("/books")
    @ResponseBody
    public IPage<Book> selectBook(Long categoryId, String order, Integer p) {
        if (p == null) {
            p = 1;
        }
        IPage<Book> pageObject = bookService.paging(categoryId, order, p, 10);
        //自动的会Json序列化输出
        return pageObject;
    }

    /**
     * 使用id这个路径变量获取存放在url中的图书编号
     * (show开头即显示页面)
     */
    @GetMapping("/book/{id}")
    //这个id从哪来呢? 从前面的路径变量。所以在参数部分增加注解@PathVariable("id"),这个("id")要和路径变量里的名字一致
    public ModelAndView showDetail(@PathVariable("id") Long id){
        Book book = bookService.selectById(id);
        ModelAndView mav = new ModelAndView("/detail");
        mav.addObject("book",book);
        return mav;
    }
}
