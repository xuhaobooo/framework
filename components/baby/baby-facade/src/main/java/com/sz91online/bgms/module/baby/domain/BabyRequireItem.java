package com.sz91online.bgms.module.baby.domain;

import com.sz91online.common.db.entity.RootBean;

/**
 *
 * 此类对应数据库表: t_baby_require_items
 */
public class BabyRequireItem extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_baby_require_items.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_baby_require_items.item_code
     */
    private String itemCode;

    /**
     *
     * 此属性对应数据库字段: t_baby_require_items.item_name
     */
    private String itemName;

    /**
     *
     * 此属性对应数据库字段: t_baby_require_items.item_price
     */
    private Float itemPrice;

    /**
     *
     * 此属性对应数据库字段: t_baby_require_items.require_code
     */
    private String requireCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
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

    public String getRequireCode() {
        return requireCode;
    }

    public void setRequireCode(String requireCode) {
        this.requireCode = requireCode;
    }
}