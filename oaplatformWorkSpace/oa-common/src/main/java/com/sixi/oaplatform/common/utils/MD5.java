package com.sixi.oaplatform.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.Map;


public class MD5 {

    /**
     * 密钥
     */
    private static String _SIGN_KEY;

    /**
     * 签名字符串
     *
     * @param text
     *         需要签名的字符串
     * @param key
     *         密钥
     * @param input_charset
     *         编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
        return DigestUtils.md5Hex(getContentBytes(text + key, input_charset));
    }

    /**
     * 签名字符串
     *
     * @param text
     *         需要签名的字符串
     * @param sign
     *         签名结果
     * @param key
     *         密钥
     * @param input_charset
     *         编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
        return DigestUtils.md5Hex(getContentBytes(text + key, input_charset)).equals(sign);
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

    /**
     * 生成签名
     *
     * @param param
     *         参数
     * @return 签名
     */
    public String buildSign(Map<String, String> param) {
        return MD5.sign(buildSignStr(param), _SIGN_KEY, "UTF-8");
    }

    /**
     * 生成待签名字符串
     */
    private String buildSignStr(Map<String, String> param) {
        StringBuilder str = new StringBuilder();
        param.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> str.append(entry.getKey()).append("=").append(entry.getValue()).append("&"));
        return str.toString().substring(0, str.length() - 1);
    }
}