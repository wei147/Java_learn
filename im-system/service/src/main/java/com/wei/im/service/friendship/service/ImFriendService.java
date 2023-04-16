package com.wei.im.service.friendship.service;

import com.wei.im.common.ResponseVO;
import com.wei.im.service.friendship.model.req.ImportFriendShipReq;

public interface ImFriendService {
    public ResponseVO importFriendShip(ImportFriendShipReq req);
}
