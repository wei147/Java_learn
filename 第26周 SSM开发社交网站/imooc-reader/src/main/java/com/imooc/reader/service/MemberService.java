package com.imooc.reader.service;

import com.imooc.reader.entity.Member;

public interface MemberService {

    /**
     * 会员注册,创建新会员   (所谓注册其实就是创建一个新的会员)
     *
     * @param username 用户名
     * @param password 密码
     * @param nickname 昵称
     * @return 新会员对象
     */
    public Member createMember(String username, String password, String nickname);

    /**
     * 登录检查
     * @param username  用户名
     * @param password  密码
     * @return  登录对象
     */
    public Member checkLogin(String username, String password);

}
