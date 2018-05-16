package com.sixi.oaplatform.common.exception;

import lombok.Data;

/**
 * 无效token异常
 *
 * @author wangwei
 */
@Data
public class InvalidTokenException extends RuntimeException {
    private static final long serialVersionUID = 5337028961625879811L;


    private Integer code;

    public InvalidTokenException() {
    }

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Integer code) {
        super(message);
        this.code = code;
    }

}
