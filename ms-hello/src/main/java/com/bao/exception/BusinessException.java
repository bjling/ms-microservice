package com.bao.exception;


import com.bao.exception.constant.ExceptionConstants;
import com.bao.exception.constant.ExceptionLevelEnum;

/**
 * 业务处理异常基类 从BaseException继承
 */
public class BusinessException extends BaseException {

  private static final long serialVersionUID = -4011725163070471891L;

  public BusinessException(final String code, final String message, final ExceptionLevelEnum exceptionLevelEnum,
      Throwable cause) {
    super(ExceptionConstants.BUSINESS, code, message, exceptionLevelEnum, cause);
  }

  public BusinessException(final String code, final String message, final ExceptionLevelEnum exceptionLevelEnum) {
    super(ExceptionConstants.BUSINESS, code, message, exceptionLevelEnum);
  }
}
