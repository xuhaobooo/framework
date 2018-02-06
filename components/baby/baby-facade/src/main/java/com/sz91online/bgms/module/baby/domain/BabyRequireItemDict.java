package com.sz91online.bgms.module.baby.domain;

import com.sz91online.common.db.entity.RootBean;

/**
 *
 * 此类对应数据库表: t_baby_item_list
 */
public class BabyRequireItemDict extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_baby_item_list.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_baby_item_list.item_name
     */
    private String itemName;

    /**
     *
     * 此属性对应数据库字段: t_baby_item_list.item_price
     */
    private Float itemPrice;

    /**
     *
     * 此属性对应数据库字段: t_baby_item_list.enable_flag
     */
    private Boolean enableFlag;

    /**
     *
     * 此属性对应数据库字段: t_baby_item_list.item_code
     */
    private String itemCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Boolean getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }
}