package com.sixi.feignservice.user.model.result;

import com.sixi.oaplatform.common.domain.vo.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色树形图
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleTree extends TreeNode implements Serializable {
    private static final long serialVersionUID = 1016152660262718805L;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 描述
     */
    private String description;
}
