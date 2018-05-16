package com.sixi.oaservice.userservice.service.user;

import com.sixi.feignservice.user.model.result.FunctionInfo;

import java.util.List;

/**
 * SyncManageService接口
 * @author Administrator create on 2018/4/25
 */
public interface SyncManageService{

    /**
     * 由用戶的id得到功能集合块
     *
     * @param loginUser 用户id
     * @return List<FunctionInfo>
     */
    List<FunctionInfo> selectFuncListByUserId(Integer loginUser);

}