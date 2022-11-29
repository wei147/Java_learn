package com.wei.oa_spring.adminController;

import com.wei.oa_spring.common.ApiRestResponse;
import com.wei.oa_spring.model.dao.LeaveFormMapper;
import com.wei.oa_spring.model.pojo.Department;
import com.wei.oa_spring.model.pojo.LeaveForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("admin/leaveForm/")
public class AdmLeaveFormController {

    @Resource
    LeaveFormMapper leaveFormMapper;

    @GetMapping("list")
    public ApiRestResponse list() {
        List<LeaveForm> leaveFormList = leaveFormMapper.selectAll();
        return ApiRestResponse.success(leaveFormList);
    }

}
