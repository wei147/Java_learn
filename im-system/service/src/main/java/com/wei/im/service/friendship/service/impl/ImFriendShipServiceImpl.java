package com.wei.im.service.friendship.service.impl;

import com.wei.im.common.ResponseVO;
import com.wei.im.service.friendship.dao.ImFriendShipEntity;
import com.wei.im.service.friendship.dao.mapper.ImFriendShipMapper;
import com.wei.im.service.friendship.model.req.AddFriendReq;
import com.wei.im.service.friendship.model.req.FriendDto;
import com.wei.im.service.friendship.model.req.ImportFriendShipReq;
import com.wei.im.service.friendship.model.resp.ImportFriendShipResp;
import com.wei.im.service.friendship.service.ImFriendShipService;
import com.wei.im.service.user.dao.ImUserDataEntity;
import com.wei.im.service.user.service.ImUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 实现类
 * @author: wei
 * @create: 2023-04-16 20:58
 **/
@Service
public class ImFriendShipServiceImpl implements ImFriendShipService {

    @Autowired
    ImFriendShipMapper imFriendShipMapper;

    @Autowired
    ImUserService imUserService;

    @Override
    public ResponseVO importFriendShip(ImportFriendShipReq req) {

        if (req.getFriendItem().size() > 100) {
            //todo 返回超出长度
        }

        ImportFriendShipResp resp = new ImportFriendShipResp();
        List<String> successId = new ArrayList<>();
        List<String> errorId = new ArrayList<>();
        for (ImportFriendShipReq.ImportFriendDto dto : req.getFriendItem()) {
            ImFriendShipEntity entity = new ImFriendShipEntity();

            // 把dto 里面的数据赋值给当前的对象
            BeanUtils.copyProperties(dto, entity);
            entity.setAppId(req.getAppId());
            entity.setFromId(req.getFromId());

            try {
                int insert = imFriendShipMapper.insert(entity);
                if (insert == 1) {
                    successId.add(dto.getToId());
                } else {
                    errorId.add(dto.getToId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                errorId.add(dto.getToId());
            }
        }
        resp.setSuccessId(successId);
        resp.setErrorId(errorId);
        return ResponseVO.successResponse(resp);
    }

    @Override
    public ResponseVO addFriend(AddFriendReq req) {
        ResponseVO<ImUserDataEntity> fromInfo = imUserService.getSingleUserInfo(req.getFromId(), req.getAppId());
        if (!fromInfo.isOk()) {
            return fromInfo;
        }

        ResponseVO<ImUserDataEntity> toInfo = imUserService.getSingleUserInfo(req.getFromId(), req.getAppId());
        if (!toInfo.isOk()) {
            return toInfo;
        }
        return null;
    }

    //真正添加好友的业务
    public ResponseVO doAddFriend(String fromId, FriendDto dto, Integer addId) {

    }
}
