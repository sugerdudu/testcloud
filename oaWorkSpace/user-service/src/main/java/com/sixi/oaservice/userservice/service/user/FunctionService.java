package com.sixi.oaservice.userservice.service.user;

import com.sixi.feignservice.user.model.param.FunctionInsertDto;
import com.sixi.oaservice.userservice.domain.model.oapg.Function;

import java.util.List;

/**
 * FunctionService接口
 * @author Administrator create on 2018/4/17
 */
public interface FunctionService{

    /**
     * 获取全部的功能列表
     *
     * @return List<Function>
     */
    List<Function> selectListAll();

    /**
     * 由名称得到功能信息
     *
     * @param name 功能名称
     * @return Function
     */
    Function selectOneByName(String name);

    /**
     * 由名称 或者 编码 得到功能信息
     *
     * @param name 名称
     * @param code 编码
     * @return 功能块
     */
    Function selectOneByNameOrCode(String name,String code);

    /**
     * 新增一条功能块
     *
     * @param functionInsertDto 新增信息
     * @param loginUser 登录人
     * @return boolean
     */
    boolean insertFunction(FunctionInsertDto functionInsertDto,Integer loginUser);

    /**
     * 删除功能块
     *
     * @param id 主键
     * @param loginUser 登录人
     * @return boolean
     */
    boolean deleteFunction(Integer id,Integer loginUser);

    /**
     * 由主键得到功能块
     *
     * @param id 主键
     * @return 功能块
     */
    Function selectByPrimary(Integer id);

    /**
     * 更新功能快
     *
     * @param functionInsertDto 功能塊新增
     * @param loginUser 登錄人
     * @return boolean
     */
    boolean updateFunction(FunctionInsertDto functionInsertDto , Integer loginUser);
}