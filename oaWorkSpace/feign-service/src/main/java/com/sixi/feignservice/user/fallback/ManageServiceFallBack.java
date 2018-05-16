package com.sixi.feignservice.user.fallback;

import com.sixi.feignservice.user.model.EmployeeResultDto;
import com.sixi.feignservice.user.model.result.FunctionInfo;
import com.sixi.feignservice.user.feign.ManageService;

import java.util.List;

/**
 * 用户服务
 *
 * @author wangwei
 */
public class ManageServiceFallBack implements ManageService {

    @Override
    public EmployeeResultDto checkOutNameAndPassword(String userName, String password) {
        return null;
    }

}
