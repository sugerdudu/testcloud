package com.sixi.oaplatform.security;

import com.sixi.oaplatform.common.config.SiXiSecurityProperties;
import com.sixi.oaplatform.domain.model.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 额外信息添加
 * jwtToken增强器
 *
 * @author wangwei
 * @date 2018/3/29
 */
public class EmployeeTokenEnhancer extends JwtAccessTokenConverter implements Serializable {

    private static final long serialVersionUID = -408440996246403934L;

    @Autowired
    private SiXiSecurityProperties siXiSecurityProperties;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken,
                                     OAuth2Authentication authentication) {
        EmployeeDetails userDetails = (EmployeeDetails) authentication.getPrincipal();
        authentication.getUserAuthentication().getPrincipal();
        Map<String, Object> info = new HashMap<>();



        info.put(siXiSecurityProperties.getToken().getUserInfo(),userDetails.getUserId());

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);

        //enhancedToken.getAdditionalInformation().put(tokenSegClient, userDetails.getClient());

        return super.enhance(customAccessToken, authentication);
    }

}
