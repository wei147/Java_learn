package com.wei.oa_spring.controller;

import com.wei.oa_spring.common.Constant;
import com.wei.oa_spring.model.pojo.Department;
import com.wei.oa_spring.model.pojo.Employee;
import com.wei.oa_spring.model.pojo.Node;
import com.wei.oa_spring.model.pojo.User;
import com.wei.oa_spring.service.DepartmentService;
import com.wei.oa_spring.service.EmployeeService;
import com.wei.oa_spring.service.NodeService;
import com.wei.oa_spring.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 首页
 */
@Controller
public class IndexController {

    @Resource
    EmployeeService employeeService;

    @Resource
    DepartmentService departmentService;

    @Resource
    UserService userService;

    @Resource
    NodeService nodeService;

    //登录页面
    @GetMapping("/index")
    public ModelAndView index(HttpServletRequest httpServletRequest) {
        ModelAndView mav = new ModelAndView("/index");
        HttpSession session = httpServletRequest.getSession();
        //得到当前登录用户对象
        User user = (User) session.getAttribute(Constant.OA_USER);
        if (user == null) {
            return new ModelAndView("login");
        }
        //获取当前登录的员工对象
        Employee employee = employeeService.selectById(user.getEmployeeId());

        Department department = departmentService.selectById(employee.getDepartmentId());
        //获取登录用户可用功能模块列表
        List<Node> nodeList = nodeService.selectNodeByUserId(user.getUserId());
        //将当前的结点列表放入请求中 （放入请求属性）
        httpServletRequest.setAttribute("node_list", nodeList);

        //如果employee数据只是为index.ftl服务的话，
        // 放入当前的请求中request.setAttribute 是没问题的。
        // 但在当前项目中需要反复用到当前登录的员工是哪一位，所以将他的存储范围放大，放入到了session中
        session.setAttribute("current_employee", employee);
        session.setAttribute("current_department", department);
        //最后利用请求转发  （请求派发至ftl进行展示）
        return mav;
    }
}
