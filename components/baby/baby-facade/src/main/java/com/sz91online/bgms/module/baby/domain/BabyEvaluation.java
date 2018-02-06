package com.sz91online.bgms.module.baby.domain;

import com.sz91online.common.db.entity.RootBean;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_baby_evaluation
 */
public class BabyEvaluation extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_baby_evaluation.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_baby_evaluation.require_code
     */
    private String requireCode;

    /**
     *
     * 此属性对应数据库字段: t_baby_evaluation.send_user_code
     */
    private String sendUserCode;

    /**
     *
     * 此属性对应数据库字段: t_baby_evaluation.receive_user_code
     */
    private String receiveUserCode;

    /**
     *
     * 此属性对应数据库字段: t_baby_evaluation.level
     */
    private String level;

    /**
     *
     * 此属性对应数据库字段: t_baby_evaluation.create_time
     */
    private Date createTime;

    /**
     *
     * 此属性对应数据库字段: t_baby_evaluation.notes
     */
    private String notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequireCode() {
        return requireCode;
    }

    public void setRequireCode(String requireCode) {
        this.requireCode = requireCode;
    }

    public String getSendUserCode() {
        return sendUserCode;
    }

    public void setSendUserCode(String sendUserCode) {
        this.sendUserCode = sendUserCode;
    }

    public String getReceiveUserCode() {
        return receiveUserCode;
    }

    public void setReceiveUserCode(String receiveUserCode) {
        this.receiveUserCode = receiveUserCode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}