package com.wei.qrcode.controller;

import com.wei.qrcode.Service.QRCodeService;
import com.wei.qrcode.common.ApiRestResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class QRCodeController {
    /**
     * 生成二维码
     */

    @Resource
    QRCodeService qrCodeService;

    @GetMapping("/index")
    public ApiRestResponse qrcode(@RequestParam String text) {
        String pngAddress = qrCodeService.qrcode(text);
        return ApiRestResponse.success(pngAddress);
    }
}
