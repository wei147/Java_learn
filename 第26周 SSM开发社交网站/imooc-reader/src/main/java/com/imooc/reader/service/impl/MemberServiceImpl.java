package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Member;
import com.imooc.reader.entity.MemberReadState;
import com.imooc.reader.mapper.MemberMapper;
import com.imooc.reader.mapper.MemberReadStateMapper;
import com.imooc.reader.service.MemberService;
import com.imooc.reader.service.exception.BussinessException;
import com.imooc.reader.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("memberService")
//作为当前的实现类,它的主要职责是完成与会员的交互,比如说会员的注册、登录、评论、点赞等功能。
//以上应该是写操作比较多,我们针对于当前声明式事务的配置默认是打开事务
@Transactional
public class MemberServiceImpl implements MemberService {
    @Resource
    private MemberMapper memberMapper;

    @Resource
    private MemberReadStateMapper memberReadStateMapper;

    /**
     * 会员注册,创建新会员   (所谓注册其实就是创建一个新的会员)
     *
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 新会员对象
     */
    @Override
    public Member createMember(String username, String password, String nickname) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username", username);
        List<Member> memberList = memberMapper.selectList(queryWrapper);
        //判断用户名是否已存在
        if (memberList.size() > 0) {
            throw new BussinessException("M01", "用户名已存在");   //这个错误将在前台页面进行显示
        }
        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);

        int salt = new Random().nextInt(1000) + 1000;   //盐值 1001-2000
        String password_md5 = MD5Utils.md5Digest(password, salt);
        member.setPassword(password_md5);
        member.setSalt(salt);
        member.setCreateTime(new Date());
        memberMapper.insert(member);
        return member;
    }

    /**
     * 登录检查
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录对象
     */
    @Override
    public Member checkLogin(String username, String password) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<Member>();
        queryWrapper.eq("username", username);
        Member member = memberMapper.selectOne(queryWrapper);
        //判断用户名是否已存在
        if (member == null) {
            throw new BussinessException("M02", "用户名不存在");
        }
        String password_md5 = MD5Utils.md5Digest(password, member.getSalt()); //拿数据库的加密密码和前台传过来经过同样经过加密处理的密码作比较
        if (!member.getPassword().equals(password_md5)) {
            throw new BussinessException("M03", "输入密码有误");
        }
        return member;
    }

    /**
     * 获取阅读状态
     *
     * @param memberId 会员编号
     * @param bookId   图书编号
     * @return 阅读状态对象   (这里有两种情况,1.是该用户压根对这本书无操作  2.是看过或者想看)
     */
    @Override
    //select方法不需要开启事务
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<MemberReadState>();
        queryWrapper.eq("member_id", memberId);
        queryWrapper.eq("book_id", bookId);

        //作为当前的查询结果要么只有一条数据,要么就是null
        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);

        return memberReadState;
    }

    /**
     * 更新阅读状态
     *
     * @param memberId  会员编号
     * @param bookId    图书编号
     * @param readState 阅读状态
     * @return 阅读状态对象
     */
    @Override
    public MemberReadState updateMemberReadState(Long memberId, Long bookId, Integer readState) {
        //首先要进行状态的查询,如果这个会员没有对应的阅读状态,则我们需要新建一条阅读状态保存到数据;
        // 但是如果这个会员之前已经有了对应的阅读状态,则需要对这个阅读状态的字段进行更新即可
        QueryWrapper<MemberReadState> queryWrapper = new QueryWrapper<MemberReadState>();
        queryWrapper.eq("member_id", memberId);
        queryWrapper.eq("book_id", bookId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(queryWrapper);
        //如果阅读状态是空的,则代表没有点过按钮,需要新建数据
        //无则新增,有则更新
        if (memberReadState.getReadState() == null) {
            memberReadState = new MemberReadState();
            memberReadState.setMemberId(memberId);
            memberReadState.setBookId(bookId);
            memberReadState.setCreateTime(new Date());
            memberReadState.setReadState(readState);
            memberReadStateMapper.insert(memberReadState);
            //如果之前这个状态已经存在,只需要更新即可。(阅读状态改为前台传来的readState)
        } else {
            memberReadState.setReadState(readState);
            //为什么这里的updateById 传入的是一个对象..? 看源码说明,传入的可以是一个实体类?
            memberReadStateMapper.updateById(memberReadState);
        }
        return memberReadState;
    }
}
