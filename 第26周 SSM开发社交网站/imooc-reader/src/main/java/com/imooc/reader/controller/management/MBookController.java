package com.imooc.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.exception.BussinessException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/book")
//@RequestMapping("management/book")
public class MBookController {
    @Resource
    private BookService bookService;

    @GetMapping("/index.html")
    public ModelAndView show() {
        return new ModelAndView("/management/book");
    }

    /**
     * wangEditor 文件上传
     *
     * @param file    上传文件
     * @param request 原生请求对象
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    //因为WangEditor需要返回json字符串,代表服务器是否处理成功
    //book.ftl 中的editor.customConfig.uploadFileName = 'img';//设置图片上传参数
    //设置好这一步我们就能通过基于参数file对象获取到从客户端上传到服务器的文件了
    //为什么要加HttpServletRequest原生的请求对象 ?  是因为文件上传以后总是要保存到某一个具体的目录下吧
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException, URISyntaxException {
        //这个根路径不是webapp所在的路径,因为这句话是在运行时执行的,那肯定是在web应用发布运行起来以后这句话才执行,
        // 所以这句话实际得到的是在我们自动发布以后————也就是out目录下的imooc_reader_Web_exploded它的实际路径(有的是在target文件夹中)。
        //一定要区分开开在运行时路径是out下面的imooc_reader_Web_exploded
        //得到上传目录
        String uploadPath = request.getServletContext().getResource("/").toURI().getPath() + "/upload/"; //加上toURI()后面 D:/project/Jave_learn/第26周 SSM开发社交网站
        //String uploadPath2 = request.getServletContext().getResource("").getPath()+"/upload/";  //D:/project/Jave_learn/第26周%20SSM开发社交网站
        //文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        //拓展名 (找到最后一次出现.的位置,然后将其余的字符进行截取,得到文件拓展名)
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //保存文件到upload目录
        file.transferTo(new File(uploadPath + fileName + suffix));
//        file.transferTo(new File("wei/chen.png")); //能正常保存图片

        System.out.println("这个file都有什么? " + file.getOriginalFilename());

        //作为文件上传时,要让wangEditor认为服务器处理成功还需要按它的格式返回结果。组织格式
        Map result = new HashMap();
        result.put("errno", 0);
        result.put("data", new String[]{"/upload/" + fileName + suffix});
        return result;
    }

    /**
     * 图书新增功能
     *
     * @param book
     * @return
     */
    @PostMapping("/create")
    @ResponseBody
    public Map createBook(Book book) {
        Map result = new HashMap();
        try {
            book.setEvaluationQuantity(0L);
            //如何把图片链接放到这里? 推荐使用Jsoup————java中html解析器(图片链接来自于提交的description中的第一张图片)
            Document doc = Jsoup.parse(book.getDescription());  //解析图书详情
            Element img = doc.select("img").first();//获取图书详情中第一个图片的元素对象
            String cover = img.attr("src");//获取当前这个元素的指定属性值
            book.setCover(cover);    //来自于description描述的第一张图片
            bookService.createBook(book);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            result.put("code", ex.getCode());
            result.put("code", ex.getCode());
        }
        return result;
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        //作为数据表格查询时,并不需要按类别、order进行排序,所以设置前两个设置为null
        IPage<Book> pageObject = bookService.paging(null, null, page, limit);
        //如果json序列化pageObject对象输出到客户端,layUI是无法识别的,因为它也要求了属于自己的规范。以下按layUI的格式进行书写
        Map result = new HashMap();
        result.put("code", 0);
        result.put("msg", "success");
        result.put("data", pageObject.getRecords());//当前页面数据
        result.put("count", pageObject.getTotal());//未分页时记录总数
        return result;

    }

    /**
     * 获取图书信息
     *
     * @param id 前台传过来的id
     * @return 返回从数据库中查询到的book信息, 交由前台进行显示
     */
    @GetMapping("/id/{id}")
    @ResponseBody
    public Map getBookInfo(@PathVariable("id") Long id) {
        System.out.println("id===================  " + id);
        Book book = bookService.selectById(id);
        Map result = new HashMap();

        result.put("code", 0);
        result.put("msg", "success");
        result.put("data", book);    //原来是这里把book对象放到了data中
        return result;
    }

    /**
     * 更新图书信息
     * 2022年9月13日11:46:36 这里我的想法是:前端post请求发过来的book对象已经包含了所有该填写的图书信息,而我只需要把这些信息更新到数据库就是。
     * 2022年9月13日11:54:11 测试成功,能正常更新图书信息  !!!酷
     * 2022年9月13日12:56:55 看了视频还是老师的做法比较严谨,个人还是考虑一下不周,这就是实际开发经验吧
     *
     * @param book 新图书数据
     * @return 更新后的数据
     */
    @PostMapping("/update")
    @ResponseBody
    public Map update(Book book) {
        //用book对象来接收前台传入的数据,但是对于这个book对象来说,不要直接将它进行更新,原因是这里边只包含了前台提交来的数据,关于其他
        // 的字段,比如评分、评分人数等这些他都是没有的,一旦对这个book直接进行更新,轻则程序报错重则数据产生混乱,这样会更危险。
        //那么一个有效的做法是: 根据传入的book编号,把原始数据库的对象先查询出来,然后在这个原始数据的基础上进行依次的赋值。
        //以后在实际开发时一旦涉及更新操作,第一反应应该是先查询原始的数据,在原始数据基础上进行对应属性的调整,这是正确的做法。
        // 决不能出现直接对某一个对象进行更新操作
        Map result = new HashMap();
        try {
            Book rawBook = bookService.selectById(book.getBookId());
            rawBook.setBookName(book.getBookName());
            rawBook.setSubTitle(book.getSubTitle());
            rawBook.setAuthor(book.getAuthor());
            rawBook.setCategoryId(book.getCategoryId());
            //如果图书封面发生了变化则需要更换
            Document doc = Jsoup.parse(book.getDescription());
            String cover = doc.select("img").first().attr("src");
            rawBook.setCover(cover);

            bookService.updateBook(book);
            result.put("code", 0);
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    //作为参数bookId不单是要把对应的图书信息进行删除,那么与之相关的数据还有评论信息和阅读状态这两项也是需要清除的,
    // 所以对于deleteBook来说,它底层实际影响到了多张表
    public Map delete(@PathVariable("id") Long bookId) {
        Map result = new HashMap();
        try {
            bookService.deleteBook(bookId);
            result.put("code", 0);
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

}
