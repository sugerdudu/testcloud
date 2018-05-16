package com.sixi.oaplatform.common.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by wangwei on 2018/3/27.
 *
 * 线上用户信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnLineUser {

    private Integer userId;

    private String userName;

    private Integer class1Id;

    private String class1Name;

    private Integer class2Id;

    private String class2Name;

}
