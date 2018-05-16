package com.sixi.feignservice.user.model.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleInfo {

    /**
     * 主键
     */
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
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;

    /**
     * 添加人
     */
    private Integer gmtAddUser;

    private Integer gmtModifyUser;

    /**
     * 状态 1启用 0禁用
     */
    private Short status;

}
