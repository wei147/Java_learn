package com.wei.oa_spring.controller;

import com.wei.oa_spring.common.ApiRestResponse;
import com.wei.oa_spring.common.Constant;
import com.wei.oa_spring.exception.OAExceptionEnum;
import com.wei.oa_spring.model.pojo.Node;
import com.wei.oa_spring.model.pojo.User;
import com.wei.oa_spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    //测试用
    @GetMapping("/user/1")
    public User getUser() {
        User user = userService.selectById();
        return user;
    }

    @PostMapping("/login")
    public ApiRestResponse login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        //登录时所要用的关键任务 HttpSession session对象
        //1.校验 username不能为空
        if (StringUtils.isEmpty(username)) {
            return ApiRestResponse.error(OAExceptionEnum.NEED_USER_NAME);
        }
        //2.校验 password不能为空
        if (StringUtils.isEmpty(password)) {
            return ApiRestResponse.error(OAExceptionEnum.NEED_PASS_WORD);
        }
        User user = userService.login(username, password);
        user.setPassword(null); //保存用户信息时,不保存密码(为了安全起见这里的password设置为空,不会返回给用户)
        session.setAttribute(Constant.OA_USER, user);
        return ApiRestResponse.success(user);
    }

    /**
     * 登出,清除session
     *
     * @param session
     * @return 只需要在Controller层清除session信息就可以, 不需要到Service
     */
//    @ApiOperation("用户登出")
    @PostMapping("/user/logout")
    public ApiRestResponse logout(HttpSession session) {
        //清除Session
        session.removeAttribute(Constant.OA_USER);
        return ApiRestResponse.success();
    }



    //未实现
//    @GetMapping("/user/department")
//    @ResponseBody
//    public ApiRestResponse getDepartment(HttpSession session) {
//        User user = (User) session.getAttribute(Constant.OA_USER);
//        List<Node> nodeList = userService.selectNodeByUserId(user.getUserId());
//        session.setAttribute("node_list", nodeList);
//        return ApiRestResponse.success(nodeList);
//    }
}
