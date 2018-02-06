package com.sz91online.bgms.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sz91online.bgms.foundation.web.session.SessionUtil;
import com.sz91online.bgms.module.payment.domain.PayWithdraw;
import com.sz91online.bgms.module.payment.enums.WithdrawStatusEnum;
import com.sz91online.bgms.module.payment.exceptions.EPaymentException;
import com.sz91online.bgms.module.payment.service.PayBalanceService;
import com.sz91online.bgms.module.payment.service.PayWithdrawService;
import com.sz91online.bgms.module.user.domain.User;
import com.sz91online.bgms.module.user.exceptions.EUserException;
import com.sz91online.bgms.module.user.service.UserService;
import com.sz91online.common.utils.PlStringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 提现管理模块
 */
@RequestMapping(value = "/withdraw")
@Controller
@Api(value = "/withdraw", description = "提现管理")
@RequiresRoles({ "admin" })
public class WithdrawController {

	Logger logger = LoggerFactory.getLogger(WithdrawController.class);

	@Autowired
	private PayWithdrawService withdrawService;

	@Autowired
	private UserService userService;

	@Autowired
	private PayBalanceService balanceService;

	/**
	 * 提现
	 *
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ApiOperation(value = "查询提现请求", httpMethod = "GET", notes = "")
	public @ResponseBody List<PayWithdraw> listActive(@RequestParam Map<String, String> map,
			HttpServletResponse response) {

		String currentPage = map.get("currentPage");
		if (currentPage == null) {
			currentPage = "1";
		}
		String pageSize = map.get("pageSize");
		if (pageSize == null) {
			pageSize = "10";
		}

		int curPageInt = Integer.parseInt(currentPage);
		int pageSizeInt = Integer.parseInt(pageSize);
		if (pageSizeInt > 50) {
			pageSizeInt = 50;
		}

		PageHelper.startPage(curPageInt, pageSizeInt);

		PayWithdraw queryBean = new PayWithdraw();

		//如果没有搜索条件，默认查待办的
		boolean flag = false;
		
		String loginName = map.get("loginName");
		if (PlStringUtils.isNotEmpty(loginName)) {
			User withdrawUser = userService.findUserByLoginName(loginName);
			if (withdrawUser == null) {
				throw EUserException.LOGIN_USER_NOT_EXIST_ERROR;
			}
			queryBean.setBusiUserCode(withdrawUser.getUserCode());
			flag = true;
		}
		
		String bankTicketId = map.get("bankTicketId");
		if(PlStringUtils.isNotEmpty(bankTicketId)){
			queryBean.setPayBankId(bankTicketId);
			flag = true;
		}
		
		String accountName = map.get("accountName");
		if(PlStringUtils.isNotEmpty(accountName)){
			queryBean.setWdAccountName(accountName);
			flag = true;
		}

		String accountNo = map.get("accountNo");
		if(PlStringUtils.isNotEmpty(accountNo)){
			queryBean.setWdAccountNo(accountNo);
			flag = true;
		}
		
		String status = map.get("status");
		if(status == null && !flag){
			queryBean.setWdStatus(WithdrawStatusEnum.NEW.getValue());
		}else{
			queryBean.setWdStatus(status);
		}
		

		List<PayWithdraw> resultList = withdrawService.findBySelective(queryBean);

		PageInfo<PayWithdraw> pageUser = new PageInfo<PayWithdraw>(resultList);

		response.setHeader("x-total-count", String.valueOf(pageUser.getTotal())); // 总记录数
		response.setHeader("x-current-page", String.valueOf(pageUser.getPageNum())); // 当前显示第几页
		response.setHeader("x-page-size", String.valueOf(pageUser.getPageSize())); // 每页的记录数
		response.setHeader("x-page-count", String.valueOf(pageUser.getPages())); // 总共多少页
		response.setHeader("x-cur-page-record-size", String.valueOf(pageUser.getSize())); // 当前页有多少条记录

		return resultList;
	}

	/**
	 * 执行提现支付操作
	 *
	 * @return
	 */
	@RequestMapping(value = "/process", method = RequestMethod.PATCH)
	@ApiOperation(value = "处理接口", httpMethod = "PATCH", notes = "公司已完成付款后调用此接口修改状态")
	public @ResponseBody void pay(HttpServletRequest request,
			@RequestBody @ApiParam(name = "提现信息", value = "", required = true) PayWithdraw withdraw) throws Exception {

		if (withdraw.getPayBankId() == null) {
			throw EPaymentException.WITHDRAW_DONE_NEED_BANKID;
		}

		PayWithdraw queryBean = new PayWithdraw();
		queryBean.setWdCode(withdraw.getWdCode());
		PayWithdraw resultBean = withdrawService.findOne(queryBean);
		if (resultBean == null) {
			throw EPaymentException.WITHDRAW_NOT_EXIST;
		}

		if (!WithdrawStatusEnum.NEW.getValue().equals(resultBean.getWdStatus())) {
			throw EPaymentException.WITHDRAW_STATUS_NOT_CORRECT;
		}

		resultBean.setWdStatus(WithdrawStatusEnum.DONE.getValue());
		if (withdraw.getWdResultTime() != null) {
			resultBean.setWdResultTime(withdraw.getWdResultTime());
		} else {
			resultBean.setWdResultTime(new Date());
		}

		resultBean.setWdWay(withdraw.getWdWay());
		resultBean.setPayBankId(withdraw.getPayBankId());
		resultBean.setWdErrorCode(withdraw.getWdErrorCode());

		withdrawService.updateWithSession(resultBean, SessionUtil.getSessionUser().getCode());

	}

