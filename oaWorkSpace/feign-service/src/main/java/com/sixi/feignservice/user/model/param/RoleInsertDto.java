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
 * 角色新增的dto
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleInsertDto {

    @NotNull(message = "id不能为空",groups = GroupFirst.class)
    @Min(value = 1,message = "id错误",groups = GroupFirst.class)
    private Integer id;

    /**
     * 角色编码
     */
    @NotBlank(message = "编码不能为空")
    @Size(min = 1,max = 255,message = "编码过长")
    private String code;

    @NotBlank(message = "标题不能为空")
    @Size(min = 1,max = 50,message = "标题过长")
    private String name;

    /**
     * 描述
     */
    @Size(max = 250 , message = "描述过长")
    private String description;

    //@NotNull(message = "父类节点不能为空")
    //@Min(value = 1,message = "错误的节点")
    private Integer parent;

}
