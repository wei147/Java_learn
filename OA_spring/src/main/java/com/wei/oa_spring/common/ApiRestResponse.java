package com.wei.oa_spring.common;

import com.wei.oa_spring.exception.OAExceptionEnum;

/**
 * 通用返回对象
 */
public class ApiRestResponse<T> {
    private Integer status;

    private String msg;

    //这里的T为泛型,这里的data对象很可能是不固定的,用泛型比较合适
    private T data;

    //状态正常是10000
    private static final int OK_CODE = 10000;

    //状态正常的情况下 msg 是success
    private static final String OK_MSG = "SUCCESS";

    //生成三个参数的构造函数
    public ApiRestResponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ApiRestResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    //为什么要建无参的构造函数?  意味我们不需要传递任何信息,即默认的信息,成功的时候(这里会调用有两个参数的构造方法)
    public ApiRestResponse() {
        this(OK_CODE, OK_MSG);
    }

    //这个方法返回一个通用的响应对象,成功的时候将会调用
    public static <T> ApiRestResponse<T> success() {
        //这里会去调用无参构造方法
        return new ApiRestResponse<>();
    }

    //这里是对上面success方法的重载
    public static <T> ApiRestResponse<T> success(T result) {
        ApiRestResponse<T> response = new ApiRestResponse<>();
        //response中要把response放进去就要set方法(于是生成get和set方法)
        response.setData(result);
        //这样一来,response不仅带上ok_code和ok_msg还带上了data
        return response;
    }

    //考虑到可能调用会失败,所以写一个失败的方法
    public static <T> ApiRestResponse<T> error(Integer code, String msg) {
        return new ApiRestResponse<>(code, msg);
    }

    //这是一个传入异常枚举的error方法
    public static <T> ApiRestResponse<T> error(OAExceptionEnum ex) {
        return new ApiRestResponse<>(ex.getCode(), ex.getMsg());
    }

    //为了打印的时候更清晰,再自动的生成一个toString方法,
    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static int getOkCode() {
        return OK_CODE;
    }

    public static String getOkMsg() {
        return OK_MSG;
    }
}

