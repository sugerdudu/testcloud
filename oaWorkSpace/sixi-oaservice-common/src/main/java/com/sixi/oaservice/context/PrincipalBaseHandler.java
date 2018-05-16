package com.sixi.oaservice.context;

import com.sixi.oaplatform.common.constant.PrincipalConstant;
import com.sixi.oaplatform.common.utils.Fn;

import java.util.HashMap;
import java.util.Map;

public class PrincipalBaseHandler {

    public static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key){
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static Integer getUserID(){
        Object value = get(PrincipalConstant.CONTEXT_KEY_USER_ID);
        return returnIntObjectValue(value);
    }

    public static String getUsername(){
        Object value = get(PrincipalConstant.CONTEXT_KEY_USERNAME);
        return returnObjectValue(value);
    }


    public static Integer getClass1Id(){
        Object value = get(PrincipalConstant.CONTEXT_KEY_CLASS1_ID);
        return returnIntObjectValue(value);
    }

    public static Integer getClass2Id(){
        Object value = get(PrincipalConstant.CONTEXT_KEY_CLASS2_ID);
        return returnIntObjectValue(value);
    }

    public static String getToken(){
        Object value = get(PrincipalConstant.CONTEXT_KEY_USER_TOKEN);
        return Fn.toString(value);
    }
    public static void setToken(String token){
        set(PrincipalConstant.CONTEXT_KEY_USER_TOKEN,token);
    }

    public static void setUserID(Integer userID){
        set(PrincipalConstant.CONTEXT_KEY_USER_ID,userID);
    }

    public static void setUsername(String username){
        set(PrincipalConstant.CONTEXT_KEY_USERNAME,username);
    }

    public static void setClass1Id(Integer class1Id){
        set(PrincipalConstant.CONTEXT_KEY_CLASS1_ID,class1Id);
    }

    public static void setClass2Id(Integer class2Id){
        set(PrincipalConstant.CONTEXT_KEY_CLASS1_ID,class2Id);
    }

    private static String returnObjectValue(Object value) {
        return value==null?null:value.toString();
    }

    private static Integer returnIntObjectValue(Object value) {
        if (value instanceof Integer){
            return Integer.valueOf(value.toString());
        }
        return 0;
    }

    public static void remove(){
        threadLocal.remove();
    }


}
