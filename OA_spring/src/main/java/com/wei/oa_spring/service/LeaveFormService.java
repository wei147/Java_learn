package com.wei.oa_spring.service;

import com.wei.oa_spring.model.request.AuditProcessFlowReq;
import com.wei.oa_spring.model.request.CreateLeaveFormReq;

import java.util.List;
import java.util.Map;

public interface LeaveFormService {

    void createLeaveForm(CreateLeaveFormReq createLeaveFormReq);

    List<Map> getLeaveFormList(String pfState, Long operatorId);

    void audit(AuditProcessFlowReq auditProcessFlowReq);
}
