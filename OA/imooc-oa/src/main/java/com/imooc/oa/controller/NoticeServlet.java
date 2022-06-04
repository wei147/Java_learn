package com.imooc.oa.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.oa.entity.Notice;
import com.imooc.oa.entity.User;
import com.imooc.oa.service.NoticeService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "NoticeServlet", urlPatterns = "/notice/list")
public class NoticeServlet extends HttpServlet {

    //创建Service 对象
    private NoticeService noticeService = new NoticeService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //完成数据查询的工作
        User user = (User) request.getSession().getAttribute("login_user");    //从当前会话中，将登录用户的信息提取出来
        List<Notice> noticeList = noticeService.getNoticeList(user.getEmployeeId()); //这里就可以查到这个员工最近要查看的一百条信息，返回的是一个集合
        Map result = new HashMap<>();
        result.put("code", "0");
        result.put("msg", "");
        result.put("count", noticeList.size());
        result.put("data", noticeList);
        String json = JSON.toJSONString(result);   //转为json字符串
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(json);  //通过响应向客户端返回，进行输出
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
