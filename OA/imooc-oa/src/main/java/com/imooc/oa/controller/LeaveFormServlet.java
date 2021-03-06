package com.imooc.oa.controller;

import com.alibaba.fastjson.JSON;
import com.imooc.oa.entity.Department;
import com.imooc.oa.entity.LeaveForm;
import com.imooc.oa.entity.User;
import com.imooc.oa.service.LeaveFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "LeaveFormServlet", urlPatterns = "/leave/*")
public class LeaveFormServlet extends HttpServlet {
    private LeaveFormService leaveFormService = new LeaveFormService();
    //Logger用来打印异常
    private Logger logger = LoggerFactory.getLogger(LeaveFormServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        //http://localhost/leave/create     怎么拿到create这个字符呢？
        String uri = request.getRequestURI();
        String methodName = uri.substring(uri.lastIndexOf("/") + 1);  //因为会截取到/本身所以需要+1
        System.out.println(methodName);
        if (methodName.equals("create")) {
            //创建请假单的处理
            this.create(request, response);
        } else if (methodName.equals("list")) {
            this.getLeaveFormList(request, response);
        } else if (methodName.equals("audit")) {
            this.audit(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    /**
     * 创建请假单
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接收各项请假单数据
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("login_user");
        String formType = request.getParameter("formType");
        String strStartTime = request.getParameter("startTime");
        String strEndTime = request.getParameter("endTime");
        String reason = request.getParameter("reason");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");

        Map result = new HashMap();
        try {
            LeaveForm form = new LeaveForm();
            form.setEmployeeId(user.getEmployeeId());
            form.setStartTime(sdf.parse(strStartTime));
            form.setEndTime(sdf.parse(strEndTime));
            form.setFormType(Integer.parseInt(formType));
            form.setReason(reason);
            form.setCreateTime(new Date());
            leaveFormService.createLeaveForm(form);
            //2.调用业务逻辑方法
            result.put("code", "0");
            result.put("message", "success");
        } catch (Exception e) {
            logger.error("请假申请异常", e);
//            e.printStackTrace();
            result.put("code", e.getClass().getSimpleName()); //拿到类名
            result.put("message", e.getMessage());
        }
        //3.组织响应数据
        String json = JSON.toJSONString(result);
        response.getWriter().println(json);
//        String json = JSON.toJSONString(result);
//        response.getWriter().println(json); //对外进行输出
    }

    /**
     * 查询需要审核的请假单列表
     *
     * @param request
     * @param response
     * @throws IOException
     */
    private void getLeaveFormList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //通过session 接收各项请假单数据
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("login_user");
        List<Map> formList = leaveFormService.getLeaveFormList("process", user.getEmployeeId());
        Map result = new HashMap();
        result.put("code", "0");    //返回0 代表服务器响应成功
        result.put("msg", "");
        result.put("count", formList.size());   //count 所有数据的总数
        result.put("data", formList);
        String json = JSON.toJSONString(result);
        System.out.println(json);
        //通过响应进行输出  即设计服务器给我们的信息
        response.getWriter().println(json);
    }

    /**
     * 处理审批操作
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void audit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String formId = request.getParameter("formId");
        String result = request.getParameter("result");
        String reason = request.getParameter("reason");

        //从当前会话中进行提取 操作人 operatorId 即 getEmployeeId
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("login_user");
        Map mpResult = new HashMap();
        //因为leaveFormService.audit本身会抛出异常，所以这里做一个异常捕获
        try {
            // Long.parseLong(formId) 转为长整形
            leaveFormService.audit(Long.parseLong(formId), user.getEmployeeId(), result, reason);
            mpResult.put("code", "0");
            mpResult.put("message", "success");
        } catch (Exception e) {
            logger.error("请假单审核失败", e);
            mpResult.put("code", e.getClass().getSimpleName());
            mpResult.put("message", e.getMessage());
        }
        String json = JSON.toJSONString(mpResult);
        response.getWriter().println(json); //对外进行输出
    }
}
