package com.sixi.feignservice.user.feign;

import com.sixi.feignservice.user.model.param.RoleFunctionAssignDto;
import com.sixi.feignservice.user.model.param.RoleFunctionUpdateAssign;
import com.sixi.feignservice.user.model.result.FunctionTree;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 角色功能依赖的接口层
 *
 * @author Administrator
 */
@FeignClient("userservice")
@RequestMapping("/roleFunc")
public interface RoleFunctionRelationService {

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    List<FunctionTree> getRoleFunctionList(@RequestParam("roleId") Integer roleId);

    @RequestMapping(value = "/apply",method = RequestMethod.POST)
    void applyFunction(@Validated @RequestBody RoleFunctionAssignDto roleFunctionAssignDto);

    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    void updateDifferentFunction(@Validated @RequestBody RoleFunctionUpdateAssign roleFunctionUpdateAssign);
}
