package com.sz91online.bgms.module.user.domain;

import com.sz91online.common.db.entity.RootBean;
import java.util.Date;

/**
 *
 * 此类对应数据库表: T_BU_LEAVE
 */
public class Leave extends RootBean {
    /**
     *
     * 此属性对应数据库字段: T_BU_LEAVE.ID
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: T_BU_LEAVE.USER_CODE
     */
    private String userCode;

    /**
     *
     * 此属性对应数据库字段: T_BU_LEAVE.LEAVE_DATE
     */
    private Date leaveDate;

    /**
     *
     * 此属性对应数据库字段: T_BU_LEAVE.DAYS
     */
    private Integer days;

    /**
     *
     * 此属性对应数据库字段: T_BU_LEAVE.READSON
     */
    private String readson;

    /**
     *
     * 此属性对应数据库字段: T_BU_LEAVE.LEAVE_TYPE
     */
    private String leaveType;

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

    public Date getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(Date leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getReadson() {
        return readson;
    }

    public void setReadson(String readson) {
        this.readson = readson;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }
}