package com.sz91online.bgms.module.payment.service.mybatis;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.common.eventbus.AsyncEventBus;
import com.sz91online.bgms.module.payment.domain.PayMoneyFlow;
import com.sz91online.bgms.module.payment.domain.PayOrder;
import com.sz91online.bgms.module.payment.domain.PayPayment;
import com.sz91online.bgms.module.payment.enums.PaymentStatusEnum;
import com.sz91online.bgms.module.payment.exceptions.EPaymentException;
import com.sz91online.bgms.module.payment.service.PayMoneyFlowService;
import com.sz91online.bgms.module.payment.service.PayOrderService;
import com.sz91online.bgms.module.payment.service.PayPaymentService;
import com.sz91online.bgms.module.payment.service.ThirdPayService;
import com.sz91online.bgms.module.payment.utils.PaymentConstants;
import com.sz91online.bgms.module.payment.wechat.MyConfig;
import com.sz91online.bgms.module.payment.wechat.NotifyMessageDto;
import com.sz91online.bgms.module.payment.wechat.NotifyReturnDto;
import com.sz91online.bgms.module.payment.wechat.SecondOrderDto;
import com.sz91online.bgms.module.payment.wechat.WechatUtil;
import com.sz91online.common.utils.PlIdWork;
import com.sz91online.common.utils.PlMD5Util;

@Service("weChatPayService")
public class WeChatService implements ThirdPayService {

	private final Logger logger = LoggerFactory.getLogger(WeChatService.class);

	@Autowired
	private PaymentConstants paymentConstants;

	@Autowired
	private PayPaymentService payPaymentService;

	@Autowired
	private PayOrderService payOrderService;

	@Autowired
	private PayMoneyFlowService moneyFlowService;

	@Autowired
	private AsyncEventBus eventBus; // 异步消息推送

	private static WXPay wxpay;

	public Logger LOG = LoggerFactory.getLogger(WeChatService.class);

	public WeChatService() {

	}

	@Override
	public Map<String, String> signature(Map<String, String> map, PayPayment payment) {

		Map<String, String> data = new HashMap<String, String>();
		data.put("body", "宝宝照护");
		data.put("out_trade_no", payment.getPayId());
		data.put("device_info", "");
		data.put("fee_type", "CNY");
		// 微信的金额以分为单位，所以需要先转换
		data.put("total_fee", String.valueOf(payment.getPayAmount().multiply(new BigDecimal("100")).intValue()));
		data.put("spbill_create_ip", map.get("serverIp"));
		data.put("notify_url", map.get("notifyUrl"));
		data.put("trade_type", "APP"); // 此处指定为扫码支付
		data.put("product_id", "");

		Map<String, String> resp;
		try {
			resp = getWxPay().unifiedOrder(data);

			if (!paymentConstants.wechatPaySuccessStatue.equalsIgnoreCase(resp.get("return_code"))) {
				throw new EPaymentException(EPaymentException.PAYMENT_SIGN_ERROR.getResultCode(),
						resp.get("return_msg"));
			}

			SecondOrderDto secondOrderDto = new SecondOrderDto();
			secondOrderDto.setTimestamp(new Date().getTime() / 1000 + "");
			secondOrderDto.setPartnerid(paymentConstants.wechatMchId);
			secondOrderDto.setNoncestr(resp.get("nonce_str"));
			secondOrderDto.setAppid(resp.get("appid"));
			secondOrderDto.setPrepayid(resp.get("prepay_id"));
			ObjectMapper objectMapper = new ObjectMapper();
			resp = objectMapper.convertValue(secondOrderDto, Map.class);
			String paramLink = PlMD5Util.encrypt(createWechtLinkString(resp));
			resp.put("sign", paramLink);
			logger.info("微信下单返回结果：" + resp);
		} catch (Exception e) {
			logger.error("微信下单出错：" + e.getMessage());
			throw new EPaymentException(EPaymentException.PAYMENT_SIGN_ERROR.getResultCode(), "微信下单出错");
		}

		return resp;
	}

