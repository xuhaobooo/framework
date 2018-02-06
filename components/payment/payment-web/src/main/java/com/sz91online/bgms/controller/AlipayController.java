package com.sz91online.bgms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz91online.bgms.foundation.web.session.SessionUtil;
import com.sz91online.bgms.foundation.web.session.UserSessionInfo;
import com.sz91online.bgms.module.payment.domain.SimplePayPayment;
import com.sz91online.bgms.module.payment.enums.PaymentTypeEnum;
import com.sz91online.bgms.module.payment.service.BgmsPreparePaymentService;
import com.sz91online.bgms.module.payment.service.ThirdPayService;
import com.sz91online.bgms.module.payment.utils.ServerInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 支付宝支付模块
 */
@RequestMapping(value = "/alipay")
@Controller
@Api(value = "/alipay", description = "支付宝支付接口")
public class AlipayController {

	Logger logger = LoggerFactory.getLogger(AlipayController.class);

	@Autowired
	ThirdPayService aliPayService;

	@Autowired
	private BgmsPreparePaymentService bgmsPreparePaymentService;
	
	/**
	 * 点击支付时,记录支付信息，并返回验证数据
	 *
	 * @return
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	@ApiOperation(value = "App支付接口", httpMethod = "POST", notes = "在APP打开支付宝前获取支付相关的数据，返回一个json字符串，具体内容参看支付宝开发文档")
	public @ResponseBody Map<String, String> pay(HttpServletRequest request,
			@RequestBody @ApiParam(name = "支付信息", value = "传入json格式,需传入以下参数：<br/>busiCode:与业务记录的主键，这里是用户需求记录的code<br/>payAmount:支付金额<br/>busiType:支付类型，目前只有保证金(BZJ),以后会有充值之类的其它交易。", required = true) SimplePayPayment simplePayPayment)
			throws Exception {

		// 清除数据
		simplePayPayment.setPayeeAccountNo(null);
		simplePayPayment.setPayeeErrorCode(null);
		simplePayPayment.setPayeeId(null);
		simplePayPayment.setPayeeResultTime(null);
		simplePayPayment.setPayeeUserId(null);
		simplePayPayment.setPayeeUserName(null);
		simplePayPayment.setPayId(null);
		simplePayPayment.setPayInitTime(null);
		simplePayPayment.setPayStatus(null);

		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setScheme(request.getScheme());
		serverInfo.setPort(request.getServerPort());
		serverInfo.setContextPath(request.getContextPath());
		serverInfo.setIp(getIpAddr(request));

		simplePayPayment.setPayWay(PaymentTypeEnum.ALIPAY.getValue());
		simplePayPayment.setPayUnit("元");
		UserSessionInfo user = SessionUtil.getSessionUser();
		simplePayPayment.setPayUserCode(user.getCode());

		Map<String, String> map = bgmsPreparePaymentService.pay(simplePayPayment, serverInfo);
		logger.debug("sign: " + map.get("signedString"));
		return map;
	}

	/**
	 * 支付宝异步回调时调用
	 *
	 * @return
	 * @throws DocumentException
	 */
	@RequestMapping(value = "/notifyUrl", method = RequestMethod.POST)
	@ApiIgnore
	public @ResponseBody String AlipayNotifyUrl(HttpServletRequest request, HttpServletResponse response)
			throws IOException, DocumentException {

		logger.debug("支付宝正在回调");

		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			logger.debug("支付宝返回信息---" + name + ":" + valueStr);
			params.put(name, valueStr);
		}
		String result = aliPayService.callback(params);
		
		return result;
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader(" x-forwarded-for ");
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getHeader(" WL-Proxy-Client-IP ");
		}
		if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
