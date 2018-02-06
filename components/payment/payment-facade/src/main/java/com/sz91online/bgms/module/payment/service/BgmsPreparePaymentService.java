package com.sz91online.bgms.module.payment.service;

import java.util.Map;

import com.sz91online.bgms.module.payment.domain.SimplePayPayment;
import com.sz91online.bgms.module.payment.utils.ServerInfo;

public interface BgmsPreparePaymentService {

	/**
	 * 
	 * @param userCode
	 *            发起交易用户
	 * @param toUserCode
	 *            目标用户
	 * @param busiCode
	 *            业务主键
	 * @param busiType
	 *            业务类型, {@link om.sz91online.bgms.module.payment.domain.PayOrder}
	 *            类的busiType字段注释
	 * @param payId
	 *            业务系统生成的交易记录唯一编号。
	 * @param amount
	 *            交易金额
	 * @param unit
	 *            金额单位
	 * @param recordTime
	 *            交易发生时间
	 * @param payType
	 *            交易方式， WC为微信， ALI为支付宝，EB为银联， BL为余额
	 * @param serverInfo
	 *            服务器信息，用于拼接回调路径
	 */
	Map<String, String> pay(SimplePayPayment simplePayPayment, ServerInfo serverInfo);

}
