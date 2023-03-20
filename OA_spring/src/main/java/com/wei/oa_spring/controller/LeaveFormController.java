package com.wei.oa_spring.controller;

import com.wei.oa_spring.common.ApiRestResponse;
import com.wei.oa_spring.common.Constant;
import com.wei.oa_spring.exception.OAExceptionEnum;
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
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    public ApiRestResponse create(HttpServletRequest request, HttpSession session) {
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

        try {

            LeaveForm form = new LeaveForm();
            form.setEmployeeId(user.getEmployeeId());
            form.setStartTime(sdf.parse(strStartTime));
            form.setEndTime(sdf.parse(strEndTime));
            form.setFormType(Integer.parseInt(formType));
            form.setReason(reason);
            form.setCreateTime(new Date());
            leaveFormService.createLeaveForm(form);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        leaveFormService.createLeaveForm(form);
        return ApiRestResponse.success();
    }

    //查询需要审核的请假单列表
    @GetMapping("list")
    public ApiRestResponse getLeaveFormList(HttpSession session) {
        //1.接收各项请假单数据
        User user = (User) session.getAttribute(Constant.OA_USER);
        if (user == null) {
            return ApiRestResponse.error(OAExceptionEnum.NEED_LOGIN);
        }
        List<Map> formList = leaveFormService.getLeaveFormList("process", user.getEmployeeId());
        //count = formList.size(); 怎么加入到响应里面?
        return ApiRestResponse.success(formList);
    }

    //具体实现类没搞好
    //审批操作
    @PostMapping("audit")
    public ApiRestResponse audit(HttpSession session, @Valid @RequestBody AuditProcessFlowReq auditProcessFlowReq) {
        //这个登录判断后面可以仿 统一校验管理员身份,做一个统一校验登录
        ProcessFlow processFlow = new ProcessFlow();
        User user = (User) session.getAttribute(Constant.OA_USER);
        if (user == null) {
            return ApiRestResponse.error(OAExceptionEnum.NEED_LOGIN);
        }
        BeanUtils.copyProperties(auditProcessFlowReq, processFlow);
        leaveFormService.audit(auditProcessFlowReq);
        return ApiRestResponse.success();
    }
}
