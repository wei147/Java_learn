package com.imooc.oa.controller;

import com.imooc.oa.entity.Node;
import com.imooc.oa.entity.User;
import com.imooc.oa.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet", urlPatterns = "/index")
public class IndexServlet extends HttpServlet {
    private UserService userService = new UserService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //IndexServlet 怎么拿到LoginServlet 登录验证通过的user信息呢？ 通过session
        HttpSession session = request.getSession();
        //得到当前登录用户对象
        User user = (User)session.getAttribute("login_user");
        //获取登录用户可用功能模块列表
        List<Node> nodeList = userService.selectNodeByUserId(user.getUserId());
        //将当前的结点列表放入请求中 （放入请求属性）
        request.setAttribute("node_list", nodeList);
        //最后利用请求转发  （请求派发至ftl进行展示）
        request.getRequestDispatcher("/index.ftl").forward(request, response);

        //shift + f9 打开调试，选择restart server  重新部署发布
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
