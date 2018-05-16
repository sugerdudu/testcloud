package com.sixi.feignservice.user.feign;

import com.sixi.feignservice.user.model.param.RoleInsertDto;
import com.sixi.feignservice.user.model.result.RoleInfo;
import com.sixi.feignservice.user.model.result.RoleTree;
import com.sixi.oaservice.valid.group.GroupFirst;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;

/**
 * 角色feign
 *
 * @author Administrator
 */
@FeignClient(name = "userservice")
@RequestMapping("/role")
public interface RoleService {

    @RequestMapping(value = "/roleTree",method = RequestMethod.GET)
    List<RoleTree> roleTreeList();

    @RequestMapping(value = "/roleTree/{id:[\\d]+}",method = RequestMethod.GET)
    List<RoleTree> roleTreeListById(@PathVariable("id") Integer id);

    @RequestMapping(value = "/roleTree/{name:[^\\d]+}",method = RequestMethod.GET)
    List<RoleTree> roleTreeListByName(@PathVariable("name") String name);

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    void insertRole(@Validated @RequestBody RoleInsertDto roleInsertDto);

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    RoleInfo getRoleInfo(@RequestParam("id") Integer id);

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    void updateRole(@Validated(value = {Default.class,GroupFirst.class}) @RequestBody RoleInsertDto roleInsertDto);

    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    void deleteRole(@RequestParam("id") Integer id);


}
