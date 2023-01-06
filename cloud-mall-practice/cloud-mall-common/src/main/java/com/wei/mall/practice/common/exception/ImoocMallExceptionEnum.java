package com.wei.mall.practice.common.exception;

/**
 * 异常枚举
 */
public enum ImoocMallExceptionEnum {
    //业务异常
//    NEED_USER_NAME(10001, "用户名不能为空"),
//    NEED_PASS_WORD(10002, "密码不能为空"),
//    NEED_TOO_SHORT(10003, "密码长度不能小于6位"),
//    NAME_EXISTED(10004, "不允许重名"),
//    INSERT_FAILED(10005, "插入失败,请重试"),
//    WRONG_PASSWORD(10006, "账号或密码错误"),    //原 密码错误
//    NEED_LOGIN(10007, "用户未登录"),
//    UPDATE_FAILED(10008, "更新失败"),
//    NEED_ADMIN(10009, "无管理员权限"),
//    PARA_NOT_NULL(100010, "名字不能为空"),
//    CREATE_FAILED(100011, "新增失败"),
//    REQUEST_PARAM_ERROR(100012, "参数错误"),
//    DELETE_FAILED(100013, "删除失败"),
//    MKDIR_FAILED(100014, "文件夹创建失败"),
//    UPLOAD_FAILED(100015, "图片上传失败"),
//    NOT_SALE(100016, "商品状态不可售"),
//    NOT_ENOUGH(100017, "商品库存不足"),
//    CART_EMPTY(100018, "购物车已勾选的商品为空"),
//    NO_ENUM(100019, "未找到对应的枚举"),
//    NO_ORDER(100020, "订单不存在"),
//    NOT_YOUR_ORDER(100021, "订单不属于你"),
//    WRONG_ORDER_STATUS(100022, "订单状态不符"),

    REQUEST_PARAM_ERROR(10001, "参数错误"),
    NO_ENUM(10002, "未找到对应的枚举"),
    NEED_USER_NAME(10003, "需要用户名"),

    NEED_PASS_WORD(10004, "需要密码"),
    NEED_TOO_SHORT(10005, "密码长度不能小于6位"),
    NAME_EXISTED(10006, "用户名不能重名"),
    INSERT_FAILED(10007, "插入失败,请重试"),
    WRONG_PASSWORD(10008, "密码错误"),
    UPDATE_FAILED(10009, "更新个性签名失败"),
    NEED_LOGIN(100010, "请登录"),
    NEED_ADMIN(10011, "非管理员,无法操作"),
    CREATE_FAILED(10012, "创建失败"),
    DELETE_FAILED(10013, "删除失败"),
    MKDIR_FAILED(10014, "文件创建失败"),
    UPLOAD_FAILED(10015, "上传失败"),
    NOT_SALE(10016, "商品未上架"),
    NOT_ENOUGH(10017, "商品库存不足"),


    //系统异常
    SYSTEM_ERROR(20000, "系统异常");

    /**
     * 异常码
     */
    Integer code;

    /**
     * 异常信息
     */
    String msg;

    ImoocMallExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
