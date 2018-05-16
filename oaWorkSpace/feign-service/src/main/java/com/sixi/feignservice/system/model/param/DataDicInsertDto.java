package com.sixi.feignservice.system.model.param;

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
 * 数据字典新增的dto
 *
 * @author Administrator
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataDicInsertDto {

    /**
     * 编码识别
     */
    @NotBlank(message = "编码不能为空")
    @Size(min = 1,max = 255,message = "输入正确的编码")
    private String code;

    /**
     * 中文识别
     */
    @NotBlank(message = "名称不能为空")
    @Size(min = 1,max = 255,message = "请输入正确的名称")
    private String name;

    /**
     * 英文识别
     */
    @NotBlank(message = "英文名称不能为空")
    @Size(min = 1,max = 255,message = "请输入正确的英文名称")
    private String engName;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    @Min(value = 0,message = "输入正确的排序")
    @Max(value = Integer.MAX_VALUE,message = "输入正确的排序")
    private Integer sort;

    /**
     * 父类
     */
    @NotNull(message = "父类节点不能为空")
    @Min(value = 0,message = "输入正确的父类节点")
    @Max(value = Integer.MAX_VALUE,message = "输入正确的父类节点")
    private Integer parent;

    /**
     * 类型
     */
    private String dataType;

}
