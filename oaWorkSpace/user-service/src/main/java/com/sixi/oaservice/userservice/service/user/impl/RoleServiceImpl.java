package com.sixi.oaservice.userservice.service.user.impl;

import com.sixi.feignservice.user.model.param.RoleInsertDto;
import com.sixi.oaplatform.common.utils.BeanUtils;
import com.sixi.oaservice.constant.JdbcPropertyConstant;
import com.sixi.oaservice.userservice.domain.model.oapg.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.sixi.oaservice.userservice.service.user.RoleService;
import com.sixi.oaservice.userservice.mapper.oapg.user.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * RoleService实现层
 * @author Administrator create on 2018/4/17
 */
@Service
@Transactional(transactionManager = "oaPg" , rollbackFor = Exception.class)
@Slf4j
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> selectAll() {
        Example example = new Example(Role.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.STATUS,JdbcPropertyConstant.STATUS_USING);

        return roleMapper.selectByExample(example);
    }

    @Override
    public Role selectRoleByName(String name) {
        Example example = new Example(Role.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.NAME,name).andEqualTo(JdbcPropertyConstant.STATUS,JdbcPropertyConstant.STATUS_USING);

        return roleMapper.selectOneByExample(example);
    }

    @Override
    public Role selectRoleByNameOrCode(String name, String code) {
        Example example = new Example(Role.class);
        example.createCriteria().andEqualTo(JdbcPropertyConstant.STATUS,JdbcPropertyConstant.STATUS_USING);

        Example.Criteria criteria = example.createCriteria().andEqualTo(JdbcPropertyConstant.NAME, name).orEqualTo(JdbcPropertyConstant.CODE, code);
        example.and(criteria);

        return roleMapper.selectOneByExample(example);
    }

    @Override
    public void insertRole(RoleInsertDto roleInsertDto,Integer loginUser) {
        Role role = BeanUtils.mapObject(roleInsertDto, Role.class);
        role.setGmtAddUser(loginUser);
        role.setGmtModifyUser(loginUser);

        //新增失败 抛异常
        if (roleMapper.insertSelective(role)<=0){

        }

        //得到父类的角色的权限集合
        //权限下发 暂时不做

    }

    @Override
    public Role selectByPrimary(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateRole(RoleInsertDto roleInsertDto,Integer loginUser) {
        Example example = new Example(Role.class);
        example.createCriteria().orEqualTo(JdbcPropertyConstant.ID,roleInsertDto.getId());

        Role role = BeanUtils.mapObject(roleInsertDto, Role.class);
        role.setGmtModifyUser(loginUser);
        role.setGmtModify(new Date());

        //更新失败  抛异常
        if (roleMapper.updateByExampleSelective(role,example)<=0){

        }
    }

    @Override
    public void deleteRole(Integer id, Integer loginUser) {
        Example example = new Example(Role.class);
        example.createCriteria().orEqualTo(JdbcPropertyConstant.ID,id);

        Role role = Role.builder().status(Short.valueOf("0")).gmtModify(new Date()).gmtModifyUser(loginUser).build();

        //更新失败 抛异常
        if (roleMapper.updateByExample(role,example)<=0){

        }
    }
}