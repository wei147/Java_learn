package com.wei.oa_spring.model.dao;

import com.wei.oa_spring.model.pojo.ProcessFlow;

import java.util.List;

public interface ProcessFlowMapper {
    int deleteByPrimaryKey(Long processId);

    int insert(ProcessFlow record);

    int insertSelective(ProcessFlow record);

    ProcessFlow selectByPrimaryKey(Long processId);

    int updateByPrimaryKeySelective(ProcessFlow record);

    int updateByPrimaryKey(ProcessFlow record);

    List<ProcessFlow> selectByFormId(Long formId);

    int update(ProcessFlow processFlow);
}