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
 * Created on 2018/04/20 02:48.
 */
@Table(name = "users.manage_role_relation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManageRoleRelation implements Serializable {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 人员id
     */
    @Column(name = "manage_id")
    private Integer manageId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 添加时间
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

    /**
     * 修改人
     */
    @Column(name = "gmt_modify_user")
    private Integer gmtModifyUser;

    private static final long serialVersionUID = 1L;
}