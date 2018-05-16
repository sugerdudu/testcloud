package com.sixi.oaservice.systemmanageservice.service.datadic;

import com.sixi.feignservice.system.model.param.DataDicInsertDto;
import com.sixi.feignservice.system.model.result.DataDicDetailInfoDto;
import com.sixi.oaservice.systemmanageservice.domain.model.oapg.DataDic;

import java.util.Collection;
import java.util.List;

/**
 * DataDicService接口
 * @author Administrator create on 2018/4/18
 */
public interface DataDicService{

    /**
     * 获得全部的数据字典
     *
     * @return List<DataDic>
     */
    List<DataDic> selectListAll();

    /**
     * 由主键得到信息
     *
     * @param id 主键
     * @return DataDic
     */
    DataDic selectDataDicByPrimaryId(Integer id);

    /**
     * 由数据字典姓名或者编码得到数据字典
     *
     * @param name 名称
     * @param code 编码
     * @return DataDic
     */
    DataDic selectDataDicByNameOrCode(String name,String code);

    /**
     * 添加一条数据字典记录
     *
     * @param dataDicInsertDto 数据字典信息
     * @param loginUser 登录人
     * @return boolean
     */
    boolean insertDataDic(DataDicInsertDto dataDicInsertDto,Integer loginUser);

    /**
     * 删除一条数据字典
     *
     * @param id id
     * @param loginUser 登录人id
     * @return boolean
     */
    boolean deleteDataDic(Integer id,Integer loginUser);

    /**
     * 由主键获得数据字典
     *
     * @param id  主键
     * @return datadic
     */
    DataDic selectByPrimary(Integer id);

    List<DataDic> selectByIdList(Collection<Integer> ids);
}