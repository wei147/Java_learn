package com.wei.oa_spring.model.dao;

import com.wei.oa_spring.model.pojo.LeaveForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LeaveFormMapper {
    int deleteByPrimaryKey(Long formId);

    int insert(LeaveForm record);

    int insertSelective(LeaveForm record);

    LeaveForm selectByPrimaryKey(Long formId);

    int updateByPrimaryKeySelective(LeaveForm record);

    int updateByPrimaryKey(LeaveForm record);

    List<Map> selectByParams(@Param("pf_state") String pfState, @Param("pf_operator_id") Long operatorId);
}