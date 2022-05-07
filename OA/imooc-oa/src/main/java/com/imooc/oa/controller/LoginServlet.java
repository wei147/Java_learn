package com.imooc.oa.controller;

import com.alibaba.fastjson2.JSON;
import com.imooc.oa.entity.User;
import com.imooc.oa.service.UserService;
import com.imooc.oa.service.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//value	String[]	等价于 urlPatterns 属性，与该属性不能同时使用
//urlPatterns	String[]	指定Servlet url的匹配模式，等价于<url-parttern>(应该是可以在xml中的配置)
@WebServlet(name = "LoginServlet", urlPatterns = "/check_login")

public class LoginServlet extends HttpServlet {
    Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //响应中的字符集
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //接收用户输入
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Map<String,Object> result = new HashMap<>();
        try {
            //调用业务逻辑
            User user = userService.checkLogin(username, password);
            result.put("code","0");
            result.put("message","success");
        } catch (BusinessException ex) {
            logger.error(ex.getMessage(), ex);
            result.put("code",ex.getCode());
            result.put("message",ex.getMessage());
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            result.put("code",ex.getClass().getSimpleName());   //类名作为编码？
            result.put("message",ex.getMessage());
        }
        //将map转化为json字符串并返回
        String json = JSON.toJSONString(result);
        //将json字符串打印
        response.getWriter().println(json);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
