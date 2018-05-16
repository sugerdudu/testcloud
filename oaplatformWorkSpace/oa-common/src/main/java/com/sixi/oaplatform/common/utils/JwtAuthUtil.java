package com.sixi.oaplatform.common.utils;

import com.sixi.oaplatform.common.config.SiXiSecurityProperties;
import com.sixi.oaplatform.common.domain.model.JwtInfo;
import com.sixi.oaplatform.common.exception.InvalidTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Jwt授权工具类
 *
 * @author Administrator
 */
@Component
public class JwtAuthUtil {

    @Autowired
    private SiXiSecurityProperties siXiSecurityProperties;

    @Autowired
    private JwtHelperUtil jwtHelperUtil;

    public JwtInfo getInfoFromToken(String token) throws Exception {
        try {
            return jwtHelperUtil.getInfoFromToken(token,siXiSecurityProperties.getOauth2().getJwtSecret());
        } catch (ExpiredJwtException ex){
            throw new InvalidTokenException("User token expired!");
        }catch (SignatureException ex){
            throw new InvalidTokenException("User token signature error!");
        }catch (IllegalArgumentException ex){
            throw new InvalidTokenException("User token is null or empty!");
        }
    }

}
