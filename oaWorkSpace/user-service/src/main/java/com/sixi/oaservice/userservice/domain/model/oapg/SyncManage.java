package com.sixi.oaservice.userservice.domain.model.oapg;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with Mybatis Generator Plugin
 *
 * @author MiaoWoo
 * Created on 2018/04/25 05:16.
 */
@Table(name = "sync_manage")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncManage implements Serializable {
    /**
     * 主键
     */
    @Id
    private Integer id;

    /**
     * 人员名
     */
    private String username;

    /**
     * 一级部门id
     */
    private Integer class1;

    /**
     * 二级部门id
     */
    private Integer class2;

    /**
     * 人员职位
     */
    private Integer place;

    /**
     * 是否在职
     */
    private Integer ischecked;

    /**
     * 花名
     */
    private String fname;

    /**
     * 头像
     */
    private String photo;

    private static final long serialVersionUID = 1L;
}