	@Override
	@Transactional
	public String callback(Map<String, String> map) {

		String notifyData = map.get("notifyData");
		// 验证返回信息
		Map<String, String> notifyMap = null;
		try {
			// 转换成map
			notifyMap = WXPayUtil.xmlToMap(notifyData);
		} catch (Exception e1) {
			logger.error("微信回调时出现异常：" + e1.getMessage());
			String xmlString = WechatUtil.parseObjToXML(new NotifyReturnDto(e1.getMessage()));
			return xmlString;
		}

		boolean flag = false;
		try {
			flag = getWxPay().isPayResultNotifySignatureValid(notifyMap);
		} catch (Exception e1) {
			logger.error("微信回调时出现异常：" + e1.getMessage());
			String xmlString = WechatUtil.parseObjToXML(new NotifyReturnDto(e1.getMessage()));
			return xmlString;
		}
		// todo 此处要验证
		if (flag) {
			// 签名正确
			// 进行处理。
			// 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
			try {

				ObjectMapper objectMapper = new ObjectMapper();
				NotifyMessageDto notifyMessageDto = objectMapper.convertValue(notifyMap, NotifyMessageDto.class);
				if (!notifyMessageDto.getReturnCode().toLowerCase().equals(paymentConstants.wechatPaySuccessStatue)) {
					// 通讯失败
					String msg = "微信回调返回通讯失败:" + notifyMessageDto.getReturnMsg();
					logger.debug(msg);
					String xmlString = WechatUtil.parseObjToXML(new NotifyReturnDto(msg));
					return xmlString;
				}

				if (!notifyMessageDto.getResultCode().toLowerCase().equals(paymentConstants.wechatPaySuccessStatue)) {
					// 支付失败
					String msg = "微信回调返回 支付失败:" + notifyMessageDto.getErrCode() + ":"
							+ notifyMessageDto.getErrCodeDes();
					logger.debug(msg);
					String xmlString = WechatUtil.parseObjToXML(new NotifyReturnDto(msg));
					return xmlString;
				}

				PayPayment queryBean = new PayPayment();
				queryBean.setPayId(notifyMessageDto.getOutTradeNo());
				PayPayment payPayment = payPaymentService.findOne(queryBean);

				if (payPayment == null) {
					logger.debug("支付记录不存在！");
					String xmlString = WechatUtil.parseObjToXML(new NotifyReturnDto("支付记录不存在！"));
					return xmlString;
				}
				// 已经处理
				if (payPayment.getPayStatus().equals(PaymentStatusEnum.SUCC.getValue())) {
					String xmlString = WechatUtil.parseObjToXML(new NotifyReturnDto());
					return xmlString;
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				payPayment.setPayeeResultTime(sdf.parse(notifyMessageDto.getTimeEnd()));
				payPayment.setPayStatus(PaymentStatusEnum.SUCC.getValue());
				payPayment.setPayeeUserId(notifyMessageDto.getOpenid());
				// packagePayment.setPayeeName(notifyMessageDto.getBuyer_email());

				payPaymentService.updateByPrimaryKeySelective(payPayment, "PaySystem");

				PayOrder queryBean2 = new PayOrder();
				queryBean2.setPayId(payPayment.getPayId());
				queryBean2.setBusiCode(payPayment.getBusiCode());
				PayOrder payOrder = payOrderService.findOne(queryBean2);

				if (payOrder != null) {
					payOrder.setOrderStatus(payPayment.getPayStatus());
					payOrderService.updateByPrimaryKeySelective(payOrder, "PaySystem");
				}

				// 添加微信支付信息到 资金流水表
				PayMoneyFlow moneyFlow = new PayMoneyFlow();
				moneyFlow.setAmount(new BigDecimal(0).subtract(payPayment.getPayAmount()));
				moneyFlow.setBusiType("BZJ-WC");
				moneyFlow.setBusiUserCode(payPayment.getPayUserCode());
				moneyFlow.setOrderRecordCode("BZ" + new PlIdWork().nextId());
				moneyFlow.setRecordTime(new Date());
				moneyFlow.setUnit("元");
				moneyFlowService.saveWithSession(moneyFlow, moneyFlow.getBusiUserCode());

				String xmlString = WechatUtil.parseObjToXML(new NotifyReturnDto());
				eventBus.post(payOrder);
				return xmlString;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			// 签名错误，如果数据里没有sign字段，也认为是签名错误
			logger.error("微信签名错误");
			String xmlString = WechatUtil.parseObjToXML(new NotifyReturnDto("微信签名错误"));
			return xmlString;
		}

	}

	/**
	 * 微信SDK支付对象
	 * 
	 * @return
	 */
	public WXPay getWxPay() {
		if (wxpay == null) {
			MyConfig config = new MyConfig(paymentConstants.wechatAppId, paymentConstants.wechatMchId,
					paymentConstants.wechatPrivateKey);
			this.wxpay = new WXPay(config);
		}
		return wxpay;

	}

	/**
	 * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 * 
	 * @param params
	 *            需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public String createWechtLinkString(Map<String, String> params) {

		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);

		String prestr = "";

		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			Object value = params.get(key);

			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}

		return prestr + "&key=" + paymentConstants.wechatPrivateKey;
	}

}
