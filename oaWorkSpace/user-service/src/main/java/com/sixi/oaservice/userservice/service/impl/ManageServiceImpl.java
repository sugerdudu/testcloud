package com.sixi.oaservice.userservice.service.impl;

import com.sixi.oaservice.userservice.service.ManageService;
import com.sixi.oaservice.userservice.domain.dto.EmployeeResultDto;
import com.sixi.oaservice.userservice.mapper.oasqlserver.ManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户管理接口实现层
 *
 * @author Administrator
 */
@Service
@Transactional(transactionManager = "oasqlServer",rollbackFor = Exception.class,readOnly = true)
public class ManageServiceImpl implements ManageService {

    @Autowired
    private ManageMapper manageMapper;

    @Override
    public EmployeeResultDto login(String username, String password) {
        return manageMapper.checkOutNameAndPassword(username,password);
    }

}
