package com.sixi.oaservice.userservice.controller.user;

import com.sixi.feignservice.user.model.param.RoleInsertDto;
import com.sixi.feignservice.user.model.result.RoleInfo;
import com.sixi.feignservice.user.model.result.RoleTree;
import com.sixi.oaplatform.common.utils.Fn;
import com.sixi.oaplatform.common.utils.TreeUtil;
import com.sixi.oaservice.constant.TreeCommonConstant;
import com.sixi.oaservice.userservice.domain.model.oapg.Role;
import com.sixi.oaservice.valid.group.GroupFirst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.sixi.oaservice.userservice.service.user.RoleService;
import lombok.extern.slf4j.Slf4j;

import javax.validation.groups.Default;
import java.util.ArrayList;
import java.util.List;

/**
 * Role控制层
 * @author Administrator create on 2018/4/17
 *
 */
@RestController
@RequestMapping("/role")
@Api(value = "Role控制器" , description = "Role管理")
@Slf4j
public class RoleController{

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "全部的角色树形图",httpMethod = "get")
    @RequestMapping(value = "/roleTree",method = RequestMethod.GET)
    public List<RoleTree> roleTreeList(){
        List<Role> roleList = roleService.selectAll();

        return getRoleTree(roleList,TreeCommonConstant.ROOT);
    }

    @ApiOperation(value = "由父类id得到旗下的角色树形图",httpMethod = "get")
    @RequestMapping(value = "/roleTree/{id:[\\d]+}",method = RequestMethod.GET)
    public List<RoleTree> roleTreeListById(@PathVariable("id") Integer id){
        List<Role> roleList = roleService.selectAll();

        return getRoleTree(roleList,id);
    }

    @ApiOperation(value = "由父类名称得到旗下的角色树形图",httpMethod = "get")
    @RequestMapping(value = "/roleTree/{name:[^\\d]+}",method = RequestMethod.GET)
    public List<RoleTree> roleTreeListByName(@PathVariable("name") String name){
        Role role = roleService.selectRoleByName(name);
        List<Role> roleList = roleService.selectAll();

        return getRoleTree(roleList,Fn.toInt(role.getId()));
    }

    @ApiOperation(value = "新增角色",httpMethod = "post")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public void insertRole(@Validated @RequestBody RoleInsertDto roleInsertDto){
        Role role = roleService.selectRoleByNameOrCode(roleInsertDto.getName(), roleInsertDto.getCode());

        //说明已经存在 抛出异常
        if (role != null){

        }

        roleService.insertRole(roleInsertDto,0);
    }

    @ApiOperation(value = "得到角色信息",httpMethod = "get")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public RoleInfo getRoleInfo(Integer id){
        Role role = roleService.selectByPrimary(id);

        return com.sixi.oaplatform.common.utils.BeanUtils.mapObject(role, RoleInfo.class);
    }

    @ApiOperation(value = "更新角色信息",httpMethod = "post")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public void updateRole(@Validated(value = {Default.class,GroupFirst.class}) @RequestBody RoleInsertDto roleInsertDto){

        roleService.updateRole(roleInsertDto,0);
    }

    @ApiOperation(value = "删除角色信息",httpMethod = "delete")
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    public void deleteRole(Integer id){
        id = Fn.toInt(id);

        roleService.deleteRole(id,0);
    }

    /**
     * 递归树结构查询
     *
     * @param roleList 角色集合
     * @param root 父类节点
     * @return List<DataDicTree>
     */
    private List<RoleTree> getRoleTree(List<Role> roleList, int root){
        List<RoleTree> trees = new ArrayList<>();
        RoleTree node;
        for (Role role : roleList) {
            node = new RoleTree();
            BeanUtils.copyProperties(role,node);
            node.setParentId(role.getParent());

            trees.add(node);
        }

        return TreeUtil.bulid(trees,root);
    }

}