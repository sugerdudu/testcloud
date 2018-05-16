package com.sixi.oaplatform.config;

import com.sixi.oaplatform.common.config.SiXiSecurityProperties;
import com.sixi.oaplatform.filter.ClientIdHeaderFilter;
import com.sixi.oaplatform.security.EmployeeAuthTokenServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wangwei
 * @date 2018/3/28
 *
 * 自定义的权限配置服务类
 * 授权服务器
 */
@Configuration
@EnableAuthorizationServer
public class MyAuthConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private SiXiSecurityProperties siXiSecurityProperties;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    @Autowired
    private ClientIdHeaderFilter clientIdHeaderFilter;

    @Autowired(required = false)
    private TokenStore jwtTokenStore;

    @Autowired(required = false)
    private JwtAccessTokenConverter accessTokenConverter;

    /**
     * 第三方信息源存储在JDBC数据库
     *
     * @param dataSource 数据源
     * @return JdbcClientDetailsService
     * @deprecated 更改为内存第三方信息 inmemory
     */
    //@Bean
    /*public JdbcClientDetailsService clientDetailsService(DataSource dataSource){
        return new JdbcClientDetailsService(dataSource);
    }*/


    /**
     * 对应于配置AuthorizationServer安全认证的相关信息，
     * 创建ClientCredentialsTokenEndpointFilter核心过滤器
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //用来配置令牌端点(Token Endpoint)的安全约束.
        security
                //允许表单认证
                //.allowFormAuthenticationForClients()
                //是否验证后获取
                .checkTokenAccess("isAuthenticated()")
                //需要验证才能获取加密串
                .tokenKeyAccess("denyAll()")
                //添加请求头过滤器(自动生成第三方应用请求头)
                .addTokenEndpointAuthenticationFilter(clientIdHeaderFilter);
    }

    /**
     * 配置OAuth2的客户端相关信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.
                //第三方应用存储位置
                //withClientDetails(clientDetailsService(dataSource));
                inMemory()
                .withClient(siXiSecurityProperties.getClient().getClientId())
                .secret(siXiSecurityProperties.getClient().getClientSecret())
                .authorizedGrantTypes("password","refresh_token")
                .scopes(siXiSecurityProperties.getClient().getScope())
                .accessTokenValiditySeconds(siXiSecurityProperties.getToken().getAccessTokenSeconds())
                .refreshTokenValiditySeconds(siXiSecurityProperties.getToken().getRefreshTokenSeconds());
    }

    /**
     * 配置身份认证器，配置认证方式，
     * TokenStore，TokenGranter，OAuth2RequestFactory
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
        endpoints.authenticationManager(authenticationManager)
                 //令牌
                 //.tokenStore(tokenStore(dataSource))
                 .tokenStore(jwtTokenStore)
                 //配置令牌服务
                 //.tokenServices(authorizationServerTokenServices())
                 //用户信息处理，因为我们自定义了登录验证不需要
                 //.userDetailsService(employeeService)
                 //异常处理器
                 .exceptionTranslator(webResponseExceptionTranslator);

        if (accessTokenConverter != null) {
            //token增强链
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancerList = new ArrayList<>();
            enhancerList.add(accessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(enhancerList);

            //token的生成方式
            endpoints
                    .tokenEnhancer(tokenEnhancerChain)
                    .accessTokenConverter(accessTokenConverter);
        }
    }


    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        EmployeeAuthTokenServices employeeAuthTokenServices = new EmployeeAuthTokenServices();
        //employeeAuthTokenServices.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
        //employeeAuthTokenServices.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
        employeeAuthTokenServices.setTokenStore(jwtTokenStore);
        employeeAuthTokenServices.setSupportRefreshToken(true);
        employeeAuthTokenServices.setReuseRefreshToken(true);
        //employeeAuthTokenServices.setClientDetailsService(clientDetailsService(dataSource));
        employeeAuthTokenServices.setAccessTokenEnhancer(accessTokenConverter);

        return employeeAuthTokenServices;
    }

}
