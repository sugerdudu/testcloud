package com.sixi.feignservice.system.model.result;

import com.sixi.oaplatform.common.domain.vo.TreeNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 数据字典树结构
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataDicTree extends TreeNode implements Serializable {

    private static final long serialVersionUID = -8909631769582511015L;

    /**
     * 数据字典名称
     */
    private String name;

}
