package com.wei.im.service.friendship.service;

import com.wei.im.common.ResponseVO;
import com.wei.im.service.friendship.dao.ImFriendShipGroupEntity;
import com.wei.im.service.friendship.model.req.AddFriendShipGroupReq;
import com.wei.im.service.friendship.model.req.DeleteFriendShipGroupReq;

/**
 * @author: Chackylee
 * @description:
 **/
public interface ImFriendShipGroupService {

    public ResponseVO addGroup(AddFriendShipGroupReq req);

    public ResponseVO deleteGroup(DeleteFriendShipGroupReq req);

    public ResponseVO<ImFriendShipGroupEntity> getGroup(String fromId, String groupName, Integer appId);


}
