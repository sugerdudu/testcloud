package com.sixi.feignservice.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityInfo implements Serializable {

    private String name;

    private String code;

    private Integer sortNum;


}
