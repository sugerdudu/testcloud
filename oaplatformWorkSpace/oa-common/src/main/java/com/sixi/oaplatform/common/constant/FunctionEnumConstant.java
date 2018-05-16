package com.sixi.oaplatform.common.constant;

/**
 * 功能类型枚举
 *
 * @author Administrator
 */
public enum  FunctionEnumConstant {

    /**
     * 列表功能类
     */
    list_view("列表","Function:type::list"),
    audit_view("审核","Function:type::audit");

    /**
     * 功能类名称
     */
    private String typeName;

    /**
     * 功能类编码
     */
    private String typeCode;

    FunctionEnumConstant() {
    }

    FunctionEnumConstant(String typeName, String typeCode) {
        this.typeName = typeName;
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
