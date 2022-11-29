package com.wei.oa_spring.model.request;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class EmployeeReq {

    @NotNull(message = "姓名不能为空")
    private String name;

    @NotNull(message = "部门id不能为空")
    private Long departmentId;

    @NotNull(message = "职称不能为空")
    private String title;

    @NotNull(message = "level不能为空")
    @Min(1)
    @Max(8)
    private Integer level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
