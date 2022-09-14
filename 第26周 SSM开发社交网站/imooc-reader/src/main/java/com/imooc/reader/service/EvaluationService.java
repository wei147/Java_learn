package com.imooc.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    /**
     * 按图书编号查询有效短评
     *
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId);


    /**
     * 分页查询评论
     *
     * @param page 页号
     * @param rows 每页记录数
     * @return 分页对象
     */
    public IPage<Evaluation> paging(Integer page, Integer rows);

    /**
     * 按短评编号查询短评
     *
     * @param evaluationId 短评编号
     * @return 短评对象
     */
    public Evaluation selectById(Long evaluationId);

    /**
     * 禁言短评操作
     *
     * @param evaluation
     * @return
     */
    public Evaluation disableEvaluation(Evaluation evaluation);

    /**
     * 查询所有评论信息
     * @return
     */
    public List<Evaluation> selectAll();



}
