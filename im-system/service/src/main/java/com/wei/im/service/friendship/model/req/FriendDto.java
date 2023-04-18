package com.wei.im.service.friendship.model.req;

import lombok.Data;

/**
 * @description: 包含好友基础信息
 * @author: wei
 * @create: 2023-04-17 18:20
 **/
@Data
public class FriendDto {

    private String toId;

    //备注
    private String remark;

    // 添加来源
    private String addSource;

    // 拓展字段
    private String extra;

}
