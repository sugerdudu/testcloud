package com.sixi.oaservice.userservice.mapper.oapg.user;

import com.sixi.oaservice.userservice.domain.dto.user.ManageRoleBatchInsert;
import com.sixi.oaservice.userservice.domain.model.oapg.ManageRoleRelation;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created with Mybatis Generator Plugin
 *
 * @author MiaoWoo
 * Created on 2018/04/20 02:48.
 */
@Repository
public interface ManageRoleRelationMapper extends Mapper<ManageRoleRelation> {

    /**
     * 批量插入
     *
     * @param manageRoleBatchInsert 员工角色批量信息
     * @return int
     */
    int insertBatch(List<ManageRoleBatchInsert> manageRoleBatchInsert);

}