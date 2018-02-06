package com.sz91online.bgms.module.payment.alipay;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlipayBaseInfo {
    @JsonProperty("sign")
    private String sign;
    @JsonProperty("seller_id")
    private String sellerId;
    @JsonProperty("out_trade_no")
    private String outTradeNo;
    @JsonProperty("partner")
    private String partner;
    @JsonProperty("notify_url")
    private String notifyUrl;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }
}
