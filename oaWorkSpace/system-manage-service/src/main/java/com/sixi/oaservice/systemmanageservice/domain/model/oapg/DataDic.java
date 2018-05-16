package com.sixi.oaservice.systemmanageservice.domain.model.oapg;

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
 * Created on 2018/04/18 03:12.
 */
@Table(name = "system.data_dic")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataDic implements Serializable {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 英文名称
     */
    @Column(name = "eng_name")
    private String engName;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 父类id
     */
    private Integer parent;

    /**
     * 占位，暂时不清楚如何使用
     */
    @Column(name = "data_type")
    private String dataType;

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
     * 新增用户
     */
    @Column(name = "gmt_add_user")
    private Integer gmtAddUser;

    /**
     * 最后修改用户
     */
    @Column(name = "gmt_modify_user")
    private Integer gmtModifyUser;

    /**
     * 是否启用 1启用 0禁用
     */
    private Short status;

    private static final long serialVersionUID = 1L;
}