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
 * Created on 2018/04/20 10:06.
 */
@Table(name = "users.role_function_relation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleFunctionRelation implements Serializable {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 功能id
     */
    @Column(name = "function_id")
    private Integer functionId;

    /**
     * 新增时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 新增人信息
     */
    @Column(name = "gmt_add_user")
    private Integer gmtAddUser;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modify")
    private Date gmtModify;

    /**
     * 修改人信息
     */
    @Column(name = "gmt_modify_user")
    private Integer gmtModifyUser;

    private static final long serialVersionUID = 1L;
}