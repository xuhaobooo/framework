package com.sz91online.bgms.module.payment.alipay;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 支付宝通知实体 Created by wendajun on 2016/4/28.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotifyMessageDto {
	@JsonProperty("app_id")
	private String appId;
	@JsonProperty("notify_time")
	private String notifyTime;
	@JsonProperty("notify_type")
	private String notifyType;
	@JsonProperty("out_trade_no")
	private String outTradeNo;
	@JsonProperty("notify_id")
	private String notifyId;
	@JsonProperty("trade_status")
	private String tradeStatus;
	@JsonProperty("buyer_id")
	private String buyerId;
	// 支付唯一id
	@JsonProperty("trade_no")
	private String tradeNo;
	@JsonProperty("buyer_email")
	private String buyerEmail;
	@JsonProperty("gmt_create")
	private String gmtCreate;

	private String body;
	@JsonProperty("use_coupon")
	private String userCoupon;
	@JsonProperty("subject")
	private String subject;
	@JsonProperty("sign_type")
	private String signType;
	@JsonProperty("is_total_fee_adjust")
	private String isTotalFeeAdjust;
	@JsonProperty("total_fee")
	private String totalFee;
	@JsonProperty("quantity")
	private String quantity;
	@JsonProperty("discount")
	private String discount;
	@JsonProperty("sign")
	private String sign;
	private String price;
	@JsonProperty("seller_id")
	private String sellerId;
	@JsonProperty("seller_email")
	private String sellerEmail;
	@JsonProperty("payment_type")
	private String paymentType;
	@JsonProperty("gmt_payment")
	private String gmtPayment;

	public String getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public String getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(String gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getUserCoupon() {
		return userCoupon;
	}

	public void setUserCoupon(String userCoupon) {
		this.userCoupon = userCoupon;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getIsTotalFeeAdjust() {
		return isTotalFeeAdjust;
	}

	public void setIsTotalFeeAdjust(String isTotalFeeAdjust) {
		this.isTotalFeeAdjust = isTotalFeeAdjust;
	}

	public String getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(String gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

}
