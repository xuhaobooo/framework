package com.sz91online.bgms.module.payment.domain;

import com.sz91online.common.db.entity.RootBean;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_pay_order
 */
public class PayOrder extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_pay_order.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_pay_order.from_user_code
     */
    private String fromUserCode;

    /**
     *
     * 此属性对应数据库字段: t_pay_order.to_user_code
     */
    private String toUserCode;

    /**
     *
     * 此属性对应数据库字段: t_pay_order.pay_id
     */
    private String payId;

    /**
     *
     * 此属性对应数据库字段: t_pay_order.amount
     */
    private BigDecimal amount;

    /**
     *
     * 此属性对应数据库字段: t_pay_order.unit
     */
    private String unit;

    /**
     *
     * 此属性对应数据库字段: t_pay_order.record_time
     */
    private Date recordTime;

    /**
     *   BZJ:保证金交易
            RC：业务交易
            TH：退货交易
            TX：提现交易
     *
     * 此属性对应数据库字段: t_pay_order.busi_type
     */
    private String busiType;

    /**
     *
     * 此属性对应数据库字段: t_pay_order.order_record_code
     */
    private String orderRecordCode;

    /**
     *
     * 此属性对应数据库字段: t_pay_order.create_time
     */
    private Date createTime;

    /**
     *   SUCC：成功完成
            NEW:新建
            FAIL:交易失败
     *
     * 此属性对应数据库字段: t_pay_order.order_status
     */
    private String orderStatus;

    /**
     *
     * 此属性对应数据库字段: t_pay_order.busi_code
     */
    private String busiCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromUserCode() {
        return fromUserCode;
    }

    public void setFromUserCode(String fromUserCode) {
        this.fromUserCode = fromUserCode;
    }

    public String getToUserCode() {
        return toUserCode;
    }

    public void setToUserCode(String toUserCode) {
        this.toUserCode = toUserCode;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getOrderRecordCode() {
        return orderRecordCode;
    }

    public void setOrderRecordCode(String orderRecordCode) {
        this.orderRecordCode = orderRecordCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }
}