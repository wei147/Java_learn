package com.wei.qrcode.Service.Impl;

import com.google.zxing.WriterException;
import com.wei.qrcode.Service.QRCodeService;
import com.wei.qrcode.common.Constant;
import com.wei.qrcode.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("qrCodeService")
public class QRCodeServiceImpl implements QRCodeService {

    //配置生成二维码中的ip信息
    @Value("${file.upload.ip}")
    String ip;

    @Override
    public String qrcode(String text) {
        //在生成二维码之前,要知道存入的url是什么。这个url是包含http、ip还有地址在内的,最后再跟上订单号,就是完整的地址了
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //生成时间戳
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd__hhmmss");
        String timeStamp = dateFormat.format(new Date());

        String address = ip + ":" + request.getLocalPort(); //拿到端口号拼接ip信息
//        String qrCodeUrl = "http://" + address + "/qrCode?timeStamp=" + timeStamp;
        try {
            QRCodeGenerator.generateQRCodeImage(text, 350, 350, Constant.FILE_UPLOAD_DIR + timeStamp + ".png");
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
        String pngAddress = "http://" + address + "/images/" + timeStamp + ".png";
        //有了端口号之后,我们还需要一个ip信息,这个ip信息是需要我们自己来配置的,而不能简单的从request去拿,因为我们的服务上线之后它其实并不是直接暴露给外面的,
        // 无论是阿里云还是腾讯云,他们这个链接都会经过多层的转发,比如说防火墙之类的才回到我们的机器上,那么这个时候这个ip从request中拿其实是经过转发之后的内网ip,那这个是不对的,
        //所以我们应该在这里配置一下可以访问的ip   @Value("${file.upload.ip}")

        //返回的结果是这个图片文件应该通过什么url可以访问到
        return pngAddress;
    }
}
