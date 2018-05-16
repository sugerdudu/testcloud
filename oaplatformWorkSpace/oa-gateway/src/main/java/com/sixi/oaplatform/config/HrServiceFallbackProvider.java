package com.sixi.oaplatform.config;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author wangwei
 * @date 2018/3/27
 *
 * 为网关指定回退 hystrix机制
 */
@Component
public class HrServiceFallbackProvider implements FallbackProvider {
    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
        return new MyDefaultClientHttp(cause);
    }

    @Override
    public String getRoute() {
        return null;
    }

    @Override
    public ClientHttpResponse fallbackResponse() {
        return new MyDefaultClientHttp(null);
    }

    class MyDefaultClientHttp implements ClientHttpResponse{


        private Throwable t;

        public MyDefaultClientHttp(Throwable t) {
            this.t = t;
        }

        @Override
        public HttpStatus getStatusCode() throws IOException {
            return HttpStatus.OK;
        }

        @Override
        public int getRawStatusCode() throws IOException {
            return HttpStatus.OK.value();
        }

        @Override
        public String getStatusText() throws IOException {
            return HttpStatus.OK.getReasonPhrase();
        }

        @Override
        public void close() {
        }

        @Override
        public InputStream getBody() throws IOException {
            if (t!=null && !StringUtils.isEmpty(t.getMessage())){
                return new ByteArrayInputStream(t.getMessage().getBytes());
            }
            return new ByteArrayInputStream("{\"error\":\"服务故障 请稍后重试!\"}".getBytes());
        }

        @Override
        public HttpHeaders getHeaders() {
            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

            return httpHeaders;
        }
    }
}
