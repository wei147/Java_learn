package com.imooc.mall.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * 生成二维码工具
 */
public class QRCodeGenerator {
    //为什么这里用 int 而其他时候用Integer? 因为这个是静态方法?
    public static void generateQRCodeImage(String text, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //BarcodeFormat.QR_CODE是格式
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        //MatrixToImageWriter从矩阵变图片
        MatrixToImageWriter.writeToPath(bitMatrix,"PNG",path);
    }

    public static void main(String[] args) {
        //从常量类里取的地址要启用Spring Boot,所以直接把地址写这就好了 Constant.FILE_UPLOAD_DIR.（注:传进来的地址需要包含完整的文件名）
        try {
            QRCodeGenerator.generateQRCodeImage("铁马冰河入梦来",350,350,
                    "D:/project/Jave_learn/mall-prepare-static/QR.png");
        } catch (WriterException | IOException e) {
            e.printStackTrace();
        }
    }
}
