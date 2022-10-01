package com.wei.oa_spring.model.request;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 审批请假单的一个请求类
 */
public class AuditProcessFlowReq {

    @NotNull(message = "formId不能为null")
    private Long formId;

    @NotNull(message = "result不能为null")
    private String result;

    @NotNull(message = "reason不能为null")
    private String reason;

    @NotNull(message = "operatorId不能为null")
    private Long operatorId;

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getFormId() {
        return formId;
    }

    public void setFormId(Long formId) {
        this.formId = formId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result == null ? null : result.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

}