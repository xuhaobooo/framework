package com.sz91online.bgms.module.baby.domain;

import com.sz91online.common.db.entity.RootBean;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_baby_require
 */
public class BabyRequire extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_baby_require.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_baby_require.start_time
     */
    private Date startTime;

    /**
     *
     * 此属性对应数据库字段: t_baby_require.end_time
     */
    private Date endTime;

    /**
     *
     * 此属性对应数据库字段: t_baby_require.addr_name
     */
    private String addrName;

    /**
     *
     * 此属性对应数据库字段: t_baby_require.addr_pos_x
     */
    private Double addrPosX;

    /**
     *
     * 此属性对应数据库字段: t_baby_require.addr_pos_y
     */
    private Double addrPosY;

    /**
     *
     * 此属性对应数据库字段: t_baby_require.baby_name
     */
    private String babyName;

    /**
     *
     * 此属性对应数据库字段: t_baby_require.baby_age
     */
    private Integer babyAge;

    /**
     *   F为女，M为男
     *
     * 此属性对应数据库字段: t_baby_require.baby_sex
     */
    private String babySex;

    /**
     *
     * 此属性对应数据库字段: t_baby_require.create_time
     */
    private Date createTime;

    /**
     *
     * 此属性对应数据库字段: t_baby_require.require_code
     */
    private String requireCode;

    /**
     *
     * 此属性对应数据库字段: t_baby_require.credit_code
     */
    private String creditCode;

    /**
     *
     * 此属性对应数据库字段: t_baby_require.pay_more
     */
    private Float payMore;

    /**
     *
     * 此属性对应数据库字段: t_baby_require.fee_amount
     */
    private Float feeAmount;

    /**
     *   NEW：订单已生成，尚未开始
            CONF：已确认
            ARRV：已到达
            PF：机构确认完成
            CF：用户确认完成
            AF: 订单完成
     *
     * 此属性对应数据库字段: t_baby_require.require_status
     */
    private String requireStatus;

    /**
     *   关联T_U_USER中的用户编号
     *
     * 此属性对应数据库字段: t_baby_require.user_code
     */
    private String userCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAddrName() {
        return addrName;
    }

    public void setAddrName(String addrName) {
        this.addrName = addrName;
    }

    public Double getAddrPosX() {
        return addrPosX;
    }

    public void setAddrPosX(Double addrPosX) {
        this.addrPosX = addrPosX;
    }

    public Double getAddrPosY() {
        return addrPosY;
    }

    public void setAddrPosY(Double addrPosY) {
        this.addrPosY = addrPosY;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public Integer getBabyAge() {
        return babyAge;
    }

    public void setBabyAge(Integer babyAge) {
        this.babyAge = babyAge;
    }

    public String getBabySex() {
        return babySex;
    }

    public void setBabySex(String babySex) {
        this.babySex = babySex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRequireCode() {
        return requireCode;
    }

    public void setRequireCode(String requireCode) {
        this.requireCode = requireCode;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public Float getPayMore() {
        return payMore;
    }

    public void setPayMore(Float payMore) {
        this.payMore = payMore;
    }

    public Float getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Float feeAmount) {
        this.feeAmount = feeAmount;
    }

    public String getRequireStatus() {
        return requireStatus;
    }

    public void setRequireStatus(String requireStatus) {
        this.requireStatus = requireStatus;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}