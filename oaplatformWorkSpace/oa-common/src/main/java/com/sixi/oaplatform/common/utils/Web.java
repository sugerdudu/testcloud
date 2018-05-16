package com.sixi.oaplatform.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求 响应处理
 * @author suger
 *
 */
public class Web {
	public static void redirect(String path) throws IOException{
			getResponse().sendRedirect(path);
	}
	
    public static HttpServletResponse getResponse(){
    	return getRequestAttributes().getResponse();
    }
    
    public static HttpServletRequest getRequest(){
    	return getRequestAttributes().getRequest();
    }
    
    public static ServletRequestAttributes getRequestAttributes(){
    	return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
}
