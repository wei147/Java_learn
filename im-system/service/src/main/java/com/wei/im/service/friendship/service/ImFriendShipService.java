package com.wei.im.service.friendship.service;

import com.wei.im.common.ResponseVO;
import com.wei.im.service.friendship.model.req.*;

/**
 * 导入关系链的开发
 */
public interface ImFriendShipService {
    //导入朋友关系
    public ResponseVO importFriendShip(ImportFriendShipReq req);

    // 添加好友接口
    public ResponseVO addFriend(AddFriendReq req);

    // 修改好友接口
    public ResponseVO updateFriend(UpdateFriendReq req);

    // 删除好友接口
    public ResponseVO deleteFriend(DeleteFriendReq req);

    // 删除所有好友接口
    public ResponseVO deleteAllFriend(DeleteFriendReq req);

    // 获取所有好友
    public ResponseVO getAllFriendShip(GetAllFriendShipReq req);

    // 获取指定的好友
    public ResponseVO getRelationReq(GetRelationReq req);

    // 校验好友
    public ResponseVO checkFriendShip(CheckFriendShipReq req);
}
