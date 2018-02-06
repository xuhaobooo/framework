package com.sz91online.bgms.module.payment.service.mybatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sz91online.bgms.eventbus.MyEventBus;
import com.sz91online.bgms.module.payment.domain.PayBalance;
import com.sz91online.bgms.module.payment.domain.PayOrder;
import com.sz91online.bgms.module.payment.domain.PayPayment;
import com.sz91online.bgms.module.payment.domain.SimplePayPayment;
import com.sz91online.bgms.module.payment.enums.PaymentStatusEnum;
import com.sz91online.bgms.module.payment.enums.PaymentTypeEnum;
import com.sz91online.bgms.module.payment.exceptions.EPaymentException;
import com.sz91online.bgms.module.payment.service.BgmsPreparePaymentService;
import com.sz91online.bgms.module.payment.service.PayBalanceService;
import com.sz91online.bgms.module.payment.service.PayOrderService;
import com.sz91online.bgms.module.payment.service.PayPaymentService;
import com.sz91online.bgms.module.payment.service.ThirdPayService;
import com.sz91online.bgms.module.payment.utils.PaymentConstants;
import com.sz91online.bgms.module.payment.utils.PaymentIdWork;
import com.sz91online.bgms.module.payment.utils.ServerInfo;

@Service
public class BgmsPreparePaymentServiceImpl implements BgmsPreparePaymentService {

	Logger logger = LoggerFactory.getLogger(BgmsPreparePaymentServiceImpl.class);

	@Autowired
	private PayOrderService payOrderService;

	@Autowired
	private PayPaymentService payPaymentService;

	@Autowired
	private PaymentConstants paymentConstants;

	@Autowired
	private PayBalanceService payBalanceService;

	@Autowired
	private MyEventBus eventBus;

	@Autowired
	// 指向支付宝
	@Qualifier("aliPayService")
	ThirdPayService aliPayService;

	@Autowired
	// 指向微信支付
	@Qualifier("weChatPayService")
	ThirdPayService weChatPayService;
	
	@Override
	@Transactional
	public Map<String, String> pay(SimplePayPayment simplePayPayment, ServerInfo serverInfo) {

		Date recordTime = new Date();

		// 检验参数
		Assert.notNull(simplePayPayment, "参数对象不能为空");
		Assert.notNull(simplePayPayment.getPayUserCode(), "发起交易的用户不能能为空");

		Assert.notNull(simplePayPayment.getBusiType(), "业务类型不能能为空");
		Assert.notNull(simplePayPayment.getPayAmount(), "金额不能为空");
		Assert.notNull(simplePayPayment.getPayUnit(), "金额单位不能能为空");

		Assert.notNull(simplePayPayment.getBusiCode(), "业务编号不能为空！");
		
		PayPayment queryPayement = new PayPayment();
		queryPayement.setBusiCode(simplePayPayment.getBusiCode());
		queryPayement.setPayStatus(PaymentStatusEnum.SUCC.getValue());
		List<PayPayment> resultPayment = payPaymentService.findBySelective(queryPayement);
		if(resultPayment != null && resultPayment.size() > 0) {
			throw EPaymentException.HAVE_BEEN_PAID;
		}

		// 先存支付信息
		PayPayment payment = new PayPayment();
		payment.setPayId(new PaymentIdWork().nextString());
		payment.setBusiCode(simplePayPayment.getBusiCode());
		payment.setPayUserCode(simplePayPayment.getPayUserCode());
		payment.setPayStatus(PaymentStatusEnum.NEW.getValue());
		payment.setPayWay(simplePayPayment.getPayWay());
		payment.setPayAmount(simplePayPayment.getPayAmount());
		payment.setPayUnit(simplePayPayment.getPayUnit());
		payment.setPayInitTime(recordTime);
		payPaymentService.saveWithSession(payment, "PaySystem");

		// 再存交易记录
		PayOrder saveBean = new PayOrder();
		saveBean.setOrderRecordCode("OR" + new PaymentIdWork().nextId());
		saveBean.setPayId(payment.getPayId());
		saveBean.setFromUserCode(simplePayPayment.getPayUserCode());
		saveBean.setBusiCode(simplePayPayment.getBusiCode());
		saveBean.setBusiType(simplePayPayment.getBusiType());
		saveBean.setAmount(simplePayPayment.getPayAmount());
		saveBean.setUnit(simplePayPayment.getPayUnit());
		saveBean.setRecordTime(recordTime);
		saveBean.setOrderStatus(PaymentStatusEnum.NEW.getValue());
		payOrderService.saveWithSession(saveBean, saveBean.getFromUserCode());

		Map<String, String> paramMap = new HashMap<>();
		Map<String, String> resultMap = new HashMap<>();

		// 发起交易,如果是余额交易，直接完成交易。如果是第三方交易，如支付宝等，需要等第三方交易结果，再异步处理
		if (PaymentTypeEnum.ALIPAY.getValue().equals(simplePayPayment.getPayWay())) {
			// 生成支付宝验证信息
			String callbackUrl = paymentConstants.pay_callback;
			logger.debug("callbackUrl>>>" + callbackUrl);
			paramMap.put("notifyUrl", callbackUrl);

			resultMap = aliPayService.signature(paramMap, payment);

		} else if (PaymentTypeEnum.WECHAT.getValue().equals(simplePayPayment.getPayWay())) {

			String callbackUrl = paymentConstants.wechatNotifyUrl;
			logger.debug("callbackUrl>>>" + callbackUrl);

			paramMap.put("notifyUrl", callbackUrl);
			paramMap.put("serverIp", serverInfo.getIp());
			// 生成微信 验证信息
			resultMap = weChatPayService.signature(paramMap, payment);

		} else if (PaymentTypeEnum.UNIONPAY.getValue().equals(simplePayPayment.getPayWay())) {
			// 调用银联联支付接口口
		} else if (PaymentTypeEnum.BALANCE.getValue().equals(simplePayPayment.getPayWay())) {
			// 余额支付
			PayBalance balancequeryBean = new PayBalance();
			balancequeryBean.setUserCode(simplePayPayment.getPayUserCode());
			PayBalance balanceResultBean = payBalanceService.findOne(balancequeryBean);
			if (balanceResultBean != null) {

				if (balanceResultBean.getBalanceAmount().compareTo(saveBean.getAmount()) == -1) {
					throw EPaymentException.NOT_ENOUGH_MONEY;
				} else {
					payBalanceService.sub(saveBean);
					saveBean.setOrderStatus(PaymentStatusEnum.SUCC.getValue());
					payOrderService.updateWithSession(saveBean, saveBean.getFromUserCode());
				}

			} else {
				throw EPaymentException.BALANCE_ACCOUNT_NOT_EXIST;
			}
		} else {
			throw EPaymentException.PAYMENT_TYPE_NOT_ACCEPT;
		}

		eventBus.post(saveBean);

		return resultMap;
	}

}
