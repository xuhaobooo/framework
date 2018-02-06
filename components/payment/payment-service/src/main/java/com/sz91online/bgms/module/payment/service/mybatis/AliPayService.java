package com.sz91online.bgms.module.payment.service.mybatis;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.AsyncEventBus;
import com.sz91online.bgms.module.payment.alipay.HttpProtocolHandler;
import com.sz91online.bgms.module.payment.alipay.HttpRequest;
import com.sz91online.bgms.module.payment.alipay.HttpResponse;
import com.sz91online.bgms.module.payment.alipay.HttpResultType;
import com.sz91online.bgms.module.payment.alipay.NotifyMessageDto;
import com.sz91online.bgms.module.payment.domain.BusiFinishNotifyBean;
import com.sz91online.bgms.module.payment.domain.PayMoneyFlow;
import com.sz91online.bgms.module.payment.domain.PayOrder;
import com.sz91online.bgms.module.payment.domain.PayPayment;
import com.sz91online.bgms.module.payment.enums.PaymentStatusEnum;
import com.sz91online.bgms.module.payment.service.PayMoneyFlowService;
import com.sz91online.bgms.module.payment.service.PayOrderService;
import com.sz91online.bgms.module.payment.service.PayPaymentService;
import com.sz91online.bgms.module.payment.service.ThirdPayService;
import com.sz91online.bgms.module.payment.utils.PaymentConstants;
import com.sz91online.common.utils.PlIdWork;
import com.sz91online.common.utils.PlStringUtils;

@Service("aliPayService")
@Transactional
public class AliPayService implements ThirdPayService {

	private final Logger logger = LoggerFactory.getLogger(AliPayService.class);

	@Autowired
	private PaymentConstants paymentConstants;

	@Autowired
	private PayPaymentService payPaymentService;

	@Autowired
	private PayOrderService payOrderService;

	private static AlipayClient alipayClient;
	
	@Autowired
	private PayMoneyFlowService moneyFlowService;

	@Autowired
	private AsyncEventBus eventBus; // 异步消息推送

