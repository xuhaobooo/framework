package com.sz91online.bgms.module.common.domain;

import com.sz91online.common.db.entity.RootBean;

/**
 *
 * 此类对应数据库表: t_dict_option
 */
public class DictOption extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_dict_option.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_dict_option.DIC_CODE
     */
    private String dicCode;

    /**
     *
     * 此属性对应数据库字段: t_dict_option.DIC_LABEL
     */
    private String dicLabel;

    /**
     *
     * 此属性对应数据库字段: t_dict_option.DIC_DESC
     */
    private String dicDesc;

    /**
     *
     * 此属性对应数据库字段: t_dict_option.DIC_CLASS_NAME
     */
    private String dicClassName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }

    public String getDicLabel() {
        return dicLabel;
    }

    public void setDicLabel(String dicLabel) {
        this.dicLabel = dicLabel;
    }

    public String getDicDesc() {
        return dicDesc;
    }

    public void setDicDesc(String dicDesc) {
        this.dicDesc = dicDesc;
    }

    public String getDicClassName() {
        return dicClassName;
    }

    public void setDicClassName(String dicClassName) {
        this.dicClassName = dicClassName;
    }
}