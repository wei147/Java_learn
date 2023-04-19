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
public class ImportFriendShipReq extends RequestBase {

    @NotBlank(message = "fromId不能为空")
    private String fromId;

    private List<ImportFriendDto> friendItem;

    @Data
    public static class ImportFriendDto {
        private String toId;
        private String remark;
        private String addSource;
        // 好友状态设置默认值 不是好友
        private Integer status = FriendShipStatusEnum.FRIEND_STATUS_NO_FRIEND.getCode();
        // 拉黑状态设置默认值 正常(非拉黑)
        private Integer black = FriendShipStatusEnum.BLACK_STATUS_NORMAL.getCode();
    }
}
