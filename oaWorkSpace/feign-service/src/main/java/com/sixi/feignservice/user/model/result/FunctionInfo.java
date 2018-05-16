package com.sixi.feignservice.user.model.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 *
 * @author wangwei
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionInfo implements Serializable {

    private static final long serialVersionUID = -1482145630198088729L;

    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名称
     */
    private String userName;

    /**
     * 编码
     */
    private String code;

    /**
     * url
     */
    private String url;

    /**
     * 名称
     */
    private String name;

    /**
     * 功能类型名称
     */
    private String functionTypeName;
    /**
     * 功能类型编码
     */
    private String functionTypeCode;


    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 方法英文名称
     */
    private String methodEngName;
    /**
     * 方法编码
     */
    private String methodCode;

    /**
     * 当前功能块的进程
     */
    private Integer progress;

    //private String menu;
    //private List<AuthorityInfo> authorityInfos;

}
