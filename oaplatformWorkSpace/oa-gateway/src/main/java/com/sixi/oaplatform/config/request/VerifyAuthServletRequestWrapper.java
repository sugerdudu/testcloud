package com.sixi.oaplatform.config.request;

import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 *
 * @author wangwei
 * @date 2018/3/27
 *
 * 自定义的权限验证请求包装器，对请求修改
 */
public class VerifyAuthServletRequestWrapper extends HttpServletRequestWrapper {

    private byte[] bodies;


    public VerifyAuthServletRequestWrapper(HttpServletRequest request, byte[] bodies) {
        super(request);
        this.bodies = bodies;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ServletInputStreamWrapper(bodies);
    }

    @Override
    public int getContentLength() {
        return bodies.length;
    }

    @Override
    public long getContentLengthLong() {
        return bodies.length;
    }


}
