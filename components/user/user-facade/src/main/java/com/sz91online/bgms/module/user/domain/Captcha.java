package com.sz91online.bgms.module.user.domain;

import com.sz91online.common.db.entity.RootBean;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_u_captcha
 */
public class Captcha extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_u_captcha.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_u_captcha.mobile_phone
     */
    private String mobilePhone;

    /**
     *
     * 此属性对应数据库字段: t_u_captcha.captcha
     */
    private String captcha;

    /**
     *
     * 此属性对应数据库字段: t_u_captcha.create_time
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}