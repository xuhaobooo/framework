package com.sz91online.bgms.module.baby.domain;

import com.sz91online.common.db.entity.RootBean;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_baby_payment_record
 */
public class BabyPayment extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_baby_payment_record.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_baby_payment_record.payment_type
     */
    private String paymentType;

    /**
     *
     * 此属性对应数据库字段: t_baby_payment_record.amount
     */
    private Float amount;

    /**
     *
     * 此属性对应数据库字段: t_baby_payment_record.create_time
     */
    private Date createTime;

    /**
     *   NEW:创建，无反馈
            SUCC:支付成功
            FAIL:支付失败
            FIN:已完成
     *
     * 此属性对应数据库字段: t_baby_payment_record.payment_status
     */
    private String paymentStatus;

    /**
     *
     * 此属性对应数据库字段: t_baby_payment_record.last_update_time
     */
    private Date lastUpdateTime;

    /**
     *
     * 此属性对应数据库字段: t_baby_payment_record.busi_payment_code1
     */
    private String busiPaymentCode1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getBusiPaymentCode1() {
        return busiPaymentCode1;
    }

    public void setBusiPaymentCode1(String busiPaymentCode1) {
        this.busiPaymentCode1 = busiPaymentCode1;
    }
}