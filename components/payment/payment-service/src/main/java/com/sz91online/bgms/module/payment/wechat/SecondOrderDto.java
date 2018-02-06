package com.sz91online.bgms.module.payment.wechat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 二次签名时使用
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SecondOrderDto {
    private String appid;

    private String partnerid;

    private String noncestr;

    @JsonProperty("package")
    private String packagevalue="Sign=WXPay";

    private String timestamp;

    private String prepayid;

    private String sign;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackagevalue() {
        return packagevalue;
    }

    public void setPackagevalue(String packagevalue) {
        this.packagevalue = packagevalue;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
