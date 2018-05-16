package com.sixi.oaplatform.config;

import com.sixi.oaplatform.security.EmployeeAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * 鉴权信息管理器
 * 这里提供网站 用户名 密码的登录
 *
 * @author wangwei
 * @date 2018/3/29
 */
@Component
public class AuthenticationManagerConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    private EmployeeAuthenticationProvider employeeAuthenticationProvider;

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(employeeAuthenticationProvider);
    }

}
