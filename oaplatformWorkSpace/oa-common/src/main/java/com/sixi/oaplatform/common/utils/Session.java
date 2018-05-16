package com.sixi.oaplatform.common.utils;

import javax.servlet.http.HttpSession;

/**
 * session 管理
 * @author suger
 *
 */
public class Session {
	
	public static void removeSession(String key){
		getSession().removeAttribute(key);
	}
	
	public static void setSession(String key,Object val){
		getSession().setAttribute(key, val);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getSession(String key) {
        return (T) getSession().getAttribute(key);
    }
	
	public static HttpSession getSession() {
        return Web.getRequest().getSession();
    }
}
