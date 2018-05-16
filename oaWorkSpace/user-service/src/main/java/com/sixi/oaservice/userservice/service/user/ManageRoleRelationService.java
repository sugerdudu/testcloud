package com.sixi.oaservice.userservice.service.user;

import com.sixi.oaservice.userservice.domain.dto.user.ManageRoleBatchUpdate;

/**
 * ManageRoleRelationService接口
 * @author Administrator create on 2018/4/20
 */
public interface ManageRoleRelationService{

    /**
     * 批量增加员工角色信息
     *
     * @param manageRoleBatchUpdate 人物 角色批量更新信息
     * @param loginUser 当前登录人
     */
    void insertManageRoleBatch(ManageRoleBatchUpdate manageRoleBatchUpdate,Integer loginUser);

    /**
     * 批量删除员工角色对应关系
     *
     * @param manageRoleBatchUpdate 员工 角色信息
     */
    void deleteManageRole(ManageRoleBatchUpdate manageRoleBatchUpdate);
}