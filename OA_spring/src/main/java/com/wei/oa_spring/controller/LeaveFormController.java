package com.wei.oa_spring.controller;

import com.wei.oa_spring.common.ApiRestResponse;
import com.wei.oa_spring.common.Constant;
import com.wei.oa_spring.exception.OAExceptionEnum;
import com.wei.oa_spring.model.pojo.ProcessFlow;
import com.wei.oa_spring.model.pojo.User;
import com.wei.oa_spring.model.request.AuditProcessFlowReq;
import com.wei.oa_spring.model.request.CreateLeaveFormReq;
import com.wei.oa_spring.service.LeaveFormService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/leave")
public class LeaveFormController {

    @Resource
    LeaveFormService leaveFormService;

    @PostMapping("/leaveForm/create")
    //加了@RequestBody之后,我们的Spring就可以从我们的body中,去把这个CreateLeaveFormReq类给对应起来
    public ApiRestResponse create(@Valid @RequestBody CreateLeaveFormReq createLeaveFormReq, HttpSession session) {
        //1.接收各项请假单数据
        User user = (User) session.getAttribute(Constant.OA_USER);
        if (user == null) {
            return ApiRestResponse.error(OAExceptionEnum.NEED_LOGIN);
        }
        leaveFormService.createLeaveForm(createLeaveFormReq);
        return ApiRestResponse.success();
    }

    //查询需要审核的请假单列表
    @GetMapping("/leaveForm/list")
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

    //审批操作
    @GetMapping("/leaveForm/audit")
    public ApiRestResponse audit(HttpSession session, @Valid @RequestBody AuditProcessFlowReq auditProcessFlowReq) {
        //这个登录判断后面可以仿 统一校验管理员身份,做一个统一校验登录
        ProcessFlow processFlow = new ProcessFlow();
        User user = (User) session.getAttribute(Constant.OA_USER);
        if (user == null) {
            return ApiRestResponse.error(OAExceptionEnum.NEED_LOGIN);
        }
        BeanUtils.copyProperties(auditProcessFlowReq,processFlow);
        leaveFormService.audit()
    }
}
