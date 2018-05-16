package com.sixi.feignservice.user.feign;

import com.sixi.feignservice.user.model.param.FunctionInsertDto;
import com.sixi.feignservice.user.model.result.FunctionDetailResultDto;
import com.sixi.feignservice.user.model.result.FunctionTree;
import com.sixi.oaservice.valid.group.GroupFirst;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;

/**
 * 功能块 feign
 *
 * @author Administrator
 */
@FeignClient(name = "userservice")
@RequestMapping("/function")
public interface FunctionService {

    @RequestMapping(value = "/funcTree",method = RequestMethod.GET)
    List<FunctionTree> functionTreeList();

    @RequestMapping(value = "/funcTree/{id:[\\d]+}",method = RequestMethod.GET)
    List<FunctionTree> functionTreeListById(@PathVariable("id") Integer id);

    @RequestMapping(value = "/funcTree/{name:[^\\d]+}",method = RequestMethod.GET)
    List<FunctionTree> functionTreeListByName(@PathVariable("name") String name);

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    void insertFunction(@Validated @RequestBody FunctionInsertDto functionInsertDto);

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    FunctionDetailResultDto getFunctionDetailInfo(@RequestParam("id") Integer id);

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    void updateFunctionDetailInfo(@Validated(value = {Default.class,GroupFirst.class}) @RequestBody FunctionInsertDto functionInsertDto);

    @RequestMapping(value = "/delete/{id:[\\d]+}",method = RequestMethod.DELETE)
    void deleteFunction(@PathVariable("id") Integer id);
}
