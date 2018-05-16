package com.sixi.feignservice.user.model.result;

import com.sixi.oaplatform.common.domain.vo.TreeNode;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能块 树结构
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionTree extends TreeNode implements Serializable {

    private static final long serialVersionUID = 8930516263591467782L;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 路径地址
     */
    private String url;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 图标
     */
    private String icon;

    /**
     * 功能块名称
     */
    private String functionTypeName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否分配
     * 1 是 0 不是
     */
    private Integer isAssign;
}
