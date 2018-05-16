package com.sixi.oaservice.userservice.domain.model.oapg;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with Mybatis Generator Plugin
 *
 * @author MiaoWoo
 * Created on 2018/04/17 05:08.
 */
@Table(name = "users.authority")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Authority implements Serializable {
    @Id
    private Integer id;

    @Column(name = "function_id")
    private Integer functionId;

    /**
     * code码
     */
    private String code;

    /**
     * 资源类型
     */
    @Column(name = "auth_type")
    private Integer authType;

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

    /**
     * 资源描述
     */
    private String description;

    /**
     * 新增时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modify")
    private Date gmtModify;

    /**
     * 新增人员
     */
    @Column(name = "gmt_add_user")
    private Integer gmtAddUser;

    @Column(name = "gmt_modify_user")
    private Integer gmtModifyUser;

    /**
     * 状态 0：禁用 1：启用
     */
    private Short status;

    private static final long serialVersionUID = 1L;
}