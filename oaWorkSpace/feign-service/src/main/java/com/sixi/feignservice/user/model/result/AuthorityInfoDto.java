package com.sixi.feignservice.user.model.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * 权限信息dto返回列表
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityInfoDto implements Serializable {
    private static final long serialVersionUID = 1757159268527126164L;

    private Integer id;

    /**
     * code码
     */
    private String code;


    /**
     * 资源类型
     */
    private Integer authType;
    private String authTypeName;

    /**
     * 权限名
     */
    private String name;

    /**
     * 资源路径
     */
    private String url;

    /**
     * 请求类型
     */
    private Integer method;
    private String methodName;

    /**
     * 资源描述
     */
    private String description;

    /**
     * 新增时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;

    /**
     * 新增人员
     */
    private Integer gmtAddUser;

    private Integer gmtModifyUser;

    /**
     * 状态 0：禁用 1：启用
     */
    private Short status;

}
