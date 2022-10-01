package com.wei.oa_spring.model.dao;

import com.wei.oa_spring.model.pojo.Notice;

import java.util.List;

public interface NoticeMapper {
    int deleteByPrimaryKey(Long noticeId);

    int insert(Notice record);

    int insertSelective(Notice record);

    Notice selectByPrimaryKey(Long noticeId);

    int updateByPrimaryKeySelective(Notice record);

    int updateByPrimaryKey(Notice record);

    List<Notice> selectByReceiverId(Long receiverId);   //接收方id
}