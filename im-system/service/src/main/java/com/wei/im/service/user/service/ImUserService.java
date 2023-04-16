package com.wei.im.service.user.service;

import com.wei.im.common.ResponseVO;
import com.wei.im.service.user.dao.ImUserDataEntity;
import com.wei.im.service.user.model.req.*;
import com.wei.im.service.user.model.resp.GetUserInfoResp;

public interface ImUserService {
    public ResponseVO importUser(ImportUserReq req);


    public ResponseVO<GetUserInfoResp> getUserInfo(GetUserInfoReq req);

    public ResponseVO<ImUserDataEntity> getSingleUserInfo(String userId , Integer appId);

    public ResponseVO deleteUser(DeleteUserReq req);

    public ResponseVO modifyUserInfo(ModifyUserInfoReq req);

    public ResponseVO login(LoginReq req);

}
