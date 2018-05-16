package com.sixi.oaservice.userservice.service.user.impl;

import com.sixi.feignservice.user.model.param.RoleFunctionAssignDto;
import com.sixi.oaservice.userservice.domain.dto.user.RoleFunctionBatchInsert;
import com.sixi.oaservice.userservice.domain.model.oapg.Role;
import com.sixi.oaservice.userservice.domain.model.oapg.RoleFunctionRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sixi.oaservice.userservice.service.user.RoleFunctionRelationService;
import com.sixi.oaservice.userservice.mapper.oapg.user.RoleFunctionRelationMapper;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * RoleFunctionRelationService实现层
 * @author Administrator create on 2018/4/19
 */
@Service
@Transactional(transactionManager = "oaPg" , rollbackFor = Exception.class)
@Slf4j
public class RoleFunctionRelationServiceImpl implements RoleFunctionRelationService{

    @Autowired
    private RoleFunctionRelationMapper roleFunctionRelationMapper;

    @Override
    public void insertRoleFunction(RoleFunctionAssignDto roleFunctionAssignDto, Integer loginUser) {
        List<RoleFunctionBatchInsert> roleFunctionBatchInsertList = new ArrayList<>();
        for (Integer funcId : roleFunctionAssignDto.getFunctionIds()) {
            RoleFunctionBatchInsert functionBatchInsert = RoleFunctionBatchInsert.builder().addUser(loginUser).modifyUser(loginUser).functionId(funcId).roleId(roleFunctionAssignDto.getRoleId()).build();

            roleFunctionBatchInsertList.add(functionBatchInsert);
        }

        //新增失败 抛异常
        if (roleFunctionRelationMapper.insertBatch(roleFunctionBatchInsertList)<roleFunctionBatchInsertList.size()){

        }
    }

    @Override
    public void deleteRoleFunction(RoleFunctionAssignDto roleFunctionAssignDto) {
        Example example = new Example(RoleFunctionRelation.class);
        example.createCriteria().andIn("functionId",roleFunctionAssignDto.getFunctionIds()).andEqualTo("roleId",roleFunctionAssignDto.getRoleId());

        roleFunctionRelationMapper.deleteByExample(example);
    }

    @Override
    public List<RoleFunctionRelation> selectFuncIdsByRoleId(Integer roleId) {
        Example example = new Example(RoleFunctionRelation.class);
        example.createCriteria().andEqualTo("roleId",roleId);

        return roleFunctionRelationMapper.selectByExample(example);
    }
}