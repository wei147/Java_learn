package com.wei.oa_spring.controller;

import com.wei.oa_spring.common.ApiRestResponse;
import com.wei.oa_spring.common.Constant;
import com.wei.oa_spring.exception.OAExceptionEnum;
import com.wei.oa_spring.model.pojo.Notice;
import com.wei.oa_spring.model.pojo.User;
import com.wei.oa_spring.service.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class NoticeController {

    @Resource
    NoticeService noticeService;


    @GetMapping("/notice/list")
    @ResponseBody
    public ApiRestResponse getNoticeList(HttpSession session) {
        User user = (User) session.getAttribute(Constant.OA_USER);
        if (user == null) {
            return ApiRestResponse.error(OAExceptionEnum.NEED_LOGIN);
        }
        //这里就可以查到这个员工最近要查看的一百条信息，返回的是一个集合
        List<Notice> noticeList = noticeService.getNoticeList(user.getEmployeeId());
        return ApiRestResponse.success(noticeList);
    }
}
