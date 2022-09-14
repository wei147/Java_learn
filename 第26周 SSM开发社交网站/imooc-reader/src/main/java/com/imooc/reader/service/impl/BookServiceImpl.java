package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.MemberReadState;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.mapper.MemberReadStateMapper;
import com.imooc.reader.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("bookService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)    //目前里边的每一个方法默认不开启事务
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private MemberReadStateMapper memberReadStateMapper;
    @Resource
    private EvaluationMapper evaluationMapper;

    /**
     * 分页查询图书
     *
     * @param categoryId 分页编号
     * @param order      排序方式
     * @param page       页号
     * @param rows       每页记录数
     * @return 分页对象
     */
    @Override
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer rows) {
        Page<Book> p = new Page<Book>(page, rows);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<Book>();
        //这个判断代表了从前台传入了有效的分页编号
        if (categoryId != null && categoryId != -1) {
            queryWrapper.eq("category_id", categoryId);
        }
        if (order != null) {
            //排序的规则有两种
            //按照评价人数来进行排序
            if (order.equals("quantity")) {
                queryWrapper.orderByDesc("evaluation_quantity");    //按照指定字段进行降序排列
                //按照具体的评分进行降序排列
            } else if (order.equals("score")) {
                queryWrapper.orderByDesc("evaluation_score");
            }
        }
        //还没有任何需要筛选的条件,所以直接将queryWrapper对象放入到第二个参数。相当于对原始的所有数据进行分页查询了
        IPage<Book> pageObject = bookMapper.selectPage(p, queryWrapper);

        return pageObject;
    }

    /**
     * 根据图书编号查询图书对象
     *
     * @param bookId 图书编号
     * @return 图书对象
     */
    @Override
    public Book selectById(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        return book;
    }

    /**
     * 更新图书评分/评价数量
     */
    //疑惑: 我从前台的不管是Controller还是什么,直接调用BookMapper不就可以了吗
    // 答: 虽然从语法上没问题,但是在实际的工作中绝对不允许,因为我们一直强调基于MVC的按层逐级调用。就像当前的例子禁止从Controller直接调
    // 用某个Mapper的方法来完成数据的操作,中间必须要经过Service,也就是说作为Controller直接面向的是Service,再由Service去调用对应的Mapper。
    // 要完成这个严格的按层逐级调用的工作。如果每一个工程师都遵循这样的开发规范,会让我们程序维护起来变得非常的轻松
    @Override
    @Transactional //这里是更新操作要开启声明式事务
    public void updateEvaluation() {
        bookMapper.updateEvaluation();
    }

    /**
     * 创建新的图书
     */
    @Override
    @Transactional
    public Book createBook(Book book) {
        bookMapper.insert(book);
        //那么在插入成功后,因为book的主键是自增的,在执行完insert()以后,
        // 由MyBatis-plus会自动的将新生成的主键回填到bookId编号中,所以将参数中的
        //book进行返回就可以了,只不过此时的book相比参数中的book它多了一个图书编号
        return book;
    }

    /**
     * 更新图书信息
     * @param book 新图书数据
     * @return 更新后的数据
     */
    @Override
    @Transactional
    public Book updateBook(Book book) {
        bookMapper.updateById(book);//按照id号对数据进行更新
        return book;
    }

    /**
     * 删除图书及相关数据
     * @param bookId 图书编号
     */
    @Override
    @Transactional
    //作为参数bookId不单是要把对应的图书信息进行删除,那么与之相关的数据还有评论信息和阅读状态这两项也是需要清除的,
    // 所以对于deleteBook来说,它底层实际影响到了多张表
    public void deleteBook(Long bookId) {
        bookMapper.deleteById(bookId);
        //这里会衍生出一个新问题: 删除的时候是根据图书编号将所有对应的会员阅读信息一并删除,那这时基于阅读状态表Id进行删除显然效率太低了,我们需要
        // 一次性删除多条数据怎么做? 可以考虑用 delete
        QueryWrapper<MemberReadState> mrsQueryWrapper = new QueryWrapper<MemberReadState>();
        mrsQueryWrapper.eq("book_id",bookId);
        memberReadStateMapper.delete(mrsQueryWrapper);

        QueryWrapper<Evaluation> eQueryWrapper = new QueryWrapper<Evaluation>();
        eQueryWrapper.eq("book_id",bookId);
        evaluationMapper.delete(eQueryWrapper);
    }
}
