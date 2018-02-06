package com.sz91online.bgms.module.payment.domain;

import com.sz91online.common.db.entity.RootBean;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_pay_withdraw
 */
public class PayWithdraw extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.wd_account_name
     */
    private String wdAccountName;

    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.wd_bank
     */
    private String wdBank;

    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.wd_account_no
     */
    private String wdAccountNo;

    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.wd_amount
     */
    private BigDecimal wdAmount;

    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.wd_unit
     */
    private String wdUnit;

    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.wd_init_time
     */
    private Date wdInitTime;

    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.wd_status
     */
    private String wdStatus;

    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.wd_result_time
     */
    private Date wdResultTime;

    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.wd_error_code
     */
    private String wdErrorCode;

    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.wd_way
     */
    private String wdWay;

    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.pay_bank_id
     */
    private String payBankId;

    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.busi_user_code
     */
    private String busiUserCode;

    /**
     *
     * 此属性对应数据库字段: t_pay_withdraw.wd_code
     */
    private String wdCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWdAccountName() {
        return wdAccountName;
    }

    public void setWdAccountName(String wdAccountName) {
        this.wdAccountName = wdAccountName;
    }

    public String getWdBank() {
        return wdBank;
    }

    public void setWdBank(String wdBank) {
        this.wdBank = wdBank;
    }

    public String getWdAccountNo() {
        return wdAccountNo;
    }

    public void setWdAccountNo(String wdAccountNo) {
        this.wdAccountNo = wdAccountNo;
    }

    public BigDecimal getWdAmount() {
        return wdAmount;
    }

    public void setWdAmount(BigDecimal wdAmount) {
        this.wdAmount = wdAmount;
    }

    public String getWdUnit() {
        return wdUnit;
    }

    public void setWdUnit(String wdUnit) {
        this.wdUnit = wdUnit;
    }

    public Date getWdInitTime() {
        return wdInitTime;
    }

    public void setWdInitTime(Date wdInitTime) {
        this.wdInitTime = wdInitTime;
    }

    public String getWdStatus() {
        return wdStatus;
    }

    public void setWdStatus(String wdStatus) {
        this.wdStatus = wdStatus;
    }

    public Date getWdResultTime() {
        return wdResultTime;
    }

    public void setWdResultTime(Date wdResultTime) {
        this.wdResultTime = wdResultTime;
    }

    public String getWdErrorCode() {
        return wdErrorCode;
    }

    public void setWdErrorCode(String wdErrorCode) {
        this.wdErrorCode = wdErrorCode;
    }

    public String getWdWay() {
        return wdWay;
    }

    public void setWdWay(String wdWay) {
        this.wdWay = wdWay;
    }

    public String getPayBankId() {
        return payBankId;
    }

    public void setPayBankId(String payBankId) {
        this.payBankId = payBankId;
    }

    public String getBusiUserCode() {
        return busiUserCode;
    }

    public void setBusiUserCode(String busiUserCode) {
        this.busiUserCode = busiUserCode;
    }

    public String getWdCode() {
        return wdCode;
    }

    public void setWdCode(String wdCode) {
        this.wdCode = wdCode;
    }
}