package com.wei.oa_spring.model.dao;

import com.wei.oa_spring.model.pojo.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long employeeId);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Long employeeId);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    List<Employee> selectAll();

    /**
     * 根据传入员工对象获取上级主管对象
     *
     * @param employee 员工对象
     * @return 上级主管对象
     */
     Employee selectLeader(@Param("emp") Employee employee);
}