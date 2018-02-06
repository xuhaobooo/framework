package com.sz91online.bgms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * 支付模块
 */
@RequestMapping(value = "/wxpay")
@Controller
@Api(value = "/wxpay", description = "微信支付接口")
public class WxpayController {

	Logger logger = LoggerFactory.getLogger(WxpayController.class);

	@Autowired
	@Qualifier("weChatPayService") // 指向微信支付
	ThirdPayService weChatPayService;

	@Autowired
	private BgmsPreparePaymentService bgmsPreparePaymentService;

	/**
	 * 点击支付时,记录支付信息，并返回验证数据
	 *
	 * @return
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	@ApiOperation(value = "App支付接口", httpMethod = "POST", notes = "在APP打开微信支付前获取支付相关的数据，返回一个Map类的json字符串，具体内容参看微信开发文档")
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
		logger.info(serverInfo.getIp());

		simplePayPayment.setPayWay(PaymentTypeEnum.WECHAT.getValue());
		simplePayPayment.setPayUnit("元");
		UserSessionInfo user = SessionUtil.getSessionUser();
		simplePayPayment.setPayUserCode(user.getCode());

		Map<String, String> map = bgmsPreparePaymentService.pay(simplePayPayment, serverInfo);
		return map;
	}

	/**
	 * 微信异步回调时调用
	 *
	 * @return
	 * @throws DocumentException
	 */
	@RequestMapping(value = "/notifyUrl", method = RequestMethod.POST)
	@ApiIgnore
	public @ResponseBody String AlipayNotifyUrl(@RequestBody String notifyData) {

		logger.debug("微信正在回调");

		// 获取微信POST过来反馈信息
		String result = null;
		try {
			Map<String, String> paramMap = new HashMap<>();
			paramMap.put("notifyData", notifyData);
			result = weChatPayService.callback(paramMap);
		} catch (Exception e) {
			logger.error("微信回调发生错误：" + e.getMessage());
		}
		return result;
	}

	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

}
