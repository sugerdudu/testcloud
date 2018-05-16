package com.sixi.oaservice.userservice.service.user.impl;

import com.sixi.oaservice.userservice.domain.dto.user.ManageRoleBatchInsert;
import com.sixi.oaservice.userservice.domain.dto.user.ManageRoleBatchUpdate;
import com.sixi.oaservice.userservice.domain.model.oapg.ManageRoleRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sixi.oaservice.userservice.service.user.ManageRoleRelationService;
import com.sixi.oaservice.userservice.mapper.oapg.user.ManageRoleRelationMapper;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * ManageRoleRelationService实现层
 * @author Administrator create on 2018/4/20
 */
@Service
@Transactional(transactionManager = "oaPg" , rollbackFor = Exception.class)
@Slf4j
public class ManageRoleRelationServiceImpl implements ManageRoleRelationService{

    @Autowired
    private ManageRoleRelationMapper manageRoleRelationMapper;

    @Override
    public void insertManageRoleBatch(ManageRoleBatchUpdate manageRoleBatchUpdate,Integer loginUser) {
        List<ManageRoleBatchInsert> manageRoleBatchInserts = new ArrayList<>();

        for (Integer roleId : manageRoleBatchUpdate.getRoleIds()) {
            ManageRoleBatchInsert manageRoleBatchInsert = ManageRoleBatchInsert.builder().addUser(loginUser).modifyUser(loginUser).manageId(manageRoleBatchUpdate.getManageId()).roleId(roleId).build();

            manageRoleBatchInserts.add(manageRoleBatchInsert);
        }

        //新增失败 抛异常
        if (manageRoleRelationMapper.insertBatch(manageRoleBatchInserts)<manageRoleBatchInserts.size()){

        }
    }

    @Override
    public void deleteManageRole(ManageRoleBatchUpdate manageRoleBatchUpdate) {
        Example example = new Example(ManageRoleRelation.class);
        example.createCriteria().andEqualTo("manageId",manageRoleBatchUpdate.getManageId()).andIn("roleId",manageRoleBatchUpdate.getRoleIds());

        manageRoleRelationMapper.deleteByExample(example);
    }

}