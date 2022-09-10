package com.imooc.reader.controller;

import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.entity.MemberReadState;
import com.imooc.reader.service.MemberService;
import com.imooc.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

//会员控制器 (用于会员交互的控制器)

@Controller
public class MemberController {
    @Resource
    private MemberService memberService;

    // .html可写可不写,但是如果是在互联网应用中,在我们互联网能够访问的情况下,建议跳转页面时将其加上后缀。
    // 一旦加上以后对于百度、谷歌这样的搜索引擎对其进行抓取时是十分友好的 对网站宣传、营销有用
    @GetMapping("/register.html")
    public ModelAndView showRegister() {
        return new ModelAndView("/register");
    }

    @GetMapping("/login.html")
    public ModelAndView showLogin() {
        return new ModelAndView("/login");
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
            result.put("code", "VC01");
            result.put("msg", "验证码错误");
        } else {
            try {
                //如果没有异常的话直接写入数据库
                memberService.createMember(username, password, nickname);
                result.put("code", "0");
                result.put("msg", "success");
                //存在异常的话,将异常信息放入result,最终在前台进行显示
            } catch (BussinessException ex) {
                ex.printStackTrace();
                result.put("code", ex.getCode());
                result.put("msg", ex.getMsg());
            }
        }
        return result;
    }

    //作为HttpSession这个对象,也是容许直接进行注入的,只需要把HttpSession对象放在参数列表中。至于这个对象的用途是: 在我们进行登录校验后,会将当前登录的这个会员信息存放到Session中,以备后续使用
    @PostMapping("/check_login")
    @ResponseBody
    public Map checkLogin(String username, String password, String vc, HttpSession session) {
        //正确的验证码        (相比于上面拿到Session数据这里直接使用session就可以,好像这种方式更快捷?)
        String verifyCode = (String) session.getAttribute("kaptchaVerifyCode");
        Map result = new HashMap();
        //验证码比对
        if (vc == null || verifyCode == null || !vc.equalsIgnoreCase(verifyCode)) {
            result.put("code", "VC01");
            result.put("msg", "验证码错误");
        } else {
            try {
                //登录校验成功之后会返回一个Member对象,我们可以将这个member当前登录用户存放在Session中,以备后续使用。 (这里放入的Session信息可以在index.ftl中被拿到,太强了)
                Member member = memberService.checkLogin(username, password);
                //这样只要这个会话还存在,用户登录成功后就可以通过LoginMember来提取到当前登录用户的数据了 将其放在首页显示,index.ftl
                session.setAttribute("loginMember", member);
                result.put("code", "0");
                result.put("msg", "success");
            } catch (BussinessException ex) {
                ex.printStackTrace();
                result.put("code", ex.getCode());
                result.put("msg", ex.getMsg());
            }
        }
        return result;
    }

    /**
     * 更新想看/看过阅读状态
     *
     * @param memberId  会员id
     * @param bookId    图书id
     * @param readState 阅读状态
     * @return 处理结果
     */
    @PostMapping("/update_read_state")
    @ResponseBody
    public Map updateReadState(Long memberId, Long bookId, Integer readState) {
        Map result = new HashMap();
        //这个更新方法并没有抛出任何的业务逻辑异常,为什么在这还要捕获?
        //答: 现在你没有抛出不代表未来不会抛出,所以我们在这进行一下捕获,也是为了未来程序的扩展性考虑的
        try {
            MemberReadState memberReadState = memberService.updateMemberReadState(memberId, bookId, readState);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    @PostMapping("/evaluate")
    @ResponseBody
    public Map evaluate(Long memberId, Long bookId, Integer score, String content){
        Map result = new HashMap();
        try {
            Evaluation evaluate = memberService.evaluate(memberId, bookId, score, content);
            result.put("code", "0");
            result.put("msg", "success");
            //这个evaluate由具体的业务逻辑决定要不要进入到返回值。(可以直接在响应中看到)
            result.put("evaluate",evaluate);
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    /**
     * 短评点赞
     *
     * @param evaluationId 短评编号
     * @return 短评对象
     */
    @PostMapping("/enjoy")
    @ResponseBody
    public Map enjoy(Long evaluationId){
        Map result = new HashMap();
        try {
            Evaluation evaluation = memberService.enjoy(evaluationId);
            result.put("code", "0");
            result.put("msg", "success");
            result.put("evaluation",evaluation);
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }
}
