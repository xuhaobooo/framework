package com.sz91online.bgms.module.payment.domain;

import com.sz91online.common.db.entity.RootBean;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_pay_payment
 */
public class PayPayment extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_pay_payment.id
     */
    private Long id;

    /**
     *   支付相关的业务主键
     *
     * 此属性对应数据库字段: t_pay_payment.busi_code
     */
    private String busiCode;

    /**
     *   支付相关的业务用户主键
     *
     * 此属性对应数据库字段: t_pay_payment.pay_user_code
     */
    private String payUserCode;

    /**
     *   微信：WX
            支付宝：ALI
     *
     * 此属性对应数据库字段: t_pay_payment.pay_status
     */
    private String payStatus;

    /**
     *
     * 此属性对应数据库字段: t_pay_payment.pay_way
     */
    private String payWay;

    /**
     *
     * 此属性对应数据库字段: t_pay_payment.pay_amount
     */
    private BigDecimal payAmount;

    /**
     *
     * 此属性对应数据库字段: t_pay_payment.pay_unit
     */
    private String payUnit;

    /**
     *   支付平台返回的用户ID
     *
     * 此属性对应数据库字段: t_pay_payment.payee_user_id
     */
    private String payeeUserId;

    /**
     *
     * 此属性对应数据库字段: t_pay_payment.payee_user_name
     */
    private String payeeUserName;

    /**
     *
     * 此属性对应数据库字段: t_pay_payment.payee_id
     */
    private String payeeId;

    /**
     *
     * 此属性对应数据库字段: t_pay_payment.payee_account_no
     */
    private String payeeAccountNo;

    /**
     *
     * 此属性对应数据库字段: t_pay_payment.pay_init_time
     */
    private Date payInitTime;

    /**
     *
     * 此属性对应数据库字段: t_pay_payment.payee_result_time
     */
    private Date payeeResultTime;

    /**
     *
     * 此属性对应数据库字段: t_pay_payment.payee_error_code
     */
    private String payeeErrorCode;

    /**
     *
     * 此属性对应数据库字段: t_pay_payment.pay_id
     */
    private String payId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusiCode() {
        return busiCode;
    }

    public void setBusiCode(String busiCode) {
        this.busiCode = busiCode;
    }

    public String getPayUserCode() {
        return payUserCode;
    }

    public void setPayUserCode(String payUserCode) {
        this.payUserCode = payUserCode;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayUnit() {
        return payUnit;
    }

    public void setPayUnit(String payUnit) {
        this.payUnit = payUnit;
    }

    public String getPayeeUserId() {
        return payeeUserId;
    }

    public void setPayeeUserId(String payeeUserId) {
        this.payeeUserId = payeeUserId;
    }

    public String getPayeeUserName() {
        return payeeUserName;
    }

    public void setPayeeUserName(String payeeUserName) {
        this.payeeUserName = payeeUserName;
    }

    public String getPayeeId() {
        return payeeId;
    }

    public void setPayeeId(String payeeId) {
        this.payeeId = payeeId;
    }

    public String getPayeeAccountNo() {
        return payeeAccountNo;
    }

    public void setPayeeAccountNo(String payeeAccountNo) {
        this.payeeAccountNo = payeeAccountNo;
    }

    public Date getPayInitTime() {
        return payInitTime;
    }

    public void setPayInitTime(Date payInitTime) {
        this.payInitTime = payInitTime;
    }

    public Date getPayeeResultTime() {
        return payeeResultTime;
    }

    public void setPayeeResultTime(Date payeeResultTime) {
        this.payeeResultTime = payeeResultTime;
    }

    public String getPayeeErrorCode() {
        return payeeErrorCode;
    }

    public void setPayeeErrorCode(String payeeErrorCode) {
        this.payeeErrorCode = payeeErrorCode;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }
}