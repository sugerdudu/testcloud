package com.sixi.oaplatform.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 四喜安全配置类
 *
 * @author wangwei
 */
@Component
@ConfigurationProperties(prefix = "sixi.security")
@Data
public class SiXiSecurityProperties {

    private SiXiSecurityOAuth2Properties oauth2;

    private SiXiSecurityTokenProperties token;

    private SiXiSecurityClientProperties client;

    /**
     * 四喜安全第三方配置类
     */
    @Data
    public static class SiXiSecurityClientProperties{

        /**
         * 第三方应用
         */
        private String clientId;

        /**
         * 第三方应用密钥
         */
        private String clientSecret;

        /**
         * client base64加密
         * 客户端标识符
         */
        private String base64ClientInfo;

        /**
         * 类型
         */
        private String grandType;

        /**
         * 权限范围
         */
        private String scope;

    }

    /**
     * 四喜安全token配置类
     */
    @Data
    public static class SiXiSecurityTokenProperties{

        /**
         * token刷新时间
         */
        private Integer refreshTokenSeconds = 300;

        /**
         * token过期时间
         */
        private Integer accessTokenSeconds = 100;

        /**
         * 头信息
         */
        private String headerName;

        /**
         * 用户token头信息
         */
        private String userInfo;

        /**
         * token前缀
         */
        private String bearer = "bearer ";
    }


    /**
     * 四喜安全oauth2配置类
     *
     * @author wangwei
     */
    @Data
    public static class SiXiSecurityOAuth2Properties {

        /**
         * token生成的类型
         */
        private String tokenType;

        /**
         * jwt密钥
         */
        private String jwtSecret;

    }

}
