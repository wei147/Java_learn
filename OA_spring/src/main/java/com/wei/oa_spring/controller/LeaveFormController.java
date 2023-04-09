package com.wei.oa_spring.controller;

import com.alibaba.fastjson.JSON;
import com.wei.oa_spring.common.ApiRestResponse;
import com.wei.oa_spring.common.Constant;
import com.wei.oa_spring.exception.OAExceptionEnum;
import com.wei.oa_spring.filter.UserFilter;
import com.wei.oa_spring.model.pojo.LeaveForm;
import com.wei.oa_spring.model.pojo.ProcessFlow;
import com.wei.oa_spring.model.pojo.User;
import com.wei.oa_spring.model.request.AuditProcessFlowReq;
import com.wei.oa_spring.model.request.CreateLeaveFormReq;
import com.wei.oa_spring.service.LeaveFormService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("leave/")
public class LeaveFormController {

    @Resource
    LeaveFormService leaveFormService;

    @PostMapping("create")
    //加了@RequestBody之后,我们的Spring就可以从我们的body中,去把这个CreateLeaveFormReq类给对应起来
//    @Valid @RequestBody CreateLeaveFormReq createLeaveFormReq,
    public Object create(HttpServletRequest request, HttpSession session) throws InterruptedException {
        User user = (User) session.getAttribute(Constant.OA_USER);
        if (user == null) {
            return ApiRestResponse.error(OAExceptionEnum.NEED_LOGIN);
        }
        //1.接收各项请假单数据
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

            String code = "0";
            if (user.getUserId() == 1) {
                code = "1";
            }
            //2.调用业务逻辑方法
            result.put("code", code);
            result.put("message", "success");
        } catch (ParseException e) {
            result.put("code", e.getClass().getSimpleName()); //拿到类名
            result.put("message", e.getMessage());
        }
//        leaveFormService.createLeaveForm(form);
        return result;
    }

    //查询需要审核的请假单列表
    @GetMapping("list")
    public Object getLeaveFormList(HttpSession session) {
        //1.接收各项请假单数据
        User user = (User) session.getAttribute(Constant.OA_USER);
        if (user == null) {
            System.out.println(OAExceptionEnum.NEED_LOGIN);
        }
        assert user != null;
        List<Map> formList = leaveFormService.getLeaveFormList("process", user.getEmployeeId());
        //count = formList.size(); 怎么加入到响应里面?
        Map result = new HashMap();
        result.put("code", "0");    //返回0 代表服务器响应成功
        result.put("message", "");
        result.put("count", formList.size());   //count 所有数据的总数
        result.put("data", formList);
        //通过响应进行输出  即设计服务器给我们的信息
//        response.getWriter().println(json);
        return result;
    }

    //具体实现类没搞好
    //审批操作
    @PostMapping("audit")
    public Object audit(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        //这个登录判断后面可以仿 统一校验管理员身份,做一个统一校验登录
        ProcessFlow processFlow = new ProcessFlow();
        User user = (User) session.getAttribute(Constant.OA_USER);
        if (user == null) {
            return ApiRestResponse.error(OAExceptionEnum.NEED_LOGIN);
        }
        String formId = request.getParameter("formId");
        String result = request.getParameter("result");
        String reason = request.getParameter("reason");

        //从当前会话中进行提取 操作人 operatorId 即 getEmployeeId
        Map mpResult = new HashMap();
        //因为leaveFormService.audit本身会抛出异常，所以这里做一个异常捕获
        try {
            leaveFormService.audit(Long.parseLong(formId), user.getEmployeeId(), result, reason);
            mpResult.put("code", "0");
            mpResult.put("message", "success");
        } catch (Exception e) {
            mpResult.put("code", e.getClass().getSimpleName());
            mpResult.put("message", OAExceptionEnum.FAILED_TO_APPROVE_THE_LEAVE);
            return ApiRestResponse.error(OAExceptionEnum.FAILED_TO_APPROVE_THE_LEAVE);
        }
        return mpResult;
    }
}
