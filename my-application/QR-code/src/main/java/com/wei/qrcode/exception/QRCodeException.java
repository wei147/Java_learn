package com.wei.qrcode.exception;

/**
 * 统一异常
 */
//public class ImoocMallException extends Exception {
// 这里改成RuntimeException就不用在后续方法中抛出异常或者try catch了.(对于RuntimeException这样的Exception是可以不做额外处理的)
public class QRCodeException extends RuntimeException {
    private final Integer code;
    private final String message;

    //这里不写构造函数赋值的话,上边会报错,可能是和final这个关键字有关
    public QRCodeException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    //有更方便的办法 : 和刚才统一处理异常的思路一致,我们可以直接传入一个异常的枚举,
    // 然后在里面调用上一个构造函数,这样一来我们就可以用一个枚举来构造出异常了
    public QRCodeException(QRCodeExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
