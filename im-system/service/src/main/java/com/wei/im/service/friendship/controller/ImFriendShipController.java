package com.wei.im.service.friendship.controller;

import com.wei.im.common.ResponseVO;
import com.wei.im.service.friendship.model.req.AddFriendReq;
import com.wei.im.service.friendship.model.req.ImportFriendShipReq;
import com.wei.im.service.friendship.model.req.UpdateFriendReq;
import com.wei.im.service.friendship.service.ImFriendShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 控制器
 * @author: wei
 * @create: 2023-04-17 17:53
 **/
@RestController
@RequestMapping("v1/friendship")
public class ImFriendShipController {

    @Autowired
    ImFriendShipService imFriendShipService;

    //用于第三方服务,导入现有的系统的里面的数据。比如关系链数据
    @RequestMapping("/importFriendShip")
    public ResponseVO importFriendShip(@RequestBody @Validated ImportFriendShipReq req, Integer appId) {
        req.setAppId(appId);
        return imFriendShipService.importFriendShip(req);
    }

    //添加好友
    @RequestMapping("/addFriend")
    public ResponseVO addFriend(@RequestBody @Validated AddFriendReq req, Integer appId) {
        req.setAppId(appId);
        return imFriendShipService.addFriend(req);
    }

    // 更新好友
    @RequestMapping("/updateFriend")
    public ResponseVO updateFriend(@RequestBody @Validated UpdateFriendReq req, Integer appId) {
        req.setAppId(appId);
        return imFriendShipService.updateFriend(req);
    }
}
