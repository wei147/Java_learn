package com.imooc.reader.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    //在类实例化的时候用@Resource注入对应的Mapper接口
    @Resource
    private EvaluationMapper evaluationMapper;
    /**
     * 按图书编号查询有效短评
     *
     * @param bookId 图书编号
     * @return 评论列表
     */
    @Override
    public List<Evaluation> selectByBookId(Long bookId) {
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        //首先按照bookId来进行筛选
        queryWrapper.eq("book_id",bookId);
        //筛选有效短评,当然是根据state这个字段
        queryWrapper.eq("state","enable");
//        evaluationMapper.selectList()
        return null;
    }
}
