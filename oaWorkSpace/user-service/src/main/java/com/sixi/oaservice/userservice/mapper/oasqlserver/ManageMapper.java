package com.sixi.oaservice.userservice.mapper.oasqlserver;

import com.sixi.oaservice.userservice.domain.dto.EmployeeResultDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户管理mapper层
 *
 * @author Administrator
 */
@Repository
public interface ManageMapper {

    /**
     * 登录检查用户名及密码
     *
     * @param userName 用户名
     * @param password 密码
     * @return EmployeeResultDto
     * @see EmployeeResultDto
     */
    EmployeeResultDto checkOutNameAndPassword(@Param("userName") String userName, @Param("password") String password);


}
