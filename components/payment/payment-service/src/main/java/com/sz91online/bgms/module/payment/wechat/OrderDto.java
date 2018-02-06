package com.sz91online.bgms.module.payment.wechat;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto{
   //应用ID
    @XStreamAlias("appid")
    @JsonProperty("appid")
    String appid;
    //附加数据
    @JsonProperty("attach")
    @XStreamAlias("attach")
    String attach;
    //商品描述
    @JsonProperty("body")
    @XStreamAlias("body")
    String body;
    //商品详情
    @JsonProperty("detail")
    @XStreamAlias("detail")
    String detail;
    //设备号
    @JsonProperty("device_info")
    @XStreamAlias("device_info")
    String deviceInfo;
    //货币类型
    @JsonProperty("fee_type")
    @XStreamAlias("fee_type")
    String feeType;
    //商品标记
    @JsonProperty("goods_tag")
    @XStreamAlias("goods_tag")
    String goodsTag;
    //指定支付方式
    @JsonProperty("limit_pay")
    @XStreamAlias("limit_pay")
    String limitPay;
    //商户号
    @JsonProperty("mch_id")
    @XStreamAlias("mch_id")
    String mchId;
    //随机字符串
    @JsonProperty("nonce_str")
    @XStreamAlias("nonce_str")
    String nonceStr;

    //通知地址
    @JsonProperty("notify_url")
    @XStreamAlias("notify_url")
    String notifyUrl;
    //商户订单号
    @JsonProperty("out_trade_no")
    @XStreamAlias("out_trade_no")
    String outTradeNo;

    //用户端IP
    @JsonProperty("spbill_create_ip")
    @XStreamAlias("spbill_create_ip")
    String spbillCreateIp;
    //交易结束时间
    @JsonProperty("time_expire")
    @XStreamAlias("time_expire")
    String timeExpire;
    //交易起始时间
    @JsonProperty("time_start")
    @XStreamAlias("time_start")
    String timeStart;
    //总金额
    @JsonProperty("total_fee")
    @XStreamAlias("total_fee")
    int totalFee;
    //交易类型
    @JsonProperty("trade_type")
    @XStreamAlias("trade_type")
    String tradeType;
    //签名
    @JsonProperty("sign")
    @XStreamAlias("sign")
    String sign;

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeExpire() {
        return timeExpire;
    }

    public void setTimeExpire(String timeExpire) {
        this.timeExpire = timeExpire;
    }

    public String getGoodsTag() {
        return goodsTag;
    }

    public void setGoodsTag(String goodsTag) {
        this.goodsTag = goodsTag;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getLimitPay() {
        return limitPay;
    }

    public void setLimitPay(String limitPay) {
        this.limitPay = limitPay;
    }
}
