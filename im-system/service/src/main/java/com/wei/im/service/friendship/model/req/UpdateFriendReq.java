package com.wei.im.service.friendship.model.req;

import com.wei.im.common.model.RequestBase;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description:更新对象的请求类
 * @author: wei
 * @create: 2023-04-18 13:30
 **/
@Data
public class UpdateFriendReq extends RequestBase {


    @NotBlank(message = "formId不能为空")
    private String fromId;

    private FriendDto toItem;

}
