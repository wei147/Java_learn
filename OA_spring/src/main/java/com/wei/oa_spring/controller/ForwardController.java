package com.wei.oa_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 页面跳转controller
 */

@Controller
public class ForwardController {

    @GetMapping("/forward/{page}")
    public ModelAndView doGet(@PathVariable String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("路径变量是: " + page);
        return new ModelAndView("/" + page);
    }
}
