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
 * Created on 2018/04/17 05:07.
 */
@Table(name = "users.function")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Function implements Serializable {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 功能名称
     */
    private String name;

    /**
     * code类型
     */
    private String code;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求方式,post get 其他
     */
    private Integer method;

    /**
     * 图标路径
     */
    private String icon;

    /**
     * 数据字典功能的类型
     */
    @Column(name = "function_type")
    private Integer functionType;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String description;

    /**
     * 父类功能
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
     * 新增人员
     */
    @Column(name = "gmt_add_user")
    private Integer gmtAddUser;

    @Column(name = "gmt_modify_user")
    private Integer gmtModifyUser;

    /**
     * 状态 0： 禁用  1启用
     */
    private Short status;

    private static final long serialVersionUID = 1L;
}