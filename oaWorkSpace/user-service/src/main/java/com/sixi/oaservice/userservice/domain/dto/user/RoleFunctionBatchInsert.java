package com.sixi.oaservice.userservice.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 角色功能批量新增dto
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleFunctionBatchInsert {

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 功能id
     */
    private Integer functionId;

    /**
     * 添加人
     */
    private Integer addUser;

    /**
     * 修改人
     */
    private Integer modifyUser;
}
