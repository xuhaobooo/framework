package com.sz91online.bgms.module.baby.domain;

import com.sz91online.common.db.entity.RootBean;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_baby_task_record
 */
public class BabyTaskStep extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_baby_task_record.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_baby_task_record.step_content
     */
    private String stepContent;

    /**
     *
     * 此属性对应数据库字段: t_baby_task_record.done_time
     */
    private Date doneTime;

    /**
     *
     * 此属性对应数据库字段: t_baby_task_record.create_time
     */
    private Date createTime;

    /**
     *
     * 此属性对应数据库字段: t_baby_task_record.task_code
     */
    private String taskCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStepContent() {
        return stepContent;
    }

    public void setStepContent(String stepContent) {
        this.stepContent = stepContent;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
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