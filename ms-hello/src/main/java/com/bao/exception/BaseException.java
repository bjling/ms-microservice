package com.bao.exception;

import com.bao.exception.constant.ExceptionLevelEnum;
import lombok.Data;

@Data
public class BaseException extends RuntimeException {

    public BaseException(String message){
        super(message);
    }
    /**
     * 异常模块
     */
    public String module;
    /**
     * 异常代码
     */
    public String code;
    /**
     * 异常级别
     */
    public ExceptionLevelEnum exceptionLevelEnum;

    public BaseException(final String module, final String code, final String message,
                         final ExceptionLevelEnum exceptionLevelEnum, Throwable cause) {
        super(message,cause);
        this.setModule(module);
        this.setCode(code);
        this.setExceptionLevelEnum(exceptionLevelEnum);
    }

    public BaseException(final String module, final String code, final String message,
                         final ExceptionLevelEnum exceptionLevelEnum) {
        super(message);
        this.setModule(module);
        this.setCode(code);
        this.setExceptionLevelEnum(exceptionLevelEnum);
    }
}
