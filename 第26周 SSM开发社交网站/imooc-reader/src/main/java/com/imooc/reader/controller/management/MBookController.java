package com.imooc.reader.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("m/book")
//@RequestMapping("management/book")
public class MBookController {
    @GetMapping("/index.html")
    public ModelAndView show(){
        return new ModelAndView("/management/book");
    }

    @PostMapping("/upload")
    //因为WangEditor需要返回json字符串,代表服务器是否处理成功
    //book.ftl 中的editor.customConfig.uploadFileName = 'img';//设置图片上传参数
    //设置好这一步我们就能通过基于参数file对象获取到从客户端上传到服务器的文件了
    //为什么要加HttpServletRequest原生的请求对象 ?  是因为文件上传以后总是要保存到某一个具体的目录下吧,,
    public Map upload(@RequestParam("img")MultipartFile file, HttpServletRequest request) throws MalformedURLException {
        //这个根路径不是webapp所在的路径,因为这句话是在运行时执行的,那肯定是在web应用发布运行起来以后这句话才执行,
        // 所以这句话实际得到的是在我们自动发布以后————也就是out目录下的imooc_reader_Web_exploded它的实际路径(有的是在target文件夹中)。
        //一定要区分开开在运行时路径是out下面的imooc_reader_Web_exploded
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/";

        Map result = new HashMap();
        result.put(uploadPath)
        return Object;
    }
}
