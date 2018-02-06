package com.sz91online.bgms.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.sz91online.bgms.module.payment.domain.PayBalance;
import com.sz91online.bgms.module.payment.domain.PayUserBank;
import com.sz91online.bgms.module.payment.domain.PayWithdraw;
import com.sz91online.bgms.module.payment.domain.SimplePayMoneyFlow;
import com.sz91online.bgms.module.payment.domain.SimplePayPayment;
import com.sz91online.bgms.module.payment.enums.PaymentStatusEnum;
import com.sz91online.bgms.module.payment.enums.PaymentTypeEnum;
import com.sz91online.bgms.module.payment.exceptions.EPaymentException;
import com.sz91online.bgms.module.payment.service.BgmsPreparePaymentService;
import com.sz91online.bgms.module.payment.service.PayBalanceService;
import com.sz91online.bgms.module.payment.service.PayMoneyFlowService;
import com.sz91online.bgms.module.payment.service.PayUserBankService;
import com.sz91online.bgms.module.payment.service.PayWithdrawService;
import com.sz91online.bgms.module.payment.utils.ServerInfo;
import com.sz91online.common.exceptions.EBusinessException;
import com.sz91online.common.utils.PlStringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 余额、提现 模块
 */
@RequestMapping(value = "/money")
@Controller
@Api(value = "/money", description = "余额相关的接口")
public class MoneyController {

	Logger logger = LoggerFactory.getLogger(MoneyController.class);

	@Autowired
	private PayWithdrawService withdrawService;

	@Autowired
	private PayBalanceService payBalanceService;

	@Autowired
	private PayMoneyFlowService payMoneyFlowService;

	@Autowired
	private BgmsPreparePaymentService bgmsPreparePaymentService;

	@Autowired
	private PayUserBankService userBankService;

	/**
	 * 提现
	 *
	 * @return
	 */
	@RequestMapping(value = "/withdraw", method = RequestMethod.POST)
	@ApiOperation(value = "提现接口", httpMethod = "POST", notes = "")
	public @ResponseBody PayWithdraw withdrwa(
			@RequestBody @ApiParam(name = "提现便利店", value = "传入json格式，只要wdBank(开户行)，wdAccountName(开户名),wdAmount(提现金额)，wdAccountNo(提现账号)", required = true) PayWithdraw withdraw) {

		String userCode = SessionUtil.getSessionUser().getCode();

		if ("Usyouke1231543434".equals(userCode) || "Usjigou1231543434".equals(userCode)) {
			throw new EBusinessException("3453143214", "游客用户不能提现");
		}

		if (PlStringUtils.isEmpty(withdraw.getWdBank()) || PlStringUtils.isEmpty(withdraw.getWdAccountName())
				|| PlStringUtils.isEmpty(withdraw.getWdAccountNo()) || withdraw.getWdAmount() == null) {
			throw EBusinessException.MIS_PARAMETER_ERROR;
		}
		
		if(withdraw.getWdAmount().compareTo(new BigDecimal(0)) != 1) {
			throw EPaymentException.WITHDRAW_MUST_BIGGER_THAN_ZERO;
		}

		withdraw.setBusiUserCode(userCode);
		withdraw.setWdUnit("元");
		withdraw.setWdStatus(PaymentStatusEnum.NEW.getValue());
		withdraw.setWdInitTime(new Date());
		withdraw.setWdCode("Wd" + new Date().getTime());

		withdrawService.withdraw(withdraw, withdraw.getBusiUserCode());
		return withdraw;
	}

	// @RequestMapping(value = "/listWithdraw", method = RequestMethod.GET)
	// @ApiOperation(value = "提现记录查询", httpMethod = "GET", notes = "")
	public @ResponseBody List<PayWithdraw> wdList() throws Exception {
		PayWithdraw queryBean = new PayWithdraw();
		queryBean.setBusiUserCode(SessionUtil.getSessionUser().getCode());
		return withdrawService.findBySelective(queryBean);
	}

	@RequestMapping(value = "/myBalance", method = RequestMethod.GET)
	@ApiOperation(value = "查询我的余额", httpMethod = "GET", notes = "")
	public @ResponseBody Map<String, Double> myBalance() throws Exception {

		Map<String, Double> resultMap = new HashMap<>();

		PayBalance queryBean = new PayBalance();
		queryBean.setUserCode(SessionUtil.getSessionUser().getCode());
		PayBalance resultBean = payBalanceService.findOne(queryBean);
		if (resultBean != null) {

			resultMap.put("balance", resultBean.getBalanceAmount().doubleValue());
		} else {
			resultMap.put("balance", 0D);
		}
		return resultMap;
	}

	@RequestMapping(value = "/findMine", method = RequestMethod.GET)
	@ApiOperation(value = "/查询我的交易记录", httpMethod = "GET", notes = "半年内与我有关的交易记录")
	public @ResponseBody List<SimplePayMoneyFlow> findMine() throws Exception {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		c.add(Calendar.MONTH, -6);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		Date startTime = c.getTime();

		return payMoneyFlowService.findMine(SessionUtil.getSessionUser().getCode(), startTime, new Date());
	}

	/**
	 * 点击支付时,记录支付信息，并返回验证数据
	 *
	 * @return
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	@ApiOperation(value = "余额支付", httpMethod = "POST", notes = "")
	public @ResponseBody void pay(HttpServletRequest request,
			@RequestBody @ApiParam(name = "支付信息", value = "传入json格式,需传入以下参数：<br/>busiCode:与业务记录的主键，这里是用户需求记录的code<br/>payAmount:支付金额<br/>busiType:支付类型，目前只有保证金(BZJ),以后会有充值之类的其它交易。", required = true) SimplePayPayment simplePayPayment)
			throws Exception {

		simplePayPayment.setPayWay(PaymentTypeEnum.BALANCE.getValue());
		simplePayPayment.setPayUnit("元");
		UserSessionInfo user = SessionUtil.getSessionUser();
		simplePayPayment.setPayUserCode(user.getCode());

		ServerInfo serverInfo = new ServerInfo();
		serverInfo.setScheme(request.getScheme());
		serverInfo.setPort(request.getServerPort());
		serverInfo.setContextPath(request.getContextPath());
		serverInfo.setIp("127.0.0.1");

		bgmsPreparePaymentService.pay(simplePayPayment, serverInfo);

	}

	@RequestMapping(value = "/listMyBank", method = RequestMethod.GET)
	@ApiOperation(value = "查询我的银行信息", httpMethod = "GET", notes = "用户快速填写提现信息")
	public @ResponseBody List<PayUserBank> listMyBank() throws Exception {

		PayUserBank queryBean = new PayUserBank();
		queryBean.setUserCode(SessionUtil.getSessionUser().getCode());
		List<PayUserBank> bankLisk = userBankService.findBySelective(queryBean);
		return bankLisk;
	}
}
