package com.wei.im.service.friendship.model.req;

import com.wei.im.common.model.RequestBase;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description:
 * @author: wei
 * @create: 2023-04-16 21:02
 **/

@Data
public class GetAllFriendShipReq extends RequestBase {

    @NotBlank(message = "用户Id不能为空")
    private String fromId;
}
