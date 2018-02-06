package com.sz91online.bgms.module.baby.domain;

import com.sz91online.common.db.entity.RootBean;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_baby_apply
 */
public class BabyApply extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_baby_apply.id
     */
    private Long id;

    /**
     *   申请用户编号
     *
     * 此属性对应数据库字段: t_baby_apply.user_code
     */
    private String userCode;

    /**
     *   申请时间
     *
     * 此属性对应数据库字段: t_baby_apply.create_time
     */
    private Date createTime;

    /**
     *   N：申请中
            D：被选中
            C：未被选中
     *
     * 此属性对应数据库字段: t_baby_apply.apply_status
     */
    private String applyStatus;

    /**
     *   需求编号
     *
     * 此属性对应数据库字段: t_baby_apply.require_code
     */
    private String requireCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getRequireCode() {
        return requireCode;
    }

    public void setRequireCode(String requireCode) {
        this.requireCode = requireCode;
    }
}