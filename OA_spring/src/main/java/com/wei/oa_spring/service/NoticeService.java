package com.wei.oa_spring.service;

import com.wei.oa_spring.model.pojo.Notice;

import java.util.List;

public interface NoticeService {

    public void insert(Notice notice);

    public List<Notice> getNoticeList(Long receiverId);
}
