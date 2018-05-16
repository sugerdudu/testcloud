package com.sixi.oaplatform.common.constant.parent;

import com.sixi.oaplatform.common.domain.model.OnLineUser;

import java.util.List;

/**
 * 常量的父类接口
 *
 * @author wangwei
 * @date 2018/3/27
 *
 */
public interface ConstantFactory<T> {

    T get(OnLineUser onLineUser);

    List<T> getByPName(OnLineUser onLineUser);
}
