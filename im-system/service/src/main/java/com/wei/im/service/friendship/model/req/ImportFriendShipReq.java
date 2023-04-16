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
public class ImportFriendShipReq extends RequestBase {

    @NotBlank(message = "formId不能为空")
    private String formId;

    private String friendItem;

    @Data
    public static class ImportFriendDto {
        private String toId;
        private String remark;
        private String addSource;
        private Integer status;
        private Integer black;
    }
}
