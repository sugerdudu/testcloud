package com.sixi.oaplatform.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

/**
 *
 * @author wangwei
 * @date 2018/3/29
 *
 * 员工model层
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetails implements UserDetails {

    private static final long serialVersionUID = 9017093533636893030L;

    private String userName;

    private String password;

    private List<Map<String,Object>> authorityList;

    private Integer class1Id;

    private Integer class2Id;

    private Integer userId;

    private String client;

    private List<Authorities> authorities;

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
