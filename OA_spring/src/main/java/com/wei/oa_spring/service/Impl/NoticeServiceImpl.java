package com.wei.oa_spring.service.Impl;

import com.wei.oa_spring.exception.OAException;
import com.wei.oa_spring.exception.OAExceptionEnum;
import com.wei.oa_spring.model.dao.NoticeMapper;
import com.wei.oa_spring.model.pojo.Notice;
import com.wei.oa_spring.service.NoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
    @Resource
    NoticeMapper noticeMapper;

    @Override
    public void insert(Notice notice) {
        int count = noticeMapper.insert(notice);
        if (count == 0) {
            throw new OAException(OAExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public List<Notice> getNoticeList(Long receiverId) {
        List<Notice> noticeList = noticeMapper.selectByReceiverId(receiverId);
        return noticeList;
    }
}
