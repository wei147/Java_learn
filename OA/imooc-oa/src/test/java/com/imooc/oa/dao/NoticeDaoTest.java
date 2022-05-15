package com.imooc.oa.dao;

import com.imooc.oa.entity.Notice;
import com.imooc.oa.utils.MybatisUtils;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class NoticeDaoTest {

    @Test
    public void insert() {
        MybatisUtils.executeUpdate(sqlSession -> {
            NoticeDao dao = sqlSession.getMapper(NoticeDao.class);
            Notice notice = new Notice();
            notice.setNotice(4L);
            notice.setReceiverId(5L);
            notice.setContent("您的请假已被部门经理XXX批准，审批意见：同意");
            notice.setCreateTime(new Date());
            dao.insert(notice);
            return null;
        });
    }
}