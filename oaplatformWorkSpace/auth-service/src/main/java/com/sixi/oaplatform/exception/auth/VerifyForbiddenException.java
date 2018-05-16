package com.sixi.oaplatform.exception.auth;

import com.sixi.oaplatform.exception.BaseException;

/**
 * Created by wangwei on 2018/3/29.
 */
public class VerifyForbiddenException extends BaseException {

    public VerifyForbiddenException(String message) {
        super(message, 401);
    }
}
