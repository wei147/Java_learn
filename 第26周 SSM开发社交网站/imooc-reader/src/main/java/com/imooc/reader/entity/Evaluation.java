package com.imooc.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("evaluation")
public class Evaluation {
    @TableId(type = IdType.AUTO)
    private Long evaluationId;
    private Long bookId;
    private String content;
    private Integer score;
    private Date createTime;
    private Long memberId;
    private Integer enjoy;
    private String state;
    private String disableReason;
    private Date disableTime;

    //在很多情况下,我们确实需要在这个实体中增加一些与数据库字段不对应的属性,比如说这里有一个bookId
    //但是它只能表明id这个信息,如果想获取评论的时候也把与之对应的图书的名字也打印出来,该怎么做?
    //在当前实体中增加     private Book book; 属性名为book,为其生成get、set方法,那这样作为这个属性
    //就拥有了一个book的关联对象,但是作为这个关联对象,它底层肯定是不会在Evaluation表中有对应字段的,
    //所以针对于这种没有对应字段的属性,我们还需要增加一个注解  @TableField(exist = false)  另外一个与evaluation关联的就是member 会员表了
    @TableField(exist = false)  //说明book属性没有对应字段,不会参与到sql自动生成   (2022年9月6日21:16:58 疑问: 这里应该更明确说的是 evaluation表里没有这个字段)
    private Book book;

    @TableField(exist = false)
    private Member member;

    //关键地方来了,这里的book和member我们如何对他们把数据填充上 ?
    //答: 这就要依托于书写是Service实现类了 EvaluationServiceImpl

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getEnjoy() {
        return enjoy;
    }

    public void setEnjoy(Integer enjoy) {
        this.enjoy = enjoy;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDisableReason() {
        return disableReason;
    }

    public void setDisableReason(String disableReason) {
        this.disableReason = disableReason;
    }

    public Date getDisableTime() {
        return disableTime;
    }

    public void setDisableTime(Date disableTime) {
        this.disableTime = disableTime;
    }
}
