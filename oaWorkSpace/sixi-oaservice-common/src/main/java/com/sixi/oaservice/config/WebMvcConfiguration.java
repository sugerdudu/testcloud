package com.sixi.oaservice.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sixi.oaplatform.common.kits.BeanKit;
import com.sixi.oaservice.filter.DebugInterceptor;
import com.sixi.oaservice.filter.UserAuthorityInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import java.nio.charset.Charset;
import java.util.List;

/**
 * 全局mvc配置类
 *
 * @author wangwei
 */
@Slf4j
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    /**
     * 返回值解析器
     *
     * @param returnValueHandlers
     */
    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
    }

    /**
     * 请求参数解析器
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(userAuthorityInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(debugInterceptor()).excludePathPatterns("/sys/**");
    }

    @Autowired
    public DebugInterceptor debugInterceptor(){
        return new DebugInterceptor();
    }

    @Bean
    public UserAuthorityInterceptor userAuthorityInterceptor(){
        return new UserAuthorityInterceptor();
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("MvcConfig.configureMessageConverters");
        //定义一个转换消息的对象
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

        //添加fastjson的配置信息 比如 ：是否要格式化返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();

        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastJsonConfig.setCharset(Charset.forName("utf-8"));
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullListAsEmpty);
        //在转换器中添加配置信息
        fastConverter.setFastJsonConfig(fastJsonConfig);

        //将转换器添加到converters中
        converters.add(fastConverter);
    }

    /**
     * 设置文件上传最大值
     * Tomcat 配置
     * // 100 MB
     */
    private int maxUploadSizeInMb = 100 * 1024 * 1024;

    @Bean
    public TomcatEmbeddedServletContainerFactory containerFactory() {
        return new TomcatEmbeddedServletContainerFactory() {
            @Override
            protected void customizeConnector(Connector connector) {
                super.customizeConnector(connector);
                if (connector.getProtocolHandler() instanceof AbstractHttp11Protocol) {
                    // if maxUploadSizeInMb = -1, accept unlimited bytes
                    ((AbstractHttp11Protocol) connector.getProtocolHandler()).setMaxSwallowSize(maxUploadSizeInMb);
                }
            }
        };
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        for (Converter<?, ?> converter : BeanKit.getListOfType(Converter.class)) {
            registry.addConverter(converter);
        }
        for (GenericConverter converter : BeanKit.getListOfType(GenericConverter.class)) {
            registry.addConverter(converter);
        }
        for (Formatter<?> formatter : BeanKit.getListOfType(Formatter.class)) {
            registry.addFormatter(formatter);
        }
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        /**
         * 文件上传配置
         * Spring 配置
         *
         * 单个文件最大大小 30 MB
         * 总数据大小 100 MB
         */
        int maxFileSizeInKB = 30 * 1024;
        int maxRequestSizeInKB = 100 * 1024;

        MultipartConfigFactory factory = new MultipartConfigFactory();
        /*设置单个文件*/
        factory.setMaxFileSize(maxFileSizeInKB + "KB");
        /*设置总数据*/
        factory.setMaxRequestSize(maxRequestSizeInKB + "KB");
        return factory.createMultipartConfig();
    }

}
