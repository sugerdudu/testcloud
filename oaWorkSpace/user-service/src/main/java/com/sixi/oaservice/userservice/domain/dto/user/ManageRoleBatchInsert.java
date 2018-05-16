package com.sixi.oaservice.userservice.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 人物 角色 批量新增dto
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManageRoleBatchInsert {

    private Integer manageId;

    private Integer roleId;

    private Integer addUser;

    private Integer modifyUser;

}
