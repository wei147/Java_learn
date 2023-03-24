package com.wei.oa_spring.model.pojo;

import java.util.Date;

public class Notice {
    private Long noticeId;

    private Long receiverId;

    private String content;

    private Date createTime;

    //为了满足java bean的要求，这里保留默认的构造方法   后
    public Notice() {

    }

    //通过构造方法简化消息的构造过程 noticeId是数据库自动赋值的，不进行赋值   先
    public Notice(Long receiverId, String content) {
        this.receiverId = receiverId;
        this.content = content;
        this.createTime = new Date();
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
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
        this.content = content == null ? null : content.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}