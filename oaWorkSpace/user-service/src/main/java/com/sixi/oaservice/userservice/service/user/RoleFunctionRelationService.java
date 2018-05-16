package com.sixi.oaservice.userservice.service.user;

import com.sixi.feignservice.user.model.param.RoleFunctionAssignDto;
import com.sixi.oaservice.userservice.domain.model.oapg.RoleFunctionRelation;

import java.util.List;

/**
 * RoleFunctionRelationService接口
 * @author Administrator create on 2018/4/19
 */
public interface RoleFunctionRelationService{

    /**
     * 新增角色功能赋值
     *
     * @param roleFunctionAssignDto 角色功能信息
     * @param loginUser 登录人
     */
    void insertRoleFunction(RoleFunctionAssignDto roleFunctionAssignDto , Integer loginUser);

    /**
     * 删除角色 功能块对应关系
     *
     * @param roleFunctionAssignDto 角色功能块信息
     */
    void deleteRoleFunction(RoleFunctionAssignDto roleFunctionAssignDto );

    /**
     * 由角色id得到功能块id集合
     *
     * @param roleId 角色id
     * @return 功能块id集合
     */
    List<RoleFunctionRelation> selectFuncIdsByRoleId(Integer roleId);
}