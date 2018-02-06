package com.sz91online.bgms.module.payment.domain;

import com.sz91online.common.db.entity.RootBean;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_pay_money_flow
 */
public class PayMoneyFlow extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_pay_money_flow.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_pay_money_flow.amount
     */
    private BigDecimal amount;

    /**
     *
     * 此属性对应数据库字段: t_pay_money_flow.unit
     */
    private String unit;

    /**
     *
     * 此属性对应数据库字段: t_pay_money_flow.record_time
     */
    private Date recordTime;

    /**
     *
     * 此属性对应数据库字段: t_pay_money_flow.order_record_code
     */
    private String orderRecordCode;

    /**
     *   BZJ:保证金交易
            RC：业务交易
            TH：退货交易
            TX：提现交易
     *
     * 此属性对应数据库字段: t_pay_money_flow.busi_type
     */
    private String busiType;

    /**
     *
     * 此属性对应数据库字段: t_pay_money_flow.busi_user_code
     */
    private String busiUserCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getOrderRecordCode() {
        return orderRecordCode;
    }

    public void setOrderRecordCode(String orderRecordCode) {
        this.orderRecordCode = orderRecordCode;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getBusiUserCode() {
        return busiUserCode;
    }

    public void setBusiUserCode(String busiUserCode) {
        this.busiUserCode = busiUserCode;
    }
}