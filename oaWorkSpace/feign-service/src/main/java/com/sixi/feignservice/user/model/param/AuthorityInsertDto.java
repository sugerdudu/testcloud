package com.sixi.feignservice.user.model.param;

import com.sixi.oaservice.valid.group.GroupFirst;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 权限新增的dto
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityInsertDto {

    @NotNull(message = "权限id不能为空",groups = GroupFirst.class)
    @Min(value = 1,message = "错误的id",groups = GroupFirst.class)
    private Integer id;

    @NotNull(message = "功能id不能为空")
    @Min(value = 1,message = "错误的功能id")
    private Integer functionId;

    /**
     * code码
     */
    @NotBlank(message = "权限编码不能为空")
    @Size(min = 1,max = 50,message = "权限编码过长")
    private String code;

    /**
     * 资源类型
     */
    @NotNull(message = "类型不能为空")
    @Min(value = 1,message = "错误的类型")
    private Integer authType;

    /**
     * 权限名
     */
    @NotBlank(message = "标题不能为空")
    @Size(min = 1,max = 50,message = "标题过长")
    private String name;

    /**
     * 资源路径
     */
    @NotBlank(message = "资源路径不能为空")
    @Size(min = 1,max = 255,message = "资源路径过长")
    private String url;

    /**
     * 请求类型
     */
    @NotNull(message = "请求类型不能为空")
    @Min(value = 0,message = "请求类型错误")
    private Integer method;

    /**
     * 资源描述
     */
    @NotBlank(message = "资源描述不能为空")
    @Size(min = 1,max = 1000,message = "资源描述过长")
    private String description;

}
