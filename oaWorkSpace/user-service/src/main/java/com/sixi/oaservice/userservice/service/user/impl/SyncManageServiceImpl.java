package com.sixi.oaservice.userservice.service.user.impl;

import com.sixi.feignservice.user.model.result.FunctionInfo;
import com.sixi.oaservice.userservice.mapper.oapg.SyncManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sixi.oaservice.userservice.service.user.SyncManageService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * SyncManageService实现层
 * @author Administrator create on 2018/4/25
 */
@Service
@Transactional(transactionManager = "oaPg" , rollbackFor = Exception.class)
@Slf4j
public class SyncManageServiceImpl implements SyncManageService{

    @Autowired
    private SyncManageMapper syncManageMapper;

    @Override
    public List<FunctionInfo> selectFuncListByUserId(Integer loginUser) {
        return syncManageMapper.selectFuncListByUserId(loginUser);
    }
}