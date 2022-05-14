package com.imooc.oa.dao;

import com.imooc.oa.entity.LeaveForm;
import com.imooc.oa.utils.MybatisUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class LeaveFromDaoTest {

    @Test
    public void insert() {
        MybatisUtils.executeUpdate(sqlSession -> {
           LeaveFromDao dao = sqlSession.getMapper(LeaveFromDao.class);
           LeaveForm form = new LeaveForm();
           form.getEmployeeId();
           dao.insert();
        })
    }
}


























