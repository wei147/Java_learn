package com.imooc.oa.entity;

import java.util.Date;

/**
 * 显示保存通知
 */
public class Notice {
    private Long noticeId;
    private Long receiverId;
    private String content;
    private Date createTime;

    public Long getNotice() {
        return noticeId;
    }

    public void setNotice(Long notice) {
        this.noticeId = notice;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
