package com.sixi.oaplatform.config.exception;

import com.sixi.oaplatform.exception.AuthWebResponseExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * 异常处理配置类
 *
 * @author wangwei
 * @date 2018/3/29
 */
@Configuration
public class ResponseExceptionConfig {

    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator(){
        return new AuthWebResponseExceptionTranslator();
    }

}
