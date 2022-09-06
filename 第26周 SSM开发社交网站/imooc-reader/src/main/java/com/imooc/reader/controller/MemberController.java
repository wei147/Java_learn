package com.imooc.reader.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

//会员控制器

@Controller
public class MemberController {
    // .html可写可不写,但是如果是在互联网应用中,在我们互联网能够访问的情况下,建议跳转页面时将其加上后缀。
    // 一旦加上以后对于百度、谷歌这样的搜索引擎对其进行抓取时是十分友好的 对网站宣传、营销有用
    @GetMapping("/register.html")
    public ModelAndView showRegister() {
        return new ModelAndView("/register");
    }

    @PostMapping("registe")
    @ResponseBody
    public Map registe(String vc, String username, String password, String nickname, HttpServletRequest request) {
        //在这里要比对前台传入的验证码和Session中的验证码,那么怎么拿到 Session中的验证码? 和在KaptchaController.java中一样,在参数列表加入原生请求参数 拿Session中的信息
        //正确的验证码
        String verifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");
        Map result = new HashMap();
        //验证码比对     (在忽略大小写的情况下来对两者进行比对,如果两者不匹配的时候,对比失败)
        if (vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)) {
            result.put("code","VC01");
            result.put("msg","验证码错误");
        }else {
            result.put("code","0");
            result.put("msg","success");
        }
        return result;
    }
}
