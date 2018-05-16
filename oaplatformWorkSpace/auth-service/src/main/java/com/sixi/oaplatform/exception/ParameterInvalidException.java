package com.sixi.oaplatform.exception;

/**
 * Created by wangwei on 2018/3/29.
 * 参数设置异常
 */
public class ParameterInvalidException extends RuntimeException {

    public ParameterInvalidException() {
        super();
    }

    public ParameterInvalidException(String message) {
        super(message);
    }

    public ParameterInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterInvalidException(Throwable cause) {
        super(cause);
    }

    protected ParameterInvalidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
