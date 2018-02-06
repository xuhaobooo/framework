package com.sz91online.bgms.module.baby.domain;

import com.sz91online.common.db.entity.RootBean;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_baby_time_price
 */
public class BabyTimePrice extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_baby_time_price.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_baby_time_price.price
     */
    private Double price;

    /**
     *
     * 此属性对应数据库字段: t_baby_time_price.last_update_time
     */
    private Date lastUpdateTime;

    /**
     *
     * 此属性对应数据库字段: t_baby_time_price.price_code
     */
    private String priceCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(String priceCode) {
        this.priceCode = priceCode;
    }
}