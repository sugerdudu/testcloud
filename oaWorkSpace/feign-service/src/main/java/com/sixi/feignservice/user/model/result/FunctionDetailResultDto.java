package com.sixi.feignservice.user.model.result;

import com.sixi.feignservice.user.model.AuthorityInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 功能块详细信息 包含数据字典信息
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionDetailResultDto implements Serializable {

    /**
     * 编码
     */
    private String code;
    /**
     * 标题
     */
    private String name;
    /**
     * 父类节点
     */
    private String parentName;
    /**
     * 图标
     */
    private String icon;
    /**
     * 请求地址
     */
    private String url;
    /**
     * 功能块类型
     */
    private String functionType;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String description;

    /**
     * 请求类型
     */
    private String methodName;

    private List<AuthorityInfo> authorityInfos;

}
