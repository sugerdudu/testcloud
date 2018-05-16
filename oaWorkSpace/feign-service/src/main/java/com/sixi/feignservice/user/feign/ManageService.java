package com.sixi.feignservice.user.feign;

import com.sixi.feignservice.user.fallback.ManageServiceFallBack;
import com.sixi.feignservice.user.model.EmployeeResultDto;
import com.sixi.feignservice.user.model.result.FunctionInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 用户服务的feign
 *
 * @author wangwei
 */
@FeignClient(value = "userservice",fallback = ManageServiceFallBack.class)
@RequestMapping("/sys")
public interface ManageService {

    /**
     * 检查用户名和密码 用于登录
     *
     * @param username 用户名
     * @param password  密码
     * @return 员工信息
     */
    @RequestMapping("/login")
    EmployeeResultDto checkOutNameAndPassword(@RequestParam("username") String username, @RequestParam("password") String password);

}