package com.sixi.oaplatform.filter;

import com.sixi.oaplatform.common.config.SiXiSecurityProperties;
import com.sixi.oaplatform.config.requestwrapper.RequestWrapperAddHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 访问资源管理器添加客户端id的头部验证
 *
 * @author wangwei
 * @date 2018/3/30
 */
@Slf4j
@Component
public class ClientIdHeaderFilter extends OncePerRequestFilter {

    @Autowired
    private SiXiSecurityProperties siXiSecurityProperties;

    /*@Override
    public void init(FilterConfig filterConfig) {
        log.info("初始化鉴权过滤器");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        log.info("鉴权过滤器正在执行");
        String header = httpServletRequest.getHeader(tokenHeader);

        if (StringUtils.isEmpty(header)&&httpServletRequest.getRequestURL().indexOf("oauth/token")>0){
            RequestWrapperAddHeader wrapperAddHeader = new RequestWrapperAddHeader(httpServletRequest);
            wrapperAddHeader.addHeader(tokenHeader,clientId);
            wrapperAddHeader.addParameter("scope",scope);
            wrapperAddHeader.addParameter("grant_type",mode);

            filterChain.doFilter(wrapperAddHeader,servletResponse);
        }else {

            filterChain.doFilter(servletRequest,servletResponse);
        }

    }*/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("鉴权过滤器正在执行");
        String header = request.getHeader(siXiSecurityProperties.getToken().getHeaderName());

        if (StringUtils.isEmpty(header)&&request.getRequestURL().indexOf("oauth/token")>0){
            RequestWrapperAddHeader wrapperAddHeader = new RequestWrapperAddHeader(request);
            wrapperAddHeader.addHeader(siXiSecurityProperties.getToken().getHeaderName(),siXiSecurityProperties.getClient().getBase64ClientInfo());
            wrapperAddHeader.addParameter("scope",siXiSecurityProperties.getClient().getScope());
            wrapperAddHeader.addParameter("grant_type",siXiSecurityProperties.getClient().getGrandType());

            filterChain.doFilter(wrapperAddHeader,response);
        }else {

            filterChain.doFilter(request,response);
        }
    }

    private void logHeaderName(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()){
            log.info("头文件名称:"+headerNames.nextElement());
        }
    }

    @Override
    public void destroy() {
        log.info("关闭鉴权过滤器");
    }
}
