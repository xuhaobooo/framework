package com.sz91online.bgms.module.tracer.domain;

import com.sz91online.common.db.entity.RootBean;
import java.util.Date;

/**
 *
 * 此类对应数据库表: T_LOG_T_TRACER
 */
public class Tracer extends RootBean {
    /**
     *
     * 此属性对应数据库字段: T_LOG_T_TRACER.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: T_LOG_T_TRACER.module
     */
    private String module;

    /**
     *
     * 此属性对应数据库字段: T_LOG_T_TRACER.sub_module
     */
    private String subModule;

    /**
     *
     * 此属性对应数据库字段: T_LOG_T_TRACER.business_id
     */
    private Long businessId;

    /**
     *
     * 此属性对应数据库字段: T_LOG_T_TRACER.created_time
     */
    private Date createdTime;

    /**
     *
     * 此属性对应数据库字段: T_LOG_T_TRACER.action
     */
    private String action;

    /**
     *
     * 此属性对应数据库字段: T_LOG_T_TRACER.created_user
     */
    private String createdUser;

    /**
     *   用来存贮扩展数据，可用作数据关联
     *
     * 此属性对应数据库字段: T_LOG_T_TRACER.extra_id
     */
    private Long extraId;

    /**
     *
     * 此属性对应数据库字段: T_LOG_T_TRACER.content
     */
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getSubModule() {
        return subModule;
    }

    public void setSubModule(String subModule) {
        this.subModule = subModule;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Long getExtraId() {
        return extraId;
    }

    public void setExtraId(Long extraId) {
        this.extraId = extraId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}