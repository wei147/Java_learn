package com.imooc.oa.test;

import com.imooc.oa.utils.MybatisUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "TestServlet", urlPatterns = "/test")
public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * 映射地址是/test (urlPatterns = "/test"),最后会将请求转发至 "/test.ftl" ,
         * 再由模板引擎进行渲染输出（test.ftl里的${result}）
         */
        String result = (String) MybatisUtils.executeQuery(sqlSession -> sqlSession.selectOne("test.sample"));
//        String result = "wei";
        System.out.println(result);
        request.setAttribute("result", result);
        request.getRequestDispatcher("/test.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
