package com.imooc.oa.controller;

import com.imooc.oa.entity.Department;
import com.imooc.oa.entity.Employee;
import com.imooc.oa.entity.Node;
import com.imooc.oa.entity.User;
import com.imooc.oa.service.DepartmentService;
import com.imooc.oa.service.EmployeeService;
import com.imooc.oa.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet", urlPatterns = "/index")
public class IndexServlet extends HttpServlet {
    private UserService userService = new UserService();
    private EmployeeService employeeService = new EmployeeService();
    private DepartmentService departmentService = new DepartmentService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //IndexServlet 怎么拿到LoginServlet 登录验证通过的user信息呢？ 通过session
        HttpSession session = request.getSession();
        //得到当前登录用户对象
        User user = (User) session.getAttribute("login_user");
        //获取当前登录的员工对象
        Employee employee = employeeService.selectById(user.getEmployeeId());
        Department department = departmentService.selectById(employee.getDepartmentId());
        //获取登录用户可用功能模块列表
        List<Node> nodeList = userService.selectNodeByUserId(user.getUserId());
        //将当前的结点列表放入请求中 （放入请求属性）
        request.setAttribute("node_list", nodeList);
        //如果employee数据只是为index.ftl服务的话，
        // 放入当前的请求中request.setAttribute 是没问题的。
        // 但在当前项目中需要反复用到当前登录的员工是哪一位，所以将他的存储范围放大，放入到了session中
        session.setAttribute("current_employee", employee);
        session.setAttribute("current_department",department);
        //最后利用请求转发  （请求派发至ftl进行展示）
        request.getRequestDispatcher("/index.ftl").forward(request, response);

        //shift + f9 打开调试，选择restart server  重新部署发布
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
