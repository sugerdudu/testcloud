package com.sixi.oaplatform.common.utils;

import lombok.Data;

/**
 * 小程序返回的请求结果
 * @param <T>
 * @author Administrator
 */
@Data
public class ResultData<T> {

    public static Integer STATUS_SUCCESS = 200;
    public static Integer STATUS_ERROR = 202;

    private int status;
    private String msg;
    private T data;

    public static ResultData error(String msg) {
        ResultData<Object> resultData = new ResultData<>();
        resultData.setMsg(msg);
        resultData.setStatus(STATUS_ERROR);
        resultData.setData(null);

        return resultData;
    }
}