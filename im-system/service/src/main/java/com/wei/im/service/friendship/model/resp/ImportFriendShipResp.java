package com.wei.im.service.friendship.model.resp;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: wei
 * @create: 2023-04-17 17:43
 **/

@Data
public class ImportFriendShipResp {
    private List<String> successId;

    private List<String> errorId;
}
