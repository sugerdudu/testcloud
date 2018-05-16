package com.sixi.oaservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Spring API 文档配置类
 *
 * @author Administrator
 */
@Configuration
public class Swagger2Configuration {

    /**
     * 初始化一个构建器
     *
     * @return Docket
     */
    @Bean
    public Docket buildDokcet(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sixi.oaservice"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 文档初始化信息
     *
     * @return ApiInfo
     */
    private ApiInfo buildApiInfo(){
        return new ApiInfoBuilder().title("微服务接口文档").description("大家尽情发挥").build();
    }

}
