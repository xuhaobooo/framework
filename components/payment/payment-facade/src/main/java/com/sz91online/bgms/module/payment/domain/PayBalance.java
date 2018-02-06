package com.sz91online.bgms.module.payment.domain;

import com.sz91online.common.db.entity.RootBean;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_pay_balance
 */
public class PayBalance extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_pay_balance.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_pay_balance.balance_amount
     */
    private BigDecimal balanceAmount;

    /**
     *
     * 此属性对应数据库字段: t_pay_balance.last_modify_date
     */
    private Date lastModifyDate;

    /**
     *
     * 此属性对应数据库字段: t_pay_balance.user_code
     */
    private String userCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Date getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}