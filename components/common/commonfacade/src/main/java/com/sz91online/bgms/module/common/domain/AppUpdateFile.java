package com.sz91online.bgms.module.common.domain;

import com.sz91online.common.db.entity.RootBean;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_dict_app_publish
 */
public class AppUpdateFile extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_dict_app_publish.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_dict_app_publish.app_code
     */
    private String appCode;

    /**
     *
     * 此属性对应数据库字段: t_dict_app_publish.version_name
     */
    private String versionName;

    /**
     *
     * 此属性对应数据库字段: t_dict_app_publish.version_code
     */
    private Integer versionCode;

    /**
     *
     * 此属性对应数据库字段: t_dict_app_publish.file_code
     */
    private String fileCode;

    /**
     *
     * 此属性对应数据库字段: t_dict_app_publish.create_time
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}