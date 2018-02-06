package com.sz91online.bgms.module.payment.wechat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 异步通知返回的dto
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotifyMessageDto {
	// 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
	@JsonProperty("is_subscribe")
	private String isSubscribe;
	// 微信开放平台审核通过的应用APPID
	@JsonProperty("appid")
	private String appid;
	// 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
	@JsonProperty("fee_type")
	private String feeType;
	// 随机字符串，不长于32位
	@JsonProperty("nonce_str")
	private String nonceStr;
	// 商户系统的订单号，与请求一致。
	@JsonProperty("out_trade_no")
	private String outTradeNo;
	// 微信支付订单号
	@JsonProperty("transaction_id")
	private String transactionId;
	// APP
	@JsonProperty("trade_type")
	private String tradeType;
	// SUCCESS/FAIL
	@JsonProperty("result_code")
	private String resultCode;
	@JsonProperty("err_code")
	private String errCode;
	@JsonProperty("err_code_des")
	private String errCodeDes;
	// 签名，详见签名算法
	@JsonProperty("sign")
	private String sign;
	// 微信支付分配的商户号
	@JsonProperty("mch_id")
	private String mchIid;
	// 订单总金额，单位为分
	@JsonProperty("total_fee")
	private String totalFee;
	// 支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	@JsonProperty("time_end")
	private String timeEnd;
	// 用户在商户appid下的唯一标识
	@JsonProperty("openid")
	private String openid;
	// 银行类型，采用字符串类型的银行标识，银行类型见银行列表
	@JsonProperty("bank_type")
	private String bankType;
	// 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
	@JsonProperty("return_code")
	private String returnCode;
	@JsonProperty("return_msg")
	private String returnMsg;

	// 现金支付金额订单现金支付金额，详见支付金额
	@JsonProperty("cash_fee")
	private String cashFee;

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getMchIid() {
		return mchIid;
	}

	public void setMchIid(String mchIid) {
		this.mchIid = mchIid;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getCashFee() {
		return cashFee;
	}

	public void setCashFee(String cashFee) {
		this.cashFee = cashFee;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

}
