package com.sixi.oaservice.userservice.controller.user;

import com.sixi.feignservice.user.model.param.ManageRoleUpdateAssign;
import com.sixi.oaservice.userservice.domain.dto.user.ManageRoleBatchUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.sixi.oaservice.userservice.service.user.ManageRoleRelationService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;

/**
 * ManageRoleRelation控制层
 * 人员 角色赋值
 *
 * @author Administrator create on 2018/4/20
 *
 */
@RestController
@RequestMapping("/manageRole")
@Api(value = "ManageRoleRelation控制器" , description = "ManageRoleRelation管理")
@Slf4j
public class ManageRoleRelationController{

    @Autowired
    private ManageRoleRelationService manageRoleRelationService;

    @ApiOperation(value = "新增 或者 删除 员工角色赋值",httpMethod = "post")
    @RequestMapping(value = "/assignRole",method = RequestMethod.POST)
    public void updateDifferentRole(@Validated @RequestBody ManageRoleUpdateAssign manageRoleUpdateAssign){
        if (manageRoleUpdateAssign.getOldRoleIds()==null){
            manageRoleUpdateAssign.setOldRoleIds(Collections.emptyList());
        }
        //交集
        Collection<Integer> intersection = CollectionUtils.union(manageRoleUpdateAssign.getNewRoleIds(), manageRoleUpdateAssign.getOldRoleIds());

        //新集合 - 旧集合 == 新增的集合 减差
        Collection<Integer> newRoleIds = CollectionUtils.subtract(manageRoleUpdateAssign.getNewRoleIds(), manageRoleUpdateAssign.getOldRoleIds());

        //新集合 差集 新集合与旧集合的交集  删除的集合
        Collection<Integer> diffUnSelect = CollectionUtils.subtract(manageRoleUpdateAssign.getNewRoleIds(), intersection);

        //新增
        if (newRoleIds.size()>0){
            ManageRoleBatchUpdate manageRoleBatchUpdate = ManageRoleBatchUpdate.builder().roleIds(newRoleIds).manageId(manageRoleUpdateAssign.getManageId()).build();
            manageRoleRelationService.insertManageRoleBatch(manageRoleBatchUpdate,0);
        }

        //删除
        if (diffUnSelect.size()>0){
            ManageRoleBatchUpdate manageRoleBatchUpdate = ManageRoleBatchUpdate.builder().roleIds(diffUnSelect).manageId(manageRoleUpdateAssign.getManageId()).build();

            manageRoleRelationService.deleteManageRole(manageRoleBatchUpdate);
        }
    }

}