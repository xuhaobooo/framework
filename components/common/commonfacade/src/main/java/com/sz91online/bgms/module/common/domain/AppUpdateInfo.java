package com.sz91online.bgms.module.common.domain;

import com.sz91online.common.db.entity.RootBean;

/**
 *
 * 此类对应数据库表: t_dict_app_list
 */
public class AppUpdateInfo extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_dict_app_list.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_dict_app_list.appCode
     */
    private String appcode;

    /**
     *
     * 此属性对应数据库字段: t_dict_app_list.appName
     */
    private String appname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppcode() {
        return appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }
}