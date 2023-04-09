package com.wei.oa_spring.controller;

import com.wei.oa_spring.common.ApiRestResponse;
import com.wei.oa_spring.common.Constant;
import com.wei.oa_spring.exception.OAExceptionEnum;
import com.wei.oa_spring.model.pojo.Node;
import com.wei.oa_spring.model.pojo.User;
import com.wei.oa_spring.service.NodeService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class NodeController {

    @Resource
    NodeService nodeService;

    //获取登录用户可用功能模块列表
    @GetMapping("/nodeList")
    public String getNodeList(HttpSession session) {
        User user = (User) session.getAttribute(Constant.OA_USER);
        if (user == null) {
            return "NEED_LOGIN";
        }
        List<Node> nodeList = nodeService.selectNodeByUserId(user.getUserId());
        session.setAttribute("node_list", nodeList);
        return "index";
    }
}
