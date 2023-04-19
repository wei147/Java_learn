package com.wei.im.service.friendship.controller;

import com.wei.im.common.ResponseVO;
import com.wei.im.service.friendship.model.req.*;
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

    // 删除好友
    @RequestMapping("/deleteFriend")
    public ResponseVO deleteFriend(@RequestBody @Validated DeleteFriendReq req, Integer appId) {
        req.setAppId(appId);
        return imFriendShipService.deleteFriend(req);
    }

    // 删除所有好友
    @RequestMapping("/deleteAllFriend")
    public ResponseVO deleteAllFriend(@RequestBody @Validated DeleteFriendReq req, Integer appId) {
        req.setAppId(appId);
        return imFriendShipService.deleteAllFriend(req);
    }

    // 获取所有好友
    @RequestMapping("/getAllFriendShip")
    public ResponseVO getAllFriendShip(@RequestBody @Validated GetAllFriendShipReq req, Integer appId) {
        req.setAppId(appId);
        return imFriendShipService.getAllFriendShip(req);
    }

    // 获取指定好友
    @RequestMapping("/getRelationReq")
    public ResponseVO getRelationReq(@RequestBody @Validated GetRelationReq req, Integer appId) {
        req.setAppId(appId);
        return imFriendShipService.getRelationReq(req);
    }

    // 单方校验好友
    @RequestMapping("/checkFriendShip")
    public ResponseVO checkFriendShip(@RequestBody @Validated CheckFriendShipReq req, Integer appId) {
        req.setAppId(appId);
        return imFriendShipService.checkFriendShip(req);
    }
}
