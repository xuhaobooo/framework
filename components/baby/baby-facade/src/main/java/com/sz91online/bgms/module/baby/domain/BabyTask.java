package com.sz91online.bgms.module.baby.domain;

import com.sz91online.common.db.entity.RootBean;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_baby_tasks
 */
public class BabyTask extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_baby_tasks.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_baby_tasks.require_code
     */
    private String requireCode;

    /**
     *
     * 此属性对应数据库字段: t_baby_tasks.send_user_code
     */
    private String sendUserCode;

    /**
     *
     * 此属性对应数据库字段: t_baby_tasks.get_user_code
     */
    private String getUserCode;

    /**
     *   NEW：订单已生成，尚未开始
            CONF：已确认
            ARRV：已到达
            PAID：已存保证金
            PF：完成待确认
            CF：完成
     *
     * 此属性对应数据库字段: t_baby_tasks.task_status
     */
    private String taskStatus;

    /**
     *
     * 此属性对应数据库字段: t_baby_tasks.start_time
     */
    private Date startTime;

    /**
     *
     * 此属性对应数据库字段: t_baby_tasks.end_time
     */
    private Date endTime;

    /**
     *
     * 此属性对应数据库字段: t_baby_tasks.create_time
     */
    private Date createTime;

    /**
     *
     * 此属性对应数据库字段: t_baby_tasks.task_code
     */
    private String taskCode;

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

    public String getGetUserCode() {
        return getUserCode;
    }

    public void setGetUserCode(String getUserCode) {
        this.getUserCode = getUserCode;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }
}