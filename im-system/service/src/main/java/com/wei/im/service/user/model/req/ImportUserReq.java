package com.wei.im.service.user.model.req;

import com.wei.im.common.model.RequestBase;
import com.wei.im.service.user.dao.ImUserDataEntity;
import lombok.Data;

import java.util.List;


@Data
public class ImportUserReq extends RequestBase {

    private List<ImUserDataEntity> userData;


}
