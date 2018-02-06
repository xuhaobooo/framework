package com.sz91online.bgms.module.user.domain;

import com.sz91online.common.db.entity.RootBean;

/**
 *
 * 此类对应数据库表: t_u_auth
 */
public class Auth extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_u_auth.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_u_auth.auth_code
     */
    private String authCode;

    /**
     *
     * 此属性对应数据库字段: t_u_auth.auth_name
     */
    private String authName;

    /**
     *
     * 此属性对应数据库字段: t_u_auth.auth_desc
     */
    private String authDesc;

    /**
     *   1为部门权限，主要用于工作流
            0为全局权限，主要用于菜单控制
     *
     * 此属性对应数据库字段: t_u_auth.auth_type
     */
    private Boolean authType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public String getAuthDesc() {
        return authDesc;
    }

    public void setAuthDesc(String authDesc) {
        this.authDesc = authDesc;
    }

    public Boolean getAuthType() {
        return authType;
    }

    public void setAuthType(Boolean authType) {
        this.authType = authType;
    }
}