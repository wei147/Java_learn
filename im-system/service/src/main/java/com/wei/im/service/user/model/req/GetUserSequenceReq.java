package com.wei.im.service.user.model.req;

import com.wei.im.common.model.RequestBase;
import lombok.Data;

/**
 * @description:
 */
@Data
public class GetUserSequenceReq extends RequestBase {

    private String userId;

}
