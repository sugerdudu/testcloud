package com.sixi.oaplatform.common.kits;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @author 喵♂呜
 * Created on 2017/12/20 10:51.
 */
public class EncryptKit {
    private static List<String> exclude = Arrays.asList("sign", "sign_type");

    /**
     * 生成待签名字符串
     */
    private static String buildSignStr(Map<String, String> param) {
        if (param.isEmpty()) { return ""; }
        StringBuilder str = new StringBuilder();
        filter(param).entrySet()
                     .stream()
                     .sorted(Map.Entry.comparingByKey())
                     .forEach(entry -> str.append(entry.getKey()).append("=")
                                          .append(entry.getValue()).append("&"));
        return str.toString().substring(0, str.length() - 1);
    }

    /**
     * 参数过滤
     */
    private static Map<String, String> filter(Map<String, String> param) {
        Map<String, String> temp = new HashMap<>();
        param.forEach((k, v) -> {
            if (!exclude.contains(k.toLowerCase()) && !StringUtils.isEmpty(v)) {
                temp.put(k, v);
            }
        });
        return temp;
    }

    /**
     * 签名字符串
     *
     * @param param
     *         需要签名的参数
     * @param key
     *         密钥
     * @param charset
     *         编码格式
     * @return 签名结果
     */
    public static String sign(Map<String, String> param, String key, String charset) {
        return sign(buildSignStr(param), key, charset);
    }

    /**
     * 签名字符串
     *
     * @param param
     *         需要签名的参数
     * @param key
     *         密钥
     * @param charset
     *         编码格式
     * @return 签名结果
     */
    public static boolean verify(Map<String, String> param, String key, String charset, String sign) {
        return verify(buildSignStr(param), key, charset, sign);
    }

    /**
     * 签名字符串
     *
     * @param text
     *         需要签名的字符串
     * @param key
     *         密钥
     * @param charset
     *         编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String charset) {
        return DigestUtils.sha256Hex(getContentBytes(text + key, charset));
    }

    /**
     * 效验参数签名结果
     *
     * @param text
     *         需要签名的字符串
     * @param key
     *         密钥
     * @param charset
     *         编码格式
     * @param sign
     *         签名结果
     * @return 签名结果
     */
    public static boolean verify(String text, String key, String charset, String sign) {
        return sign(text, key, charset).equals(sign);
    }

    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
}
