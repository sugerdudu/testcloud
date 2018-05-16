package com.sixi.oaservice.userservice.controller.user;

import com.sixi.feignservice.user.model.result.FunctionInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.sixi.oaservice.userservice.service.user.SyncManageService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * SyncManage控制层
 * @author Administrator create on 2018/4/25
 *
 */
@RestController
@RequestMapping("/sync/manage")
@Api(value = "SyncManage控制器" , description = "SyncManage管理")
@Slf4j
public class SyncManageController{

    @Autowired
    private SyncManageService syncManageService;

    @RequestMapping("/functionlist/{id}")
    public List<FunctionInfo> getFunctionByUserId(@PathVariable("id") Integer userId){

        return syncManageService.selectFuncListByUserId(userId);
    }

}