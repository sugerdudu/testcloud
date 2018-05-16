package com.sixi.oaservice.userservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author wangwei
 * @date 2018/3/29
 *
 * 员工结果dto
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResultDto {

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 大部门id
     */
    private Integer class1Id;

    /**
     * 小部门id
     */
    private Integer class2Id;

}
