package com.imooc.oa.dao;

import com.imooc.oa.entity.LeaveForm;
import com.imooc.oa.entity.ProcessFlow;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface LeaveFromDao {
    public void insert(LeaveForm form);

    //    public List<Map> selectByParams( Map params); 不推荐
    //通过@Param 说明在他们xml中的参数名是什么
    public List<Map> selectByParams(@Param("pf_state") String pfState, @Param("pf_operator_id") Long operatorId);

    public LeaveForm selectById(Long formId);

    public void update(LeaveForm form); //对指定数据进行更新，即表单中的数据
}
