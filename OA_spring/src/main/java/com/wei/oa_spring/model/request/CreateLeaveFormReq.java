package com.wei.oa_spring.model.request;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 创建请假单的一个请求类
 */
public class CreateLeaveFormReq {

    @NotNull(message = "employeeId不能为null")
    private Long employeeId;

    @NotNull(message = "formType不能为null")
    private Integer formType;

    @NotNull(message = "startTime不能为null")
    private Date startTime;

    @NotNull(message = "endTime不能为null")
    private Date endTime;

    @NotNull(message = "reason不能为null")
    private String reason;

    @NotNull(message = "createTime不能为null")
    private Date createTime;


    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getFormType() {
        return formType;
    }

    public void setFormType(Integer formType) {
        this.formType = formType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

//    public void setState(String state) {
//        this.state = state == null ? null : state.trim();
//    }
}