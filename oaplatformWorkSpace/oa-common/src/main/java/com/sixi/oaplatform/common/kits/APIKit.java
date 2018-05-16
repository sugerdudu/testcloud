package com.sixi.oaplatform.common.kits;

import com.alibaba.fastjson.JSON;
import com.sixi.oaplatform.common.utils.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liren
 * 签名后调用远程API
 */
@Slf4j
@Component
public class APIKit {

    private String SignHeader = "Sixi-Signature";
    private String key = "SixiWxApp!@#$%";
    private String charset = "UTF-8";

    public ResultData post(String url, Object object) {
        return post(url, JSON.toJSONString(object));
    }

    public ResultData post(String url, Map<String, String> queryParas, Object object) {
        return post(url, queryParas, JSON.toJSONString(object));
    }

    public ResultData post(String url, Map<String, String> queryParas, String body) {
        Map<String, String> header = new HashMap<>(1);
        header.put("Content-Type", "application/json");
        header.put(SignHeader, EncryptKit.sign(body, key, charset));
        return HttpKit.post(url, queryParas, body, header);
    }

    public ResultData post(String url, String body) {
        Map<String, String> header = new HashMap<>(1);
        header.put("Content-Type", "application/json");
        header.put(SignHeader, EncryptKit.sign(body, key, charset));
        return HttpKit.post(url, body, header);
    }

    public ResultData get(String url) {
        return HttpKit.get(url);
    }

    public ResultData get(String url, Map<String, String> query) {
        Map<String, String> header = new HashMap<>(1);
        header.put(SignHeader, EncryptKit.sign(query, key, charset));
        return HttpKit.get(url, query, header);
    }

}