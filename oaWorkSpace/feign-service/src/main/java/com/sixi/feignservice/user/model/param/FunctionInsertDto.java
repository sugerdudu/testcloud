package com.sixi.feignservice.user.model.param;

import com.sixi.oaservice.valid.group.GroupFirst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 新增功能块dto
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionInsertDto {

    @NotNull(message = "功能块id不能为空",groups = GroupFirst.class)
    @Min(value = 1,message = "功能块id错误",groups = GroupFirst.class)
    private Integer id;

    /**
     * 名称
     */
    @NotBlank(message = "标题不能为空")
    @Size(min = 1,max = 50,message = "标题过长")
    private String name;

    /**
     * 编码
     */
    @NotBlank
    @Size(min = 1,max = 50,message = "编码过长")
    private String code;

    /**
     * 路径
     */
    @Size(max = 1000,message = "路径过长")
    private String url;

    /**
     * 请求方法
     */
    @NotNull(message = "请求方式不能为空")
    private Integer method;

    /**
     * 图标
     */
    @Size(max = 255,message = "图标过长")
    private String icon;

    /**
     * 功能的类型
     */
    @NotNull(message = "功能类型不能为空")
    @Min(value = 1,message = "错误的功能类型")
    private Integer functionType;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    @Min(value = 1,message = "错误的排序")
    private Integer sort;

    /**
     * 描述
     */
    @Size(max = 250,message = "描述过长")
    private String description;

    /**
     * 父类
     */
    @NotNull(message = "父类节点不能为空")
    @Min(value = 1,message = "错误的节点")
    private Integer parent;
}
