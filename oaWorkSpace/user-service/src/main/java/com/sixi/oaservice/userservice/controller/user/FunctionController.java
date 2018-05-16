package com.sixi.oaservice.userservice.controller.user;

import com.sixi.feignservice.system.feign.DataDicService;
import com.sixi.feignservice.system.model.result.DataDicDetailInfoDto;
import com.sixi.feignservice.user.model.param.FunctionInsertDto;
import com.sixi.feignservice.user.model.result.FunctionDetailResultDto;
import com.sixi.feignservice.user.model.result.FunctionTree;
import com.sixi.oaplatform.common.utils.Fn;
import com.sixi.oaplatform.common.utils.TreeUtil;
import com.sixi.oaservice.constant.TreeCommonConstant;
import com.sixi.oaservice.userservice.domain.model.oapg.Function;
import com.sixi.oaservice.valid.group.GroupFirst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.sixi.oaservice.userservice.service.user.FunctionService;
import lombok.extern.slf4j.Slf4j;

import javax.validation.groups.Default;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Function控制层
 * @author Administrator create on 2018/4/17
 *
 */
@RestController
@RequestMapping("/function")
@Api(value = "Function控制器" , description = "Function管理")
@Slf4j
public class FunctionController{

    @Autowired
    private FunctionService functionService;

    @Autowired
    private DataDicService dataDicService;

    @ApiOperation(value = "功能块树形图",httpMethod = "get")
    @RequestMapping(value = "/funcTree",method = RequestMethod.GET)
    public List<FunctionTree> functionTreeList(){
        List<Function> functionList = functionService.selectListAll();

        return getFunctionTree(functionList,TreeCommonConstant.ROOT);
    }

    @ApiOperation(value = "由父类id得到他旗下的功能块树形图",httpMethod = "get")
    @RequestMapping(value = "/funcTree/{id:[\\d]+}",method = RequestMethod.GET)
    public List<FunctionTree> functionTreeListById(@PathVariable("id") Integer id){
        List<Function> functionList = functionService.selectListAll();

        return getFunctionTree(functionList,id);
    }

    @ApiOperation(value = "由父类名称得到他旗下的功能块树形图" , httpMethod = "get")
    @RequestMapping(value = "/funcTree/{name:[^\\d]+}",method = RequestMethod.GET)
    public List<FunctionTree> functionTreeListByName(@PathVariable("name") String name) throws UnsupportedEncodingException {
        name = URLDecoder.decode(name,"utf-8");
        Function function = functionService.selectOneByName(name);
        //抛出空的异常
        if (function==null){

        }
        List<Function> functionList = functionService.selectListAll();

        return getFunctionTree(functionList,function.getId());
    }

    @ApiOperation(value = "新增一条功能块" , httpMethod = "post")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public void insertFunction(@Validated @RequestBody FunctionInsertDto functionInsertDto){
        Function function = functionService.selectOneByNameOrCode(functionInsertDto.getName(), functionInsertDto.getCode());

        //抛出异常 存在重复
        if (function!=null){

        }

        //抛异常
        if (!functionService.insertFunction(functionInsertDto,0)){

        }
    }

    @ApiOperation(value = "得到功能块的详细信息",httpMethod = "get")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public FunctionDetailResultDto getFunctionDetailInfo(Integer id){
        id = Fn.toInt(id);

        Function function = functionService.selectByPrimary(id);
        DataDicDetailInfoDto functionDataDic = dataDicService.getDataDicInfo(function.getFunctionType());
        DataDicDetailInfoDto methodDataDic = dataDicService.getDataDicInfo(function.getMethod());

        FunctionDetailResultDto functionDetailResultDto = com.sixi.oaplatform.common.utils.BeanUtils.mapObject(function, FunctionDetailResultDto.class);
        functionDetailResultDto.setFunctionType(functionDataDic.getName());
        functionDetailResultDto.setMethodName(methodDataDic.getName());

        return functionDetailResultDto;
    }

    @ApiOperation(value = "修改一条功能块信息",httpMethod = "post")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public void updateFunctionDetailInfo(@Validated(value = {Default.class,GroupFirst.class}) @RequestBody FunctionInsertDto functionInsertDto){

        //拋異常
        if (!functionService.updateFunction(functionInsertDto,0)){

        }
    }

    @ApiOperation(value = "删除一条功能块" , httpMethod = "delete")
    @RequestMapping(value = "/delete/{id:[\\d]+}",method = RequestMethod.DELETE)
    public void deleteFunction(@PathVariable("id") Integer id){
        id = Fn.toInt(id);

        //抛异常
        if (!functionService.deleteFunction(id,0)){

        }
    }



    private List<FunctionTree> getFunctionTree(List<Function> functions,int root){
        List<FunctionTree> trees = new ArrayList<>();
        FunctionTree node;
        for (Function function : functions) {
            node = new FunctionTree();
            BeanUtils.copyProperties(function,node);
            node.setParentId(function.getParent());

            trees.add(node);
        }

        return TreeUtil.bulid(trees,root);
    }

}