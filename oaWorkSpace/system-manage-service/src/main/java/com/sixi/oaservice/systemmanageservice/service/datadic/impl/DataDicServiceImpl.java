package com.sixi.oaservice.systemmanageservice.service.datadic.impl;

import com.sixi.oaplatform.common.utils.BeanUtils;
import com.sixi.feignservice.system.model.param.DataDicInsertDto;
import com.sixi.oaservice.constant.JdbcPropertyConstant;
import com.sixi.oaservice.systemmanageservice.domain.model.oapg.DataDic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sixi.oaservice.systemmanageservice.service.datadic.DataDicService;
import com.sixi.oaservice.systemmanageservice.mapper.oapg.system.DataDicMapper;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * DataDicService实现层
 * @author Administrator create on 2018/4/18
 */
@Service
@Transactional(transactionManager = "oaPg" , rollbackFor = Exception.class)
@Slf4j
public class DataDicServiceImpl implements DataDicService{

    @Autowired
    private DataDicMapper dataDicMapper;

    @Override
    public List<DataDic> selectListAll() {
        Example example = new Example(DataDic.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.STATUS,JdbcPropertyConstant.STATUS_USING);

        return dataDicMapper.selectByExample(example);
    }

    @Override
    public DataDic selectDataDicByPrimaryId(Integer id) {
        return dataDicMapper.selectByPrimaryKey(id);
    }

    @Override
    public DataDic selectDataDicByNameOrCode(String name, String code) {
        Example example = new Example(DataDic.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.STATUS,JdbcPropertyConstant.STATUS_USING);

        Example.Criteria criteria = example.createCriteria().andEqualTo(JdbcPropertyConstant.NAME, name).orEqualTo(JdbcPropertyConstant.CODE, code);
        example.and(criteria);

        return dataDicMapper.selectOneByExample(example);
    }

    @Override
    public boolean insertDataDic(DataDicInsertDto dataDicInsertDto,Integer loginUser) {
        DataDic dataDic = BeanUtils.mapObject(dataDicInsertDto, DataDic.class);
        dataDic.setGmtAddUser(loginUser);
        dataDic.setGmtModifyUser(loginUser);

        int i = dataDicMapper.insertSelective(dataDic);
        return i>0;
    }

    @Override
    public boolean deleteDataDic(Integer id,Integer loginUser) {
        Example example = new Example(DataDic.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.ID,id);

        DataDic dataDic = DataDic.builder().status(Short.valueOf("0")).gmtModify(new Date()).gmtModifyUser(loginUser).build();

        int i = dataDicMapper.updateByExampleSelective(dataDic, example);
        return i>0;
    }

    @Override
    public DataDic selectByPrimary(Integer id) {
        return dataDicMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<DataDic> selectByIdList(Collection<Integer> ids) {
        Example example = new Example(DataDic.class);
        example.createCriteria().andIn(JdbcPropertyConstant.ID,ids);

        return dataDicMapper.selectByExample(example);
    }
}