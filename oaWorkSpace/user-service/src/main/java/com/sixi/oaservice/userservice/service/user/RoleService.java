package com.sixi.oaservice.userservice.service.user;

import com.sixi.feignservice.user.model.param.RoleInsertDto;
import com.sixi.oaservice.userservice.domain.model.oapg.Role;

import java.util.List;

/**
 * RoleService接口
 * @author Administrator create on 2018/4/17
 */
public interface RoleService{

    /**
     * 得到全部的角色
     *
     * @return List<Role>
     */
    List<Role> selectAll();

    /**
     * 由角色名得到唯一的角色信息
     *
     * @param name 角色名
     * @return role
     */
    Role selectRoleByName(String name);

    /**
     * 由名称或者编码得到角色
     *
     * @param name 名称
     * @param code 编码
     * @return 角色
     */
    Role selectRoleByNameOrCode(String name , String code);

    /**
     * 新增一条角色
     *
     * @param roleInsertDto 角色新增dto信息
     * @param loginUser 登录人id
     */
    void insertRole(RoleInsertDto roleInsertDto,Integer loginUser);

    /**
     * 得到一条角色信息
     *
     * @param id 主键
     * @return 角色
     */
    Role selectByPrimary(Integer id);

    /**
     * 更新角色
     *
     * @param roleInsertDto 更新的信息
     * @param loginUser 登录人
     */
    void updateRole(RoleInsertDto roleInsertDto,Integer loginUser);

    /**
     * 删除角色
     *
     * @param id 主键
     * @param loginUser 登录人
     */
    void deleteRole(Integer id,Integer loginUser);
}