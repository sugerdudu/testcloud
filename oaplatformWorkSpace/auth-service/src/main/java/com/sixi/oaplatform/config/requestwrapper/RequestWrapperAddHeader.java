package com.sixi.oaplatform.config.requestwrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * 添加头文件信息
 * @author wangwei
 * @date 2018/3/30
 */
public class RequestWrapperAddHeader extends HttpServletRequestWrapper {


    private Set<String> names = new HashSet<>();

    private Map<String,String> headers = new HashMap<>();

    private Map<String,String[]> params = new HashMap<>();

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public RequestWrapperAddHeader(HttpServletRequest request) {
        super(request);
        this.params.putAll(request.getParameterMap());
        Enumeration<String> headerNames = super.getHeaderNames();
        while (headerNames.hasMoreElements()){
            names.add(headerNames.nextElement());
        }
    }

    public RequestWrapperAddHeader(HttpServletRequest request, Map<String, Object> params) {
        super(request);
        addparameters(params);
    }

    /**
     * 添加额外参数
     *
     * @param extraParams
     */
    public void addparameters(Map<String,Object> extraParams){
        for (Map.Entry<String, Object> entry : extraParams.entrySet()) {
            addParameter(entry.getKey(),entry.getValue());
        }
    }

    public void addParameter(String name, Object value){
        if (value != null){
            if (value instanceof String[]){
                params.put(name,(String[])value);
            } else if (value instanceof String){
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)} );
            }
        }
    }

    public void addHeader(String name, String value){
        headers.put(name,value);
        names.add(name);
    }

    @Override
    public String getHeader(String name) {
        if (headers.containsKey(name)){
            return headers.get(name);
        }else{
            return super.getHeader(name);
        }
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(names);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        if (params!=null&&params.size()>0){
            return params;
        }
        return super.getParameterMap();
    }

    @Override
    public Enumeration<String> getParameterNames() {
        if (params!=null&&params.size()>0){
            return Collections.enumeration(params.keySet());
        }
        return super.getParameterNames();
    }

    @Override
    public String[] getParameterValues(String name) {
        if (params!=null&&params.keySet().contains(name)){
            return params.get(name);
        }
        return super.getParameterValues(name);
    }
}
