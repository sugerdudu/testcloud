package com.sixi.oaservice.userservice.service.user;

import com.sixi.feignservice.user.model.param.AuthorityInsertDto;
import com.sixi.oaservice.userservice.domain.model.oapg.Authority;

import java.util.List;

/**
 * AuthorityService接口
 * @author Administrator create on 2018/4/17
 */
public interface AuthorityService{

    /**
     * 由功能id得到权限列表
     *
     * @param funcId 功能id
     * @return 权限列表
     */
    List<Authority> selectListByFuncId(Integer funcId);

    /**
     * 由编码 或者 url得到权限信息
     *
     * @param code 编码
     * @param url 路径
     * @return 权限
     */
    Authority selectByCodeOrUrl(String code , String url);

    /**
     * 新增一条权限
     *
     * @param authorityInsertDto 权限信息
     * @param loginUser 登录人id
     */
    void insertAuthority(AuthorityInsertDto authorityInsertDto,Integer loginUser);

    /**
     * 由主键id得到信息
     *
     * @param id 主键
     * @return 权限
     */
    Authority selectByPrimary(Integer id);

    /**
     * 更新一条权限信息
     *
     * @param authorityInsertDto 权限信息
     * @param loginUser 登录人
     */
    void updateAuthority(AuthorityInsertDto authorityInsertDto , Integer loginUser);

    /**
     * 删除一条权限信息
     *
     * @param id 主键
     * @param loginUser 登录人
     */
    void deleteAuthority(Integer id , Integer loginUser);
}