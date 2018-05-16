package com.sixi.feignservice.user.model.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * 角色功能块赋值的dto
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleFunctionAssignDto {

    /**
     * 角色id
     */
    @NotNull(message = "角色id不能为空")
    @Min(value = 1,message = "角色id错误")
    private Integer roleId;

    /**
     * 功能块id集合
     */
    @NotEmpty(message = "功能id不能为空")
    private Collection<Integer> functionIds;

}
