package com.sixi.oaplatform.config;

import com.sixi.oaplatform.common.config.SiXiSecurityProperties;
import com.sixi.oaplatform.security.EmployeeTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

/**
 * Token生成配置类
 *
 * @author wangwei
 */
@Configuration
public class TokenStoreConfig {

    /**
     * JDBC token生成方式
     *
     * @param dataSource 验证数据源
     * @return JdbcTokenStore
     * @see JdbcTokenStore
     * @deprecated
     */
    @Bean
    @ConditionalOnProperty(prefix = "sixi.security.oauth2",name = "tokenType",havingValue = "jdbc")
    public JdbcTokenStore tokenStore(DataSource dataSource){
        return new JdbcTokenStore(dataSource);
    }

    @Configuration
    @ConditionalOnProperty(prefix = "sixi.security.oauth2",name = "tokenType",havingValue = "redis")
    public static class RedisTokenConfig{

        @Autowired
        private RedisConnectionFactory redisConnectionFactory;

        /**
         * 生成redisTokenStore
         *
         * @return RedisTokenStore
         * @see RedisTokenStore
         */
        @Bean
        public TokenStore redisTokenStore(){

            return new RedisTokenStore(redisConnectionFactory);

        }

    }


    @Configuration
    @ConditionalOnProperty(prefix = "sixi.security.oauth2",name = "tokenType",havingValue = "jwt")
    public static class JwtTokenConfig{

        @Autowired
        private SiXiSecurityProperties siXiSecurityProperties;

        /**
         * JTW token生成方式
         *
         * @return jtwtoken
         * @see JwtTokenStore
         */
        @Bean
        public TokenStore jwtTokenStore(){
            return new JwtTokenStore(accessTokenConverter());
        }

        /**
         * jwt Token增强器
         *
         * @return jwt生成方式
         */
        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
            JwtAccessTokenConverter converter = new EmployeeTokenEnhancer();
            //设置JWT密钥
            converter.setSigningKey(siXiSecurityProperties.getOauth2().getJwtSecret());
            return converter;
        }

    }

}
