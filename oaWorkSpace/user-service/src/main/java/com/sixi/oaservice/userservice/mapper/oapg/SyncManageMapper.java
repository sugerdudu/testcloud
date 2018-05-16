package com.sixi.oaservice.userservice.mapper.oapg;

import com.sixi.feignservice.user.model.result.FunctionInfo;
import com.sixi.oaservice.userservice.domain.model.oapg.SyncManage;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created with Mybatis Generator Plugin
 *
 * @author MiaoWoo
 * Created on 2018/04/25 05:16.
 */
@Repository
public interface SyncManageMapper extends Mapper<SyncManage> {

    /**
     * 由用戶的id得到功能集合块
     *
     * @param loginUser 用户id
     * @return List<FunctionInfo>
     */
    List<FunctionInfo> selectFuncListByUserId(Integer loginUser);

}