package com.imooc.oa.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * 页面跳转Servlet
 */
@WebServlet(name = "ForwardServlet", urlPatterns = "/forward/*")
public class ForwardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();   //获取uri的地址
        System.out.println("uri是 ---=-"+uri);
        /**
         * /forward/form
         * /forward/a/b/c/form
         */
        String subUri = uri.substring(1);    //代表从第二个字符之后开始截取   forward/form
        String page = subUri.substring(subUri.indexOf("/")); // a/b/c/form
        System.out.println("page 是-----====="+ page);
        request.getRequestDispatcher(page + ".ftl").forward(request, response); //这个不理解


    }

}
