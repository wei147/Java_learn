package com.imooc.reader.controller;

import com.imooc.reader.entity.Category;
import com.imooc.reader.impl.CategoryServiceImpl;
import com.imooc.reader.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;

    /**
     * 显示首页
     * @return
     */
    @GetMapping("/")
    public ModelAndView showIndex() {
        List<Category> categoryList = categoryService.selectAll();
        ModelAndView mav = new ModelAndView("/index");
        mav.addObject("categoryList", categoryList);
        return mav;
    }
}
