package com.sixi.oaservice.userservice.controller.user;

import com.sixi.feignservice.system.feign.DataDicService;
import com.sixi.feignservice.system.model.param.DataDicIdListDto;
import com.sixi.feignservice.system.model.result.DataDicDetailInfoDto;
import com.sixi.feignservice.user.model.param.RoleFunctionAssignDto;
import com.sixi.feignservice.user.model.param.RoleFunctionUpdateAssign;
import com.sixi.feignservice.user.model.result.FunctionTree;
import com.sixi.oaplatform.common.utils.TreeUtil;
import com.sixi.oaservice.constant.TreeCommonConstant;
import com.sixi.oaservice.userservice.domain.model.oapg.Function;
import com.sixi.oaservice.userservice.domain.model.oapg.RoleFunctionRelation;
import com.sixi.oaservice.userservice.service.user.FunctionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.sixi.oaservice.userservice.service.user.RoleFunctionRelationService;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

/**
 * RoleFunctionRelation控制层
 * 角色赋值功能
 *
 * @author Administrator create on 2018/4/19
 *
 */
@RestController
@RequestMapping("/roleFunc")
@Api(value = "RoleFunctionRelation控制器" , description = "RoleFunctionRelation管理")
@Slf4j
public class RoleFunctionRelationController{

    @Autowired
    private RoleFunctionRelationService roleFunctionRelationService;

    @Autowired
    private FunctionService functionService;

    @Autowired
    private DataDicService dataDicService;

    @ApiOperation(value = "得到该角色的已分配的功能列表",httpMethod = "Get")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public List<FunctionTree> getRoleFunctionList(Integer roleId){
        List<RoleFunctionRelation> roleFunctionRelations = roleFunctionRelationService.selectFuncIdsByRoleId(roleId);
        List<Integer> funcIds = roleFunctionRelations.stream().map(RoleFunctionRelation::getFunctionId).collect(Collectors.toList());
        List<Function> functionList = functionService.selectListAll();

        //获得数据字典的id集合
        Set<Integer> dataDicSet = functionList.stream().map(Function::getFunctionType).collect(Collectors.toSet());
        Set<Integer> methodSet = functionList.stream().map(Function::getMethod).collect(Collectors.toSet());
        dataDicSet.addAll(methodSet);

        //获得id集合对应的名称
        DataDicIdListDto dataDicIdListDto = DataDicIdListDto.builder().ids(dataDicSet).build();
        List<DataDicDetailInfoDto> dataDicInfoList = dataDicService.getDataDicInfoList(dataDicIdListDto);

        //数据字典id 名称 转换为map
        Map<Integer,String> dataDicMap = new HashMap<>();
        for (DataDicDetailInfoDto dataDicDetailInfoDto : dataDicInfoList) {
            dataDicMap.put(dataDicDetailInfoDto.getId(),dataDicDetailInfoDto.getName());
        }

        return getFunctionTree(functionList,TreeCommonConstant.ROOT,funcIds,dataDicMap);
    }

    @ApiOperation(value = "角色赋值功能块",httpMethod = "post")
    @RequestMapping(value = "/apply",method = RequestMethod.POST)
    public void applyFunction(@Validated @RequestBody RoleFunctionAssignDto roleFunctionAssignDto){

        roleFunctionRelationService.insertRoleFunction(roleFunctionAssignDto,0);

    }

    @ApiOperation(value = "新增 或者 删除功能块的不同点" , httpMethod = "post")
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    public void updateDifferentFunction(@Validated @RequestBody RoleFunctionUpdateAssign roleFunctionUpdateAssign){
        //交集
        Collection<Integer> intersection = CollectionUtils.union(roleFunctionUpdateAssign.getNewFunctionIds(), roleFunctionUpdateAssign.getOldFunctionIds());

        //新集合 - 旧集合 那么得到集合就是 新增的集合
        Collection<Integer> subtractNew = CollectionUtils.subtract(roleFunctionUpdateAssign.getNewFunctionIds(), roleFunctionUpdateAssign.getOldFunctionIds());

        //新集合 差集 新集合与旧集合的交集  删除的集合
        Collection<Integer> subtractBefore = CollectionUtils.subtract(roleFunctionUpdateAssign.getNewFunctionIds(), intersection);

        //新增
        if (subtractNew.size()>0){
            RoleFunctionAssignDto roleFunctionAssignDto = RoleFunctionAssignDto.builder().roleId(roleFunctionUpdateAssign.getRoleId()).functionIds(subtractNew).build();

            roleFunctionRelationService.insertRoleFunction(roleFunctionAssignDto,0);
        }

        //删除
        if (subtractBefore.size()>0){
            RoleFunctionAssignDto roleFunctionAssignDto = RoleFunctionAssignDto.builder().roleId(roleFunctionUpdateAssign.getRoleId()).functionIds(subtractBefore).build();
            roleFunctionRelationService.deleteRoleFunction(roleFunctionAssignDto);
        }
    }

    private List<FunctionTree> getFunctionTree(List<Function> functions, int root,List<Integer> funcIds,Map<Integer,String> dataDicMap){
        List<FunctionTree> trees = new ArrayList<>();
        FunctionTree node;
        for (Function function : functions) {
            node = new FunctionTree();
            BeanUtils.copyProperties(function,node);
            node.setParentId(function.getParent());

            if (funcIds.contains(function.getId())){
                node.setIsAssign(1);
            }else {
                node.setIsAssign(0);
            }

            node.setMethodName(dataDicMap.get(function.getMethod()));
            node.setFunctionTypeName(dataDicMap.get(function.getFunctionType()));

            trees.add(node);
        }

        return TreeUtil.bulid(trees,root);
    }

}