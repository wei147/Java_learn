package com.wei.im.service.user.model.req;

import com.wei.im.common.model.RequestBase;
import lombok.Data;

import java.util.List;


@Data
public class GetUserInfoReq extends RequestBase {

    private List<String> userIds;


}
