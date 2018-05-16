package com.sixi.feignservice.user.model.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 人员 角色 赋值更新dto
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManageRoleUpdateAssign {


    private List<Integer> oldRoleIds;

    @NotEmpty(message = "不能赋值空角色")
    private List<Integer> newRoleIds;

    @NotNull(message = "人员id不能为空")
    @Min(value = 1,message = "人员错误")
    private Integer manageId;
}
