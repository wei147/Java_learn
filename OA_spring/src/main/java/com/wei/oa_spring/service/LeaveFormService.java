package com.wei.oa_spring.service;

import com.wei.oa_spring.model.pojo.LeaveForm;
import com.wei.oa_spring.model.request.AuditProcessFlowReq;
import com.wei.oa_spring.model.request.CreateLeaveFormReq;

import java.util.List;
import java.util.Map;

public interface LeaveFormService {

    LeaveForm createLeaveForm(LeaveForm leaveForm);

    List<Map> getLeaveFormList(String pfState, Long operatorId);

    void audit(Long formId, Long operatorId, String result, String reason);
}
