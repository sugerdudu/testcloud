package com.sixi.oaservice.userservice.mapper.oapg.user;

import com.sixi.oaservice.userservice.domain.dto.user.RoleFunctionBatchInsert;
import com.sixi.oaservice.userservice.domain.model.oapg.RoleFunctionRelation;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created with Mybatis Generator Plugin
 *
 * @author MiaoWoo
 * Created on 2018/04/20 10:06.
 */
@Repository
public interface RoleFunctionRelationMapper extends Mapper<RoleFunctionRelation> {

    /**
     * 批量新增角色 功能块对应信息
     *
     * @param roleFunctionBatchInsertList 批量信息
     * @return int
     */
    int insertBatch(List<RoleFunctionBatchInsert> roleFunctionBatchInsertList);

}