package com.sixi.oaplatform.common.constant;

import com.sixi.oaplatform.common.constant.parent.ConstantFactory;
import com.sixi.oaplatform.common.domain.model.OnLineUser;

import java.util.List;

/**
 * Created by wangwei on 2018/3/27.
 *
 * 权限的枚举类
 */
public enum RoleEnumConstant implements ConstantFactory {

    master(0,"掌门");

    RoleEnumConstant() {
    }

    private Integer id;
    private String roleName;

    RoleEnumConstant(Integer id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    @Override
    public Object get(OnLineUser onLineUser) {
        return null;
    }

    @Override
    public List getByPName(OnLineUser onLineUser) {
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
