package com.wei.im.service.friendship.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wei.im.common.ResponseVO;
import com.wei.im.common.enums.FriendShipErrorCode;
import com.wei.im.common.enums.FriendShipStatusEnum;
import com.wei.im.service.friendship.dao.ImFriendShipEntity;
import com.wei.im.service.friendship.dao.mapper.ImFriendShipMapper;
import com.wei.im.service.friendship.model.req.*;
import com.wei.im.service.friendship.model.resp.ImportFriendShipResp;
import com.wei.im.service.friendship.service.ImFriendShipService;
import com.wei.im.service.user.dao.ImUserDataEntity;
import com.wei.im.service.user.service.ImUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            return ResponseVO.errorResponse(FriendShipErrorCode.IMPORT_SIZE_BEYOND);
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
        return this.doAddFriend(req.getFromId(), req.getToItem(), req.getAppId());
    }


    @Override
    public ResponseVO updateFriend(UpdateFriendReq req) {
        ResponseVO<ImUserDataEntity> fromInfo = imUserService.getSingleUserInfo(req.getFromId(), req.getAppId());
        if (!fromInfo.isOk()) {
            return fromInfo;
        }
        ResponseVO<ImUserDataEntity> toInfo = imUserService.getSingleUserInfo(req.getFromId(), req.getAppId());
        if (!toInfo.isOk()) {
            return toInfo;
        }
        return this.doUpdate(req.getFromId(), req.getToItem(), req.getAppId());
    }

    @Override
    public ResponseVO deleteFriend(DeleteFriendReq req) {
        QueryWrapper<ImFriendShipEntity> query = new QueryWrapper<>();
        query.eq("app_id", req.getAppId());
        query.eq("from_id", req.getFromId());
        query.eq("to_id", req.getToId());
        ImFriendShipEntity fromItem = imFriendShipMapper.selectOne(query);
        if (fromItem == null) {
            // todo 返回不是好友提示
        }else {
            // 这里只有是好友才能删除
            if (fromItem.getStatus() == FriendShipStatusEnum.FRIEND_STATUS_NORMAL.getCode()) {
                // todo 执行删除操作
                ImFriendShipEntity entity = new ImFriendShipEntity();
                // 设置为删除状态
                entity.setStatus(FriendShipStatusEnum.FRIEND_STATUS_DELETE.getCode());
                imFriendShipMapper.update(entity,query);
            }else {
                // todo 返回已被删除

            }
        }
        return ResponseVO.successResponse();
    }

    @Override
    public ResponseVO deleteAllFriend(DeleteFriendReq req) {
        return null;
    }

    //真正添加好友的业务
    @Transactional
    public ResponseVO doUpdate(String fromId, FriendDto dto, Integer appId) {
        UpdateWrapper<ImFriendShipEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().set(ImFriendShipEntity::getAddSource, dto.getAddSource())
                .set(ImFriendShipEntity::getExtra, dto.getExtra())
                .set(ImFriendShipEntity::getRemark, dto.getRemark())
                .eq(ImFriendShipEntity::getAppId, appId)
                .eq(ImFriendShipEntity::getFromId, fromId)
                .eq(ImFriendShipEntity::getToId, dto.getToId());

        imFriendShipMapper.update(null, updateWrapper);
        return ResponseVO.successResponse();
    }

    //真正添加好友的业务
    @Transactional
    public ResponseVO doAddFriend(String fromId, FriendDto dto, Integer appId) {
        // A-B
        // Friend表插入A和B两条记录
        // 查询是否有记录存在,如果存在则判断状态,如果是已添加,则提示已添加,如果是未添加,则修改状态
        QueryWrapper<ImFriendShipEntity> query = new QueryWrapper<>();
        query.eq("app_id", appId);
        query.eq("from_id", fromId);
        query.eq("to_id", dto.getToId());
        ImFriendShipEntity fromItem = imFriendShipMapper.selectOne(query);
        if (fromItem == null) {
            // 走添加逻辑
            fromItem = new ImFriendShipEntity();
            BeanUtils.copyProperties(dto, fromItem);
            fromItem.setStatus(FriendShipStatusEnum.FRIEND_STATUS_NORMAL.getCode());
            fromItem.setCreateTime(System.currentTimeMillis());
            int insert = imFriendShipMapper.insert(fromItem);
            if (insert != 1) {
                // todo 返回添加好友失败
                return ResponseVO.errorResponse(FriendShipErrorCode.ADD_FRIEND_ERROR);
            }
        } else {
            // 如果存在则判断状态,如果是已添加,则提示已添加,如果是未添加,则修改状态
            if (fromItem.getStatus() == FriendShipStatusEnum.FRIEND_STATUS_NORMAL.getCode()) {
                // todo 返回已添加好友
                return ResponseVO.errorResponse(FriendShipErrorCode.TO_IS_YOUR_FRIEND);
            }
            // 如果是未添加,则修改状态
            //FRIEND_STATUS_NO_FRIEND 如果不是好友
            else{
                ImFriendShipEntity update = new ImFriendShipEntity();
                // 如果添加方式不为空的话。
                if (StringUtils.isNotBlank(dto.getAddSource())) {
                    update.setAddSource(dto.getAddSource());
                }
                if (StringUtils.isNotBlank(dto.getRemark())) {
                    update.setRemark(dto.getRemark());
                }
                if (StringUtils.isNotBlank(dto.getExtra())) {
                    update.setExtra(dto.getExtra());
                }
                // 如果不是好友设置为添加状态
                update.setStatus(FriendShipStatusEnum.FRIEND_STATUS_NORMAL.getCode());
                int result = imFriendShipMapper.update(update, query);
                if (result != 1) {
                    // todo 返回更新失败 (添加好友失败)
                    return ResponseVO.errorResponse(FriendShipErrorCode.ADD_FRIEND_ERROR);
                }
            }
        }
        return ResponseVO.successResponse();
    }
}
