package com.sixi.oaservice.filter;

import com.sixi.oaplatform.common.utils.Fn;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * debug输入
 *
 * @Author 艾翔
 * @Date 2017/8/26 15:27
 */
public class DebugInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        StringBuilder sb = (new StringBuilder("\nOAService2 action report -------- ")).append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date())).append(" ------------------------------\n");

        if (handler instanceof HandlerMethod) {
            Class cc = ((HandlerMethod) handler).getMethod().getDeclaringClass();
            StringBuilder str = new StringBuilder().append(cc.getName()).append(".(").append(cc.getSimpleName()).append(".java:1)");
            sb.append("Controller  : ").append(cc.getName()).append(".(").append(cc.getSimpleName()).append(".java:1)");
            sb.append("\nMethod      : ").append(((HandlerMethod) handler).getMethod().getName()).append("\n");
        }
        String method=request.getMethod();

        String urlPath=request.getServletPath();
        if (urlPath != null) {
            sb.append("UrlPara     : ").append(urlPath).append("\n");
        }
        sb.append("method      : ").append(method).append("\n");

        //get请求
        if("GET".equals(method)){
            String getParam = Fn.toString(request.getQueryString());
            if(getParam.length()>0) {
                String[] getParamArr = getParam.split("&");
                StringBuilder paramStr = new StringBuilder("Param       : ");

                for (String s : getParamArr) {
                    paramStr.append(s).append("  ");
                }

                sb.append(paramStr);
                sb.append("\n");
            }
            //post请求
        }else if("POST".equals(method)){
            Map<String, String[]> map= request.getParameterMap();
            StringBuilder queryString = new StringBuilder("Param       : ");
            for (String key : map.keySet()) {
                String[] values = map.get(key);
                for (String value : values) {
                    queryString.append(key).append("=").append(value).append("  ");
                }
            }
            sb.append(queryString);
        }

        sb.append("\n----------------------------------------------------------------------------------\n");
        System.out.print(sb.toString());

    }
}
