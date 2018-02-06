package com.sz91online.bgms.module.payment.service;

import java.util.Map;

import com.sz91online.bgms.module.payment.domain.PayPayment;

public interface ThirdPayService {

	// 支付前的签名
	public Map<String, String> signature(Map<String, String> map, PayPayment payment);

	// 支付后的第三方回调
	public String callback(Map<String, String> paramMap);
}
