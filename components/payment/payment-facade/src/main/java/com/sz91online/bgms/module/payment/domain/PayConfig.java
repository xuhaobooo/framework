package com.sz91online.bgms.module.payment.domain;

import com.sz91online.common.db.entity.RootBean;

/**
 *
 * 此类对应数据库表: t_pay_config
 */
public class PayConfig extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_pay_config.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_pay_config.value
     */
    private String value;

    /**
     *
     * 此属性对应数据库字段: t_pay_config.conf_desc
     */
    private String confDesc;

    /**
     *
     * 此属性对应数据库字段: t_pay_config.code
     */
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getConfDesc() {
        return confDesc;
    }

    public void setConfDesc(String confDesc) {
        this.confDesc = confDesc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}