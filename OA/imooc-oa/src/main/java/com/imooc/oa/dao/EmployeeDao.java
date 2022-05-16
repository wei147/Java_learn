package com.imooc.oa.dao;

import com.imooc.oa.entity.Employee;
import org.apache.ibatis.annotations.Param;

/**
 * 接口类interface 只负责写方法 （而方法对应实现）
 */
public interface EmployeeDao {
    public Employee selectById(Long employeeId);

    /**
     * 根据传入员工对象获取上级主管对象
     * @param employee 员工对象
     * @return  上级主管对象
     */
    public Employee selectLeader(@Param("emp") Employee employee);
}
