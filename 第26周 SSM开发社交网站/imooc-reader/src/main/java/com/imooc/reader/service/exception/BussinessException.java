package com.imooc.reader.service.exception;

/**
 * BussinessException 业务逻辑异常
 */
//与业务逻辑相关的异常  (Bussiness与Business)
public class BussinessException extends RuntimeException {
    private String code;
    private String msg;

    public BussinessException(String code, String msg) {
        //先调用super() 完成父类构造方法的调用 (父类构造方法继承自 RuntimeException)
        super(code + ":" + msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
