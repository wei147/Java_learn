package com.imooc.reader.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {
    @Resource
    //要与applicationContext.xml 定义的 kaptcha beanId一致  (Producer实际上是一个接口,而在xml中引入的...kaptcha.impl...就是实现Producer接口)
    private Producer kaptchaProducer;

    //生成验证码图片
    //SpringMVC 底层还是依赖于J2EE的web模块————Servlet,对于在开发中有一些特殊的场景必须要使用到原生的请求或者响应对象,那此时就可以像当前这样书写,把原生对象放在参数列表中
    @GetMapping("/verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //响应立即过期
        response.setDateHeader("Expires",0);
        //因为每一次要求生成的验证码都是全新的,所以我们要把所有的与浏览器的缓存都清空掉 (不存储、不缓存、必须重新进行校验 | no-store,no-cache..)
        response.setHeader("Cache-Control","no-store,no-cache,must-revalidate");
        //这两项比较古老了,是在ie5以后的扩展指令,其用意也是和缓存控制相关,平时用不到,出于兼容性的考虑将其加上
        response.setHeader("Cache-Control","post-check=0,pre-check=0");
        response.setHeader("Pragma","no-cache");
        //返回的内容类型
        response.setContentType("image/png");

        //生成验证码文本
        String verifyCode = kaptchaProducer.createText();
        //将验证码放入会话中
        request.getSession().setAttribute("kaptchaVerifyCode",verifyCode);
        System.out.println(request.getSession().getAttribute("kaptchaVerifyCode"));
        //创建验证码图片 (根据传入的验证码文本创建图片)
        BufferedImage image = kaptchaProducer.createImage(verifyCode);
        //因为这里得到的图片是二进制的,所以使用getOutputStream输出流; 如果输出内容是字符的话,就要使用getWriter来输出字符
        ServletOutputStream out = response.getOutputStream();
        //javax.imageio包中所提供的图片的输入输出功能。将image放入到out输出流中,其输出的图片格式为 png。
        // 通过这一句话,就可以完成将图片从服务器端通过响应发送给客户端浏览器,客户端浏览器收到了这个图片数据以后,一看内容类型是png就当图片进行展示了
        ImageIO.write(image,"png",out);
        out.flush();    //立即输出
        out.close();    //关闭输出流

        //注: Servlet程序向ServletOutputStream或PrintWriter对象中写入的数据将被Servlet引擎从response里面获取，
        // Servlet引擎将这些数据当做响应消息的正文，然后再与响应状态行和各响应头组合后输出到客户端。
    }
}
