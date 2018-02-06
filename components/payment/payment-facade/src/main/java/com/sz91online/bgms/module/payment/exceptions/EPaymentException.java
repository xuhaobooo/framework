package com.sz91online.bgms.module.payment.exceptions;

import com.sz91online.common.exceptions.EBusinessException;

public class EPaymentException extends EBusinessException{
	
	private static final long serialVersionUID = -2524275212267006703L;
	
	public EPaymentException(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}
	
	public static final EPaymentException PAYMENT_FAIL = new EPaymentException("10330001", "支付失败!");
	public static final EPaymentException PAYMENT_TYPE_NOT_ACCEPT = new EPaymentException("10330002", "不支持的支付方式!");
	public static final EPaymentException PAYMENT_TYPE_MISS = new EPaymentException("10330003", "没有支付类型参数!");
	public static final EPaymentException PAYMENT_SIGN_ERROR = new EPaymentException("10330004", "签名信息错误!");
	public static final EPaymentException PAYMENT_URLCODE_ERROR = new EPaymentException("10330005", "生成验证信息，URLEcode时发生错误!");
	public static final EPaymentException PAYMENT_WECHAT_BODY_MISS = new EPaymentException("10330006", "微信支付验证时没有发现body参数！");
	public static final EPaymentException PAYMENT_WECHAT_SERVER_VALID_ERROR = new EPaymentException("10330007", "微信支付验证时服务器返回失败代码！");
	public static final EPaymentException PAYMENT_SIGN_SYSTEM_ERROR = new EPaymentException("10330008", "支付验证时出现未知错误！");
	public static final EPaymentException PAYMENT_RECORD_NOT_EXIST = new EPaymentException("10330009", "支付记录不存在！");
	public static final EPaymentException NOT_ENOUGH_MONEY = new EPaymentException("10330010", "余额不足！");
	
	public static final EPaymentException BALANCE_ACCOUNT_NOT_EXIST = new EPaymentException("10330011", "余额账号不存在，请联系管理员！");
	public static final EPaymentException HAVE_BEEN_PAID = new EPaymentException("10330012", "订单已被支付成功，不能重复支付！");
	public static final EPaymentException WITHDRAW_MUST_BIGGER_THAN_ZERO = new EPaymentException("10330013", "提现金额必须大于零");
	public static final EPaymentException WITHDRAW_NOT_EXIST = new EPaymentException("10330014", "提现记录不存在");
	public static final EPaymentException WITHDRAW_STATUS_NOT_CORRECT = new EPaymentException("10330015", "提现状态不正确");
	public static final EPaymentException WITHDRAW_DONE_NEED_BANKID = new EPaymentException("10330016", "执行提现确认需要支付流水单号");
}
