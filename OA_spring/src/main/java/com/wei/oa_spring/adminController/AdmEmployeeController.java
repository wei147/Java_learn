package com.wei.oa_spring.adminController;

import com.wei.oa_spring.common.ApiRestResponse;
import com.wei.oa_spring.exception.OAExceptionEnum;
import com.wei.oa_spring.model.dao.EmployeeMapper;
import com.wei.oa_spring.model.pojo.Department;
import com.wei.oa_spring.model.pojo.Employee;
import com.wei.oa_spring.model.request.EmployeeReq;
import com.wei.oa_spring.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("admin/employee/")
@Transactional(propagation = Propagation.REQUIRED)
public class AdmEmployeeController {

    @Resource
    EmployeeMapper employeeMapper;

    @Resource
    EmployeeService employeeService;

    //通过id查询员工
    @GetMapping("get")
    public Employee get(@RequestParam Long id) {
        return employeeService.selectById(id);
    }

    //获取所有员工信息
    @GetMapping("list")
    public ApiRestResponse list() {
        List<Employee> employeeList = employeeMapper.selectAll();
        return ApiRestResponse.success(employeeList);
    }

    //新增员工
    @PostMapping("add")
    public ApiRestResponse add(@Valid @RequestBody EmployeeReq employeeReq) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeReq, employee);
        int count = employeeMapper.insertSelective(employee);
        if (count == 0) {
            return ApiRestResponse.error(OAExceptionEnum.INSERT_FAILED);
        }
        return ApiRestResponse.success();
    }

    //更新员工信息
    @PostMapping("update")
    public ApiRestResponse update(@RequestBody Employee employee) {
        Employee employeeOld = employeeMapper.selectByPrimaryKey(employee.getEmployeeId());
        if (employeeOld == null) {
            return ApiRestResponse.error(OAExceptionEnum.NO_EMPLOYEE_FOUND);
        }
        int count = employeeMapper.updateByPrimaryKeySelective(employee);
        if (count == 0) {
            return ApiRestResponse.error(OAExceptionEnum.UPDATE_FAILED);
        }
        return ApiRestResponse.success();
    }

    //删除员工
    @PostMapping("delete")
    public ApiRestResponse delete(@RequestParam Long employee_id) {
        if (employeeMapper.selectByPrimaryKey(employee_id) == null) {
            return ApiRestResponse.error(OAExceptionEnum.NO_EMPLOYEE_FOUND);
        }
        int count = employeeMapper.deleteByPrimaryKey(employee_id);
        if (count == 0) {
            return ApiRestResponse.error(OAExceptionEnum.DELETE_FAILED);
        }
        return ApiRestResponse.success();
    }

}
