package com.sixi.oaplatform.security.token;

import com.sixi.oaplatform.domain.model.EmployeeDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 *
 * 自定义的token验证方式
 *
 * @author wangwei
 * @date 2018/3/29
 */
public class EmployeeAuthToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 4747591732658310815L;

    private EmployeeDetails employeeDetails;

    /**
     * setAuthenticated
     * 设置为false时候表示不验证客户端，采用用户名 密码模式
     * @see org.springframework.security.authentication.UsernamePasswordAuthenticationToken
     *
     * @param employeeDetails
     */
    public EmployeeAuthToken(EmployeeDetails employeeDetails) {
        super(null);
        this.employeeDetails = employeeDetails;

        //当前登录是否已经验证过了
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return employeeDetails.getPassword();
    }

    @Override
    public Object getPrincipal() {
        return employeeDetails;
    }

}
