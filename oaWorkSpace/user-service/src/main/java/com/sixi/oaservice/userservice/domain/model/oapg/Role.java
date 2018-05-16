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
 * Created on 2018/04/17 05:05.
 */
@Table(name = "users.role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 编码描述
     */
    private String code;

    /**
     * 权限名
     */
    private String name;

    /**
     * 备注
     */
    private String description;

    /**
     * 父类角色（总监 经理 掌门）
     */
    private Integer parent;

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
     * 添加人
     */
    @Column(name = "gmt_add_user")
    private Integer gmtAddUser;

    @Column(name = "gmt_modify_user")
    private Integer gmtModifyUser;

    /**
     * 状态 1启用 0禁用
     */
    private Short status;

    private static final long serialVersionUID = 1L;
}