	/**
	 * 完成提现流程
	 *
	 * @return
	 */
	@RequestMapping(value = "/finish", method = RequestMethod.PATCH)
	@ApiOperation(value = "结束接口", httpMethod = "PATCH", notes = "在确定银行支付成功后修改状态")
	public @ResponseBody void finish(HttpServletRequest request,
			@RequestParam @ApiParam(name = "提现编号", value = "", required = true) String wdCode) throws Exception {

		PayWithdraw queryBean = new PayWithdraw();
		queryBean.setWdCode(wdCode);
		PayWithdraw resultBean = withdrawService.findOne(queryBean);
		if (resultBean == null) {
			throw EPaymentException.WITHDRAW_NOT_EXIST;
		}

		if (!WithdrawStatusEnum.DONE.getValue().equals(resultBean.getWdStatus())) {
			throw EPaymentException.WITHDRAW_STATUS_NOT_CORRECT;
		}

		resultBean.setWdStatus(WithdrawStatusEnum.FIN.getValue());
		withdrawService.updateWithSession(resultBean, SessionUtil.getSessionUser().getCode());
	}

	/**
	 * 不能支付（如信息错误等）提现流程
	 *
	 * @return
	 */
	@RequestMapping(value = "/fail", method = RequestMethod.PATCH)
	@ApiOperation(value = "结束接口", httpMethod = "PATCH", notes = "在确定银行支付失败后修改状态，同时会把提现金额返回")
	public @ResponseBody void fail(HttpServletRequest request,
			@RequestParam @ApiParam(name = "提现编号", value = "", required = true) String wdCode) throws Exception {

		PayWithdraw queryBean = new PayWithdraw();
		queryBean.setWdCode(wdCode);
		PayWithdraw resultBean = withdrawService.findOne(queryBean);
		if (resultBean == null) {
			throw EPaymentException.WITHDRAW_NOT_EXIST;
		}

		if (!WithdrawStatusEnum.DONE.getValue().equals(resultBean.getWdStatus())) {
			throw EPaymentException.WITHDRAW_STATUS_NOT_CORRECT;
		}

		resultBean.setWdStatus(WithdrawStatusEnum.FAIL.getValue());
		withdrawService.updateWithSession(resultBean, SessionUtil.getSessionUser().getCode());

		// 执行余额退回操作
		balanceService.addForWithdrawFail(resultBean);

	}
}
