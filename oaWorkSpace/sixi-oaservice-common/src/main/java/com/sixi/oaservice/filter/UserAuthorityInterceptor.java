package com.sixi.oaservice.filter;

import com.sixi.oaservice.annotation.IgnoreAuthToken;
import com.sixi.oaservice.context.PrincipalBaseHandler;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户鉴权过滤器
 * 每个微服务需要的
 *
 * @author wangwei
 */
public class UserAuthorityInterceptor extends HandlerInterceptorAdapter {

    private String tokenHeader;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        IgnoreAuthToken annotation = handlerMethod.getBeanType().getAnnotation(IgnoreAuthToken.class);
        if (annotation == null){
            annotation = handlerMethod.getMethod().getAnnotation(IgnoreAuthToken.class);
        }
        if (annotation != null){
            return super.preHandle(request,response,handler);
        }
        String token = request.getHeader(tokenHeader);
        if (StringUtils.isEmpty(token)) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(tokenHeader)) {
                        token = cookie.getValue();
                    }
                }
            }
        }

        //*******************验证token的合法性*************************//

        PrincipalBaseHandler.setClass1Id(1);
        PrincipalBaseHandler.setClass2Id(1);
        PrincipalBaseHandler.setUserID(1);
        PrincipalBaseHandler.setToken("1");
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        //取消当前线程的参数
        PrincipalBaseHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }
}
