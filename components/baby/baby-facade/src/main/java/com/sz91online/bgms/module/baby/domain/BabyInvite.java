package com.sz91online.bgms.module.baby.domain;

import com.sz91online.common.db.entity.RootBean;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_baby_invite
 */
public class BabyInvite extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_baby_invite.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_baby_invite.user_code
     */
    private String userCode;

    /**
     *
     * 此属性对应数据库字段: t_baby_invite.invite_user_code
     */
    private String inviteUserCode;

    /**
     *
     * 此属性对应数据库字段: t_baby_invite.create_time
     */
    private Date createTime;

    /**
     *
     * 此属性对应数据库字段: t_baby_invite.enable
     */
    private Boolean enable;

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

    public String getInviteUserCode() {
        return inviteUserCode;
    }

    public void setInviteUserCode(String inviteUserCode) {
        this.inviteUserCode = inviteUserCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}