package com.sixi.feignservice.user.feign;

import com.sixi.feignservice.user.model.result.FunctionInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 用户模块 个人信息
 *
 * @author Administrator
 */
@FeignClient(name = "userservice")
@RequestMapping("/sync/manage")
public interface SyncManageService {

    /**
     * 用户名id得到功能列表集合
     *
     * @param userId 用户id
     * @return 用户功能集合列表
     */
    @RequestMapping("/functionlist/{id}")
    List<FunctionInfo> getFunctionByUserId(@PathVariable("id") Integer userId);

}
