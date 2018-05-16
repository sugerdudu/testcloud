package com.sixi.oaplatform.security;

import com.sixi.oaplatform.domain.model.EmployeeDetails;
import com.sixi.oaplatform.exception.auth.VerifyForbiddenException;
import com.sixi.oaplatform.security.token.EmployeeAuthToken;
import com.sixi.feignservice.user.feign.ManageService;
import com.sixi.feignservice.user.model.EmployeeResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 *
 * @author wangwei
 * @date 2018/3/29
 *
 * 自定义员工校验权限提供器
 * 真正校验逻辑
 * 我们现在是用户名、密码提供器
 */
@Component
@Slf4j
public class EmployeeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private ManageService manageService;

    /**
     * 自定义验证方式
     *
     * @param authentication 凭证
     * @return 用户详细
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = (String) authentication.getCredentials();

        log.info("检查用户名***密码是否正确");
        EmployeeResultDto employeeResultDto = manageService.checkOutNameAndPassword(name, password);

        if (employeeResultDto==null||employeeResultDto.getUserId()<=0){
            throw new VerifyForbiddenException("请检查账号密码");
        }

        EmployeeDetails employeeDetails = buildCustomUserDetails(name, password,employeeResultDto.getUserId(),employeeResultDto.getClass1Id(),employeeResultDto.getClass2Id());

        return new EmployeeAuthToken(employeeDetails);
    }

    /**
     * 是否支持该认真
     *
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    /**
     * 构造一个CustomUserDetails，简单，略去
     *
     * @param username 用户名
     * @param password 密码
     * @return EmployeeDetails
     */
    private EmployeeDetails buildCustomUserDetails(String username, String password,Integer userId,Integer class1Id,Integer class2Id) {

        return EmployeeDetails.builder().userName(username).password(password).userId(userId).class1Id(class1Id).class2Id(class2Id).build();
    }

}
