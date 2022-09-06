package com.imooc.reader.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.mapper.MemberMapper;
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
    //memberMapper在查询每条评论与之对应的会员对象时用得到 1.通过evaluation表中的member_id
    // 2.然后由member表查询这个id，进而可以把member表中信息显示在evaluation模块中 ${evaluation.member.nickname} ftl中引用 （BookMapper也类似）
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;
    /**
     * 按图书编号查询有效短评
     *
     * @param bookId 图书编号
     * @return 评论列表
     */
    @Override
    public List<Evaluation> selectByBookId(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        QueryWrapper<Evaluation> queryWrapper = new QueryWrapper<Evaluation>();
        //首先按照bookId来进行筛选
        queryWrapper.eq("book_id",bookId);
        //筛选有效短评,当然是根据state这个字段
        queryWrapper.eq("state","enable");
        //同时默认情况下,按创建时间的降序来进行排列
        queryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(queryWrapper);

        //[book和member我们如何对他们把数据填充上? 答:这就要依托于书写是Service实现类了EvaluationServiceImpl]
        // 在获取到对应的Evaluation List集合的时候,我们也要进行额外的查询工作————来查询
        // 每一个评论它所对应的会员以及图书的信息
        for(Evaluation eva:evaluationList){
            //查询该评论与之对应的会员对象
            Member member = memberMapper.selectById(eva.getMemberId());
            eva.setMember(member);
            eva.setBook(book);
        }
        return evaluationList;
    }
}
