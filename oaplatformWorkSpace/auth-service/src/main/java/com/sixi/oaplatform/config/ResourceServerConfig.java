package com.sixi.oaplatform.config;

import com.sixi.oaplatform.security.handler.EmployeeLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * 资源服务管理器
 * 使得Spring Security filter通过请求中的OAuth2 token来验证请求
 *
 * @author wangwei
 * @date 2018/3/29
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Bean
    public EmployeeLogoutHandler logoutHandler(){
        return new EmployeeLogoutHandler();
    }

    /**
     * 自定义置基于web安全以针对特定http请求
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
            http
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                /*.and()
                    .requestMatchers()
                    .antMatchers("/**")*/
                .and()
                    .authorizeRequests()
                    .antMatchers("/","/home","/oauth")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    //.invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .addLogoutHandler(logoutHandler())
                    .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

}
