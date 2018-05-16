package com.sixi.oaservice.systemmanageservice.controller.datadic;

import com.sixi.feignservice.system.model.param.DataDicIdListDto;
import com.sixi.feignservice.system.model.result.DataDicDetailInfoDto;
import com.sixi.oaplatform.common.utils.Fn;
import com.sixi.oaplatform.common.utils.TreeUtil;
import com.sixi.oaservice.constant.TreeCommonConstant;
import com.sixi.feignservice.system.model.param.DataDicInsertDto;
import com.sixi.feignservice.system.model.result.DataDicTree;
import com.sixi.oaservice.systemmanageservice.domain.model.oapg.DataDic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.sixi.oaservice.systemmanageservice.service.datadic.DataDicService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * DataDic控制层
 * @author Administrator create on 2018/4/18
 *
 */
@RestController
@RequestMapping("/datadic")
@Api(value = "数据字典DataDic控制器" , description = "数据字典DataDic管理")
@Slf4j
public class DataDicController{

    @Autowired
    private DataDicService dataDicService;

    @ApiOperation(value = "新增一条数据字典",httpMethod = "post")
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public void insertDataDic(@Validated @RequestBody DataDicInsertDto dataDicInsertDto){
        DataDic dataDic = dataDicService.selectDataDicByNameOrCode(dataDicInsertDto.getName(), dataDicInsertDto.getCode());

        //说明已经存在数据 直接跑出异常
        if (dataDic!=null){

        }
        //不存在 新增
        //新增失败，抛出异常
        if (!dataDicService.insertDataDic(dataDicInsertDto, 0)){

        }
    }

    @ApiOperation(value = "删除一条数据字典",httpMethod = "delete")
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public void deleteDataDic(@PathVariable("id") Integer id){
        id = Fn.toInt(id);

        //删除失败，抛出异常
        if (!dataDicService.deleteDataDic(id,0)){

        }
    }

    @ApiOperation(value = "得到全部的树形结构",httpMethod = "get")
    @RequestMapping(value = "/dataDicTree",method = RequestMethod.GET)
    public List<DataDicTree> dataDicTreeList(){
        List<DataDic> dataDicList = dataDicService.selectListAll();

        return getDataDicTree(dataDicList,TreeCommonConstant.ROOT);
    }

    @ApiOperation(value = "由指定id得到他下面的树结构",httpMethod = "get")
    @RequestMapping(value = "/dataDicTree/{id}",method = RequestMethod.GET)
    public List<DataDicTree> listDataDic(@PathVariable("id") Integer id){
        List<DataDic> dataDicList = dataDicService.selectListAll();

        return getDataDicTree(dataDicList,id);
    }

    @ApiOperation(value = "由指定id得到数据字典信息",httpMethod = "get")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public DataDicDetailInfoDto getDataDicInfo(Integer id){
        DataDic dataDic = dataDicService.selectByPrimary(id);

        return com.sixi.oaplatform.common.utils.BeanUtils.mapObject(dataDic,DataDicDetailInfoDto.class);
    }

    @ApiOperation(value = "由id集合得到数据字典集合",httpMethod = "post")
    @RequestMapping(value = "/infoList",method = RequestMethod.POST)
    public List<DataDicDetailInfoDto> getDataDicInfoList(@RequestBody DataDicIdListDto dataDicIdListDto){
        List<DataDic> dataDicList = dataDicService.selectByIdList(dataDicIdListDto.getIds());

        return com.sixi.oaplatform.common.utils.BeanUtils.mapList(dataDicList, DataDicDetailInfoDto.class);
    }

    /**
     * 递归树结构查询
     *
     * @param dataDicList 数据字典集合
     * @param root 父类节点
     * @return List<DataDicTree>
     */
    private List<DataDicTree> getDataDicTree(List<DataDic> dataDicList,int root){
        List<DataDicTree> trees = new ArrayList<>();
        DataDicTree node;
        for (DataDic dataDic : dataDicList) {
            node = new DataDicTree();
            BeanUtils.copyProperties(dataDic,node);
            node.setParentId(dataDic.getParent());

            trees.add(node);
        }

        return TreeUtil.bulid(trees,root);
    }
}