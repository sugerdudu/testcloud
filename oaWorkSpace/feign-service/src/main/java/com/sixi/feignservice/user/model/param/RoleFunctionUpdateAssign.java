package com.sixi.feignservice.user.model.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

/**
 * 角色 功能 不同的块进行更新赋值
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleFunctionUpdateAssign {

    /**
     * 这个角色的旧功能块id集合
     */
    private Collection<Integer> oldFunctionIds;

    /**
     * 当前点击保存的新功能块id集合
     */
    private Collection<Integer> newFunctionIds;

    /**
     * 角色集合
     */
    private Integer roleId;

}
