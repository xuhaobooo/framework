package com.sz91online.bgms.module.payment.domain;

import com.sz91online.common.db.entity.RootBean;

/**
 *
 * 此类对应数据库表: t_pay_user_bank
 */
public class PayUserBank extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_pay_user_bank.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_pay_user_bank.user_code
     */
    private String userCode;

    /**
     *
     * 此属性对应数据库字段: t_pay_user_bank.account_name
     */
    private String accountName;

    /**
     *
     * 此属性对应数据库字段: t_pay_user_bank.bank
     */
    private String bank;

    /**
     *
     * 此属性对应数据库字段: t_pay_user_bank.account_no
     */
    private String accountNo;

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

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
}