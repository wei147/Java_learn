package com.imooc.oa.service.exception;

/**
 * 业务逻辑异常
 */
//为什么是继承运行时异常？ 因为在进行检查的时候 肯定是在程序运行时才去进行的（UserService.java checkLogin）
public class BusinessException extends RuntimeException{
    private String code; //异常编码，异常的以为标识（？有没有打错字了）
    private  String message; //异常的具体文本信息
    public BusinessException(String code,String msg){
        super(code+":"+msg);
        this.code = code;
        this.message = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