	@Override
	public Map<String, String> signature(Map<String, String> map, PayPayment payment) {

		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("APP支付订单");
		model.setSubject("宝宝照护");
		model.setOutTradeNo(payment.getPayId());
		model.setTimeoutExpress("30m");
		model.setTotalAmount(String.valueOf(payment.getPayAmount()));
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setNotifyUrl(map.get("notifyUrl"));

		Map<String, String> result = new HashMap<>();

		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = getAlipayClient().sdkExecute(request);
			result.put("signedString", response.getBody());
			return result;// 就是orderString 可以直接给客户端请求，无需再做处理。
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return result;
		}

	}

	@Override
	@Transactional
	public String callback(Map<String, String> paramMap) {

		// 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		// boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String
		// publicKey, String charset, String sign_type)
		try {
			//boolean flag = AlipaySignature.rsaCheckV2(paramMap, paymentConstants.alipayPublicKey,paymentConstants.charset, paymentConstants.signType);
			boolean flag = AlipaySignature.rsaCheckV1(paramMap, paymentConstants.alipayPublicKey, paymentConstants.charset, paymentConstants.signType);
			if (!flag) {
				logger.error("支付回调信息验证不能通过！");
				return paymentConstants.AliPayFailureStatue;
			}
		} catch (AlipayApiException e1) {
			e1.printStackTrace();
			logger.error("支付回调信息验证过程中出错！");
			return paymentConstants.AliPayFailureStatue;
		}

		ObjectMapper objectMapper = new ObjectMapper();
		NotifyMessageDto notifyMessageDto = objectMapper.convertValue(paramMap, NotifyMessageDto.class);

		try {

			/*
			 * HttpRequest httpRequest = new HttpRequest(HttpResultType.STRING); String url
			 * = "https://mapi.alipay.com/gateway.do?service=notify_verify&partner=" +
			 * paymentConstants.AlipayAppId + "&notify_id=" +
			 * notifyMessageDto.getNotifyId(); httpRequest.setUrl(url);
			 * httpRequest.setMethod(HttpRequest.METHOD_GET); HttpResponse httpResponse =
			 * HttpProtocolHandler.getInstance().execute(httpRequest); String result =
			 * httpResponse.getStringResult();
			 */

			if (PlStringUtils.isEmpty(notifyMessageDto.getOutTradeNo())) {
				logger.debug("支付宝返回数据里没有 TradeNo");
				return paymentConstants.AliPayFailureStatue;
			}

			PayPayment queryBean = new PayPayment();
			queryBean.setPayId(notifyMessageDto.getOutTradeNo());
			PayPayment payPayment = payPaymentService.findOne(queryBean);

			if (payPayment == null) {
				logger.debug("支付记录不存在！");
				return paymentConstants.AliPayFailureStatue;
			}

			if (!paymentConstants.alipayAppId.equals(notifyMessageDto.getAppId())) {
				logger.debug("支付宝通知记录的app_id和本地支付记录不同！");
				return paymentConstants.AliPayFailureStatue;
			}

			if (notifyMessageDto.getSellerId() != null
					&& !paymentConstants.alipaySellerId.equals(notifyMessageDto.getSellerId())) {
				logger.debug("支付宝通知记录的seller_id和本地不同！");
				return paymentConstants.AliPayFailureStatue;
			}

			if (!payPayment.getPayId().equals(notifyMessageDto.getOutTradeNo())) {
				logger.debug("支付宝通知记录的out_trade_no和本地支付记录的app_id不同！");
				return paymentConstants.AliPayFailureStatue;
			}

			if (notifyMessageDto.getTotalFee() != null
					&& payPayment.getPayAmount().compareTo(new BigDecimal(notifyMessageDto.getTotalFee())) != 0) {
				logger.debug("支付宝通知记录的的总金额与本地不同！");
				return paymentConstants.AliPayFailureStatue;
			}

			if (notifyMessageDto.getTradeStatus().equals("TRADE_CLOSED")) {
				logger.debug("支付返回：交易关闭！");

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				payPayment.setPayeeResultTime(sdf.parse(notifyMessageDto.getGmtCreate()));
				payPayment.setPayStatus(PaymentStatusEnum.FAIL.getValue());
				payPayment.setPayeeUserId(notifyMessageDto.getBuyerId());
				// packagePayment.setPayeeName(notifyMessageDto.getBuyer_email());
				payPayment.setPayeeAccountNo(notifyMessageDto.getBuyerEmail());
				payPayment.setPayeeId(notifyMessageDto.getTradeNo());
				payPaymentService.updateByPrimaryKeySelective(payPayment, "PaySystem");

				PayOrder queryBean2 = new PayOrder();
				queryBean2.setPayId(payPayment.getPayId());
				queryBean2.setBusiCode(payPayment.getBusiCode());
				PayOrder payOrder = payOrderService.findOne(queryBean2);

				if (payOrder != null) {
					payOrder.setOrderStatus(payPayment.getPayStatus());
					payOrderService.updateByPrimaryKeySelective(payOrder, "PaySystem");
				}

				eventBus.post(payOrder);
				return paymentConstants.AliPaySuccessStatue;
			} else if ("TRADE_SUCCESS".equals(notifyMessageDto.getTradeStatus())) {

				// 已经处理
				if (payPayment.getPayStatus().equals(PaymentStatusEnum.SUCC.getValue())) {
					return paymentConstants.AliPaySuccessStatue;
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				payPayment.setPayeeResultTime(sdf.parse(notifyMessageDto.getGmtCreate()));
				payPayment.setPayStatus(PaymentStatusEnum.SUCC.getValue());
				payPayment.setPayeeUserId(notifyMessageDto.getBuyerId());
				// packagePayment.setPayeeName(notifyMessageDto.getBuyer_email());
				payPayment.setPayeeAccountNo(notifyMessageDto.getBuyerEmail());
				payPayment.setPayeeId(notifyMessageDto.getTradeNo());
				payPaymentService.updateByPrimaryKeySelective(payPayment, "PaySystem");

				PayOrder queryBean2 = new PayOrder();
				queryBean2.setPayId(payPayment.getPayId());
				queryBean2.setBusiCode(payPayment.getBusiCode());
				PayOrder payOrder = payOrderService.findOne(queryBean2);

				if (payOrder != null) {
					payOrder.setOrderStatus(payPayment.getPayStatus());
					payOrderService.updateByPrimaryKeySelective(payOrder, "PaySystem");
				}
				
				//添加支付宝支付信息到 资金流水表
				PayMoneyFlow moneyFlow = new PayMoneyFlow();
				moneyFlow.setAmount(new BigDecimal(0).subtract(payPayment.getPayAmount()));
				moneyFlow.setBusiType("BZJ-ALI");
				moneyFlow.setBusiUserCode(payPayment.getPayUserCode());
				moneyFlow.setOrderRecordCode("BZ" + new PlIdWork().nextId());
				moneyFlow.setRecordTime(new Date());
				moneyFlow.setUnit("元");
				moneyFlowService.saveWithSession(moneyFlow, moneyFlow.getBusiUserCode());

				eventBus.post(payOrder);

				return paymentConstants.AliPaySuccessStatue;
			} else if ("TRADE_FINISHED".equals(notifyMessageDto.getTradeStatus())) {
				logger.debug("支付完成通知，暂未处理！");
				return paymentConstants.AliPaySuccessStatue;
			} else if ("WAIT_BUYER_PAY".equals(notifyMessageDto.getTradeStatus())) {
				logger.debug("交易创建，等待买家付款！");
				return paymentConstants.AliPaySuccessStatue;
			} else {
				return paymentConstants.AliPayFailureStatue;
			}
		} catch (Exception e) {
			logger.error("支付宝回调过程出错：" + e.getMessage());
			return paymentConstants.AliPayFailureStatue;
		}
	}

	public AlipayClient getAlipayClient() {
		if (alipayClient == null) {
			alipayClient = new DefaultAlipayClient(paymentConstants.alipayGateway, paymentConstants.alipayAppId,
					paymentConstants.privateKey, "JSON", paymentConstants.charset, paymentConstants.alipayPublicKey,
					paymentConstants.signType);
		}
		return alipayClient;
	}

}
