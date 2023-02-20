package com.wei.qrcode.exception;

import com.wei.qrcode.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


/**
 * 处理统一异常的handler
 */
//这个ControllerAdvice注解的作用就是拦截这些异常单的
@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //目前有两种类型的异常需要拦截: 1.系统异常  2.业务异常

    @ExceptionHandler(Exception.class)  //异常的类型
    @ResponseBody
    public Object handleException(Exception e) {
        log.error("Default Exception : ", e);
        return ApiRestResponse.error(QRCodeExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(QRCodeException.class)
    @ResponseBody
    public Object handleImoocMallException(QRCodeException e) {
        log.error("ImoocMallException : ", e);
        //这里传进来的是什么就正常打印出去
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }

    //处理方法的参数不合规的异常(情况)
    @ExceptionHandler(MethodArgumentNotValidException.class)    //异常的类型
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException : ", e);
        return handleBindingResult(e.getBindingResult());
    }

    //在绑定出问题的时候,我们来把这个异常处理成一个返回的ApiRestResponse,,,
    private ApiRestResponse handleBindingResult(BindingResult result) {
        //把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {   //是否包含错误
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();   //getDefaultMessage 拿到错误信息
                list.add(message);
            }
            if (list.size() == 0) {
                return ApiRestResponse.error(QRCodeExceptionEnum.REQUEST_PARAM_ERROR);
            }
        }
        return ApiRestResponse.error(QRCodeExceptionEnum.REQUEST_PARAM_ERROR.getCode(), list.toString());
    }
}
