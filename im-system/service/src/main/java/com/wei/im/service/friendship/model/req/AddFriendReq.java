package com.wei.im.service.friendship.model.req;

import com.wei.im.common.enums.FriendShipStatusEnum;
import com.wei.im.common.model.RequestBase;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @description:
 * @author: wei
 * @create: 2023-04-16 21:02
 **/

@Data
public class AddFriendReq extends RequestBase {

    @NotBlank(message = "formId不能为空")
    private String fromId;

    private FriendDto toItem;
}
