package com.sixi.oaservice.userservice.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

/**
 * 人员角色匹配更新的数据
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManageRoleBatchUpdate {

    /**
     * 角色集合
     */
    private Collection<Integer> roleIds;

    /**
     * 用户id
     */
    private Integer manageId;

}
