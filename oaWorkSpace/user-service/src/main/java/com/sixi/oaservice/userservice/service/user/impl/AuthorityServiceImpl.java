package com.sixi.oaservice.userservice.service.user.impl;

import com.sixi.feignservice.user.model.param.AuthorityInsertDto;
import com.sixi.oaplatform.common.utils.BeanUtils;
import com.sixi.oaservice.constant.JdbcPropertyConstant;
import com.sixi.oaservice.userservice.domain.model.oapg.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sixi.oaservice.userservice.service.user.AuthorityService;
import com.sixi.oaservice.userservice.mapper.oapg.user.AuthorityMapper;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * AuthorityService实现层
 * @author Administrator create on 2018/4/17
 */
@Service
@Transactional(transactionManager = "oaPg" , rollbackFor = Exception.class)
@Slf4j
public class AuthorityServiceImpl implements AuthorityService{

    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    public List<Authority> selectListByFuncId(Integer funcId) {
        Example example = new Example(Authority.class);
        example.createCriteria().andEqualTo("status",1).andEqualTo("function_id",funcId);


        return authorityMapper.selectByExample(example);
    }

    @Override
    public Authority selectByCodeOrUrl(String code, String url) {
        Example example = new Example(Authority.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.STATUS,JdbcPropertyConstant.STATUS_USING);
        example.or().andEqualTo("code",code);
        example.or().andEqualTo("url",url);

        return authorityMapper.selectOneByExample(example);
    }

    @Override
    public void insertAuthority(AuthorityInsertDto authorityInsertDto,Integer loginUser) {
        Authority authority = BeanUtils.mapObject(authorityInsertDto, Authority.class);
        authority.setGmtAddUser(loginUser);
        authority.setGmtModifyUser(loginUser);

        //抛异常
        if (authorityMapper.insertSelective(authority)<=0){

        }
    }

    @Override
    public Authority selectByPrimary(Integer id) {

        return authorityMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateAuthority(AuthorityInsertDto authorityInsertDto, Integer loginUser) {
        Authority authority = BeanUtils.mapObject(authorityInsertDto, Authority.class);
        authority.setGmtModifyUser(loginUser);
        authority.setGmtModify(new Date());

        Example example = new Example(Authority.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.ID,authorityInsertDto.getId());

        //更新失败 抛异常
        if (authorityMapper.updateByExampleSelective(authority,example)<=0){

        }
    }

    @Override
    public void deleteAuthority(Integer id, Integer loginUser) {
        Example example = new Example(Authority.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.ID,id);

        Authority authority = Authority.builder().status(Short.valueOf("0")).gmtModify(new Date()).gmtModifyUser(loginUser).build();

        //删除失败 抛异常
        if (authorityMapper.updateByExampleSelective(authority,example)<=0){

        }
    }
}