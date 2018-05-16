package com.sixi.feignservice.user.feign;

import com.sixi.feignservice.user.model.param.ManageRoleUpdateAssign;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 员工角色依赖的队外服务层
 *
 * @author Administrator
 */
@FeignClient(name = "userservice")
@RequestMapping("/manageRole")
public interface ManageRoleRelationService {

    @RequestMapping(value = "/assignRole",method = RequestMethod.POST)
    void updateDifferentRole(@Validated @RequestBody ManageRoleUpdateAssign manageRoleUpdateAssign);



}
