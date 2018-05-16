package com.sixi.oaservice.userservice.service;

import com.sixi.oaservice.userservice.domain.dto.EmployeeResultDto;

/**
 * 用户管理接口层
 *
 * @author Administrator
 */
public interface ManageService {

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 返回用户信息
     */
    EmployeeResultDto login(String username, String password);

}
