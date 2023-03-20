package com.wei.oa_spring.controller;

import com.wei.oa_spring.common.ApiRestResponse;
import com.wei.oa_spring.common.Constant;
import com.wei.oa_spring.exception.OAExceptionEnum;
import com.wei.oa_spring.model.pojo.Notice;
import com.wei.oa_spring.model.pojo.User;
import com.wei.oa_spring.service.NoticeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/notice/")
public class NoticeController {

    @Resource
    NoticeService noticeService;


    @GetMapping("list")
    @ResponseBody
    public void getNoticeList(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute(Constant.OA_USER);
        if (user == null) {
//            return ApiRestResponse.error(OAExceptionEnum.NEED_LOGIN);
            System.out.println("NEED_LOGIN");
        }
        //这里就可以查到这个员工最近要查看的一百条信息，返回的是一个集合
        List<Notice> noticeList = noticeService.getNoticeList(user.getEmployeeId());
//        return ApiRestResponse.success(noticeList);

        Map result = new HashMap<>();

        result.put("code", "0");
        result.put("msg", "");
        result.put("count", noticeList.size());
        result.put("data", noticeList);
        String json = JSON.toJSONString(result);   //转为json字符串
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(json);  //通过响应向客户端返回，进行输出
    }


}
