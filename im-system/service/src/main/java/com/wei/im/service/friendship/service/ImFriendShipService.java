package com.wei.im.service.friendship.service;

import com.wei.im.common.ResponseVO;
import com.wei.im.service.friendship.model.req.AddFriendReq;
import com.wei.im.service.friendship.model.req.ImportFriendShipReq;

/**
 * 导入关系链的开发
 */
public interface ImFriendShipService {
    //导入朋友关系
    public ResponseVO importFriendShip(ImportFriendShipReq req);

    // 添加好友接口
    public ResponseVO addFriend(AddFriendReq req);
}
