package com.wei.oa_spring.adminController;

import com.wei.oa_spring.common.ApiRestResponse;
import com.wei.oa_spring.common.Constant;
import com.wei.oa_spring.exception.OAExceptionEnum;
import com.wei.oa_spring.model.dao.DepartmentMapper;
import com.wei.oa_spring.model.pojo.Department;
import com.wei.oa_spring.model.pojo.User;
import com.wei.oa_spring.service.DepartmentService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("admin/department/")
@Transactional(propagation = Propagation.REQUIRED)
public class AdmDepartmentController {

    @Resource
    DepartmentService departmentService;

    @Resource
    DepartmentMapper departmentMapper;

    //通过id查询部门名称
    @GetMapping("get")
    public ApiRestResponse getDepartment(@RequestParam Long department_id) {
        Department department = departmentService.selectById(department_id);
        return ApiRestResponse.success(department.getDepartmentName());
    }

    //获取所有部门信息
    @GetMapping("list")
    public ApiRestResponse list() {
        List<Department> departmentList = departmentMapper.selectAll();
        return ApiRestResponse.success(departmentList);
    }

    //新增部门信息
    @PostMapping("add")
    public ApiRestResponse add(@RequestParam String department_name) {
        Department department = new Department();
        department.setDepartmentName(department_name);
        int count = departmentMapper.insertSelective(department);
        if (count == 0) {
            return ApiRestResponse.error(OAExceptionEnum.INSERT_FAILED);
        }
        return ApiRestResponse.success();
    }

    //更新部门信息
    @PostMapping("update")
    public ApiRestResponse update(@RequestBody Department department) {
        Department departmentOld = departmentService.selectById(department.getDepartmentId());
        if (departmentOld == null) {
            return ApiRestResponse.error(OAExceptionEnum.NO_DEPARTMENT_FOUND);
        }
        int count = departmentMapper.updateByPrimaryKeySelective(department);
        if (count == 0) {
            return ApiRestResponse.error(OAExceptionEnum.UPDATE_FAILED);
        }
        return ApiRestResponse.success();
    }

    //删除部门
    @PostMapping("delete")
    public ApiRestResponse delete(@RequestParam Long department_id) {
        int count = departmentMapper.deleteByPrimaryKey(department_id);
        if (count == 0) {
            return ApiRestResponse.error(OAExceptionEnum.DELETE_FAILED);
        }
        return ApiRestResponse.success();
    }
}
