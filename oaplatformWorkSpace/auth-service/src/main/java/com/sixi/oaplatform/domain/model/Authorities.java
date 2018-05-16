package com.sixi.oaplatform.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * 权限集合
 *
 * @author wangwei
 * @date 2018/3/29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authorities implements GrantedAuthority {

    private static final long serialVersionUID = -8233329225090128297L;
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}
