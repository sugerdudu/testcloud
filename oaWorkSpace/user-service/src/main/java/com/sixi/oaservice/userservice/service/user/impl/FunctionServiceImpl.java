package com.sixi.oaservice.userservice.service.user.impl;

import com.sixi.feignservice.user.model.param.FunctionInsertDto;
import com.sixi.oaplatform.common.utils.BeanUtils;
import com.sixi.oaservice.constant.JdbcPropertyConstant;
import com.sixi.oaservice.userservice.domain.model.oapg.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sixi.oaservice.userservice.service.user.FunctionService;
import com.sixi.oaservice.userservice.mapper.oapg.user.FunctionMapper;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * FunctionService实现层
 * @author Administrator create on 2018/4/17
 */
@Service
@Transactional(transactionManager = "oaPg" , rollbackFor = Exception.class)
@Slf4j
public class FunctionServiceImpl implements FunctionService{

    @Autowired
    private FunctionMapper functionMapper;

    @Override
    public List<Function> selectListAll() {
        Example example = new Example(Function.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.STATUS,JdbcPropertyConstant.STATUS_USING);

        return functionMapper.selectByExample(example);
    }

    @Override
    public Function selectOneByName(String name) {
        Example example = new Example(Function.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.STATUS,JdbcPropertyConstant.STATUS_USING).andEqualTo(JdbcPropertyConstant.NAME,name);

        return functionMapper.selectOneByExample(example);
    }

    @Override
    public Function selectOneByNameOrCode(String name, String code) {
        Example example = new Example(Function.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.STATUS, JdbcPropertyConstant.STATUS_USING);

        Example.Criteria criteria1 = example.createCriteria().andEqualTo(JdbcPropertyConstant.NAME, name).orEqualTo(JdbcPropertyConstant.CODE, code);

        example.and(criteria1);
        //example.or().andEqualTo(JdbcPropertyConstant.CODE,code);

        return functionMapper.selectOneByExample(example);
    }

    @Override
    public boolean insertFunction(FunctionInsertDto functionInsertDto, Integer loginUser) {
        Function function = BeanUtils.mapObject(functionInsertDto, Function.class);
        function.setGmtAddUser(loginUser);
        function.setGmtModifyUser(loginUser);

        return functionMapper.insertSelective(function)>0;
    }

    @Override
    public boolean deleteFunction(Integer id,Integer loginUser) {
        Example example = new Example(Function.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.ID,id);
        Function function = Function.builder().status(Short.valueOf("0")).gmtAddUser(loginUser).gmtModifyUser(loginUser).build();

        return functionMapper.updateByExampleSelective(function,example)>0;
    }

    @Override
    public Function selectByPrimary(Integer id) {
        return functionMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean updateFunction(FunctionInsertDto functionInsertDto, Integer loginUser) {
        Example example = new Example(Function.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.ID,functionInsertDto.getId());

        Function function = BeanUtils.mapObject(functionInsertDto, Function.class);
        function.setGmtModifyUser(loginUser);
        function.setGmtModify(new Date());

        return functionMapper.updateByExampleSelective(function,example)>0;
    }
}