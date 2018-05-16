package com.sixi.oaplatform.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.sixi.feignservice.user.feign.SyncManageService;
import com.sixi.oaplatform.common.config.SiXiSecurityProperties;
import com.sixi.oaplatform.common.constant.SymbolCommonConstant;
import com.sixi.oaplatform.common.domain.model.JwtInfo;
import com.sixi.oaplatform.common.utils.JwtAuthUtil;
import com.sixi.feignservice.user.feign.ManageService;
import com.sixi.feignservice.user.model.result.FunctionInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 权限上下文过滤器
 *
 * @author wangwei
 */
@Component
@Slf4j
public class AuthorityContextFilter extends ZuulFilter {


    @Value("${gate.ignore.startWith}")
    private String startWith;

    @Autowired
    private ZuulProperties zuulProperties;

    @Autowired
    private SyncManageService syncManageService;

    @Autowired
    private JwtAuthUtil jwtAuthUtil;

    @Autowired
    private SiXiSecurityProperties siXiSecurityProperties;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 前置过滤
     * @return String
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否需要过滤
     * 去配置中心查询自己配置不需要验证的接口
     *
     * @return boolean
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        //判断此请求路径是否需要过滤
        final String requestUri = request.getRequestURI().substring(zuulProperties.getPrefix().length());

        return !isStartWith(requestUri);
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        //******************开始验证用户名是否有此url的权限***********************//

        JwtInfo jwtUser;
        try {
            jwtUser = getJwtUser(request);
        } catch (Exception e) {
            e.printStackTrace();
            setFailedRequest(JSON.toJSONString(e.getMessage()),401);
            return null;
        }

        checkoutPermission(ctx,jwtUser);
        return null;
    }

    private JwtInfo getJwtUser(HttpServletRequest request) throws Exception {
        String authToken = request.getHeader(siXiSecurityProperties.getToken().getHeaderName());
        if (StringUtils.isEmpty(authToken)) {
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if (cookie.getName().equals(siXiSecurityProperties.getToken().getHeaderName())) {
                        authToken = cookie.getValue();
                    }
                }
            }
        }
        authToken = StringUtils.substringAfter(authToken,siXiSecurityProperties.getToken().getBearer());

        return jwtAuthUtil.getInfoFromToken(authToken);
    }

    /**
     * 检查是否这个人是否有该url权限
     *
     * @param request requestContext
     * @param jwtInfo 用户信息
     */
    private void checkoutPermission(RequestContext request , JwtInfo jwtInfo){
        List<FunctionInfo> functionList = syncManageService.getFunctionByUserId(jwtInfo.getLoginUser());

        HttpServletRequest servletRequest = request.getRequest();
        String requestURL = servletRequest.getRequestURL().toString();
        String method = servletRequest.getMethod();

        Set<FunctionInfo> functionInfoSet = functionList.stream().filter(f -> antPathMatcher.match(f.getUrl(), requestURL) && f.getMethodEngName().equalsIgnoreCase(method)).collect(Collectors.toSet());

        if (functionInfoSet==null || functionInfoSet.size()<=0) {
            setFailedRequest(JSON.toJSONString("你没有此权限".getBytes(Charset.forName("utf-8"))), 403);
            return;
        }

        setCurrentUserInfo(request,jwtInfo,functionInfoSet);
    }

    /**
     * URI是否以什么打头
     *
     * @param requestUri 请求的uri
     * @return boolean
     */
    private boolean isStartWith(String requestUri) {
        for (String s : startWith.split(SymbolCommonConstant.COMMA)) {
            if (requestUri.startsWith(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 网关抛异常
     *
     * @param body 失败的消息
     * @param code 失败的编码
     */
    private void setFailedRequest(String body, int code) {
        log.debug("Reporting error ({}): {}", code, body);
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.setResponseStatusCode(code);
        if (ctx.getResponseBody() == null) {
            ctx.setResponseBody(body);
            ctx.setSendZuulResponse(false);
        }
    }

    private void setCurrentUserInfo(RequestContext requestContext , JwtInfo jwtInfo, Collection<FunctionInfo> functionInfoSet){
        //requestContext.addZuulRequestHeader("userId",jwtInfo.getLoginUser().toString());
        //requestContext.addZuulRequestHeader("class1Id",userInfo.getClass1Id().toString());
        //requestContext.addZuulRequestHeader("class2Id",userInfo.getClass2Id().toString());
        FunctionInfo functionInfo = functionInfoSet.iterator().next();
        Set<Integer> progressSet = functionInfoSet.stream().map(FunctionInfo::getProgress).collect(Collectors.toSet());

        String progress = StringUtils.join(progressSet, SymbolCommonConstant.COMMA);
        //功能类型，是列表，是按钮，还是审核，还是其他
        requestContext.addZuulRequestHeader("functionType",functionInfo.getCode());
        //当前这个用户该url所拥有的进度
        requestContext.addZuulRequestHeader("progress",progress);
    }
}
