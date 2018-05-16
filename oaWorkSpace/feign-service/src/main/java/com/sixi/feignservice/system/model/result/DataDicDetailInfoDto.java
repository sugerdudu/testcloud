package com.sixi.feignservice.system.model.result;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据字典详细信息的dto
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataDicDetailInfoDto implements Serializable {
    private static final long serialVersionUID = -4338299495985220149L;

    /**
     * 主键
     */
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
    private String dataType;

    /**
     * 新增时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModify;

    /**
     * 新增用户
     */
    private Integer gmtAddUser;

    /**
     * 最后修改用户
     */
    private Integer gmtModifyUser;

    /**
     * 是否启用 1启用 0禁用
     */
    private Short status;

}
