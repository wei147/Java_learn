package com.imooc.oa.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * #### 实现注销操作
 * 把登录的信息给予清除
 */
//  urlPatterns = "/Logout"
@WebServlet(name = "LogoutServlet", value = "/Logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //两种清除session的方式    removeAttribute要传入session名参数 login_user。    invalidate更常用
        //request.getSession().removeAttribute();
        request.getSession().invalidate();
        //重定向跳转到登录页面
        response.sendRedirect("/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
