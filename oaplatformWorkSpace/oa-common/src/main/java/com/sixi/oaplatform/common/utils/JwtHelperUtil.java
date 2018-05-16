package com.sixi.oaplatform.common.utils;

import com.sixi.oaplatform.common.config.SiXiSecurityProperties;
import com.sixi.oaplatform.common.domain.model.JwtInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.commons.codec.Charsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Jwt 生成 解析 工具类
 *
 * @author Administrator
 */
@Component
public class JwtHelperUtil {

    @Autowired
    private SiXiSecurityProperties siXiSecurityProperties;

    public Jws<Claims> parseToken(String token,String signingKey) throws Exception{
        return Jwts.parser().setSigningKey(signingKey.getBytes(Charsets.UTF_8)).parseClaimsJws(token);
    }

    public JwtInfo getInfoFromToken(String token,String signingKey) throws Exception{
        Jws<Claims> claimsJws = parseToken(token,signingKey);
        Claims body = claimsJws.getBody();

        return JwtInfo.builder().loginUser(Fn.toInt(body.get(siXiSecurityProperties.getToken().getUserInfo()))).build();
    }

}
