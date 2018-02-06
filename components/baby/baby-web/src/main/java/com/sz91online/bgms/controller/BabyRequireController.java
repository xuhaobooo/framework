package com.sz91online.bgms.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz91online.bgms.foundation.web.session.SessionUtil;
import com.sz91online.bgms.foundation.web.session.UserSessionInfo;
import com.sz91online.bgms.module.baby.domain.BabyApply;
import com.sz91online.bgms.module.baby.domain.BabyPayment;
import com.sz91online.bgms.module.baby.domain.BabyRequireItemDict;
import com.sz91online.bgms.module.baby.domain.BabyTimePrice;
import com.sz91online.bgms.module.baby.domain.SimpleBabyApply;
import com.sz91online.bgms.module.baby.domain.SimpleBabyRequire;
import com.sz91online.bgms.module.baby.domain.SimpleBabyTask;
import com.sz91online.bgms.module.baby.enums.BabyApplyStatusEnum;
import com.sz91online.bgms.module.baby.enums.BabyRequireStatusEnum;
import com.sz91online.bgms.module.baby.service.BabyPaymentService;
import com.sz91online.bgms.module.baby.service.BabyRequireService;
import com.sz91online.bgms.module.baby.service.BabyTimePriceService;
import com.sz91online.common.utils.PlDateTime;
import com.sz91online.common.utils.PlStringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping(value = "babyRequire")
@Api(value = "/babyRequire", description = "用户需求接口")
public class BabyRequireController {

	@Autowired
	private BabyRequireService babyRequireService;
	
	@Autowired 
	private BabyTimePriceService timePriceService;

	@RequestMapping(method = RequestMethod.POST, value = "publish")
	@ApiOperation(value = "发布需求信息", httpMethod = "POST", notes = "返回需求的Code")
	public @ResponseBody String publishRequire(
			@RequestBody @ApiParam(name = "需求信息", value = "传入json格式(不含ID，requireCode,requireStatus,userCode,create_time),包括需求项目.需求项目不需要传id和requireCode", required = true) SimpleBabyRequire babyRequire,
			HttpServletRequest request) {

		Assert.notEmpty(babyRequire.getItems());

		UserSessionInfo user = SessionUtil.getSessionUser();
		String userCode = user.getCode();

		// 自动生成authCode
		babyRequire.setRequireCode("Rq" + new Date().getTime());
		babyRequire.setRequireStatus(BabyRequireStatusEnum.NEW.getValue());
		babyRequire.setUserCode(userCode);
		babyRequire.setCreateTime(new Date());

		babyRequireService.publish(babyRequire, userCode);

		return babyRequire.getRequireCode();
	}

	@RequestMapping(method = RequestMethod.GET, value = "findOne/{requireCode}")
	@ApiOperation(value = "查询单个需求", httpMethod = "GET", notes = "需求信息,带项目")
	public @ResponseBody SimpleBabyRequire findOne(
			@PathVariable @ApiParam(name = "requireCode", value = "需求编号", required = true) String requireCode,
			HttpServletRequest request) {

		// UserSessionInfo user = SessionUtil.getSessionUser();

		SimpleBabyRequire requireBean = babyRequireService.findSingle(requireCode);

		return requireBean;
	}

	@RequestMapping(method = RequestMethod.GET, value = "listMine")
	@ApiOperation(value = "查询自己的需求", httpMethod = "GET", notes = "需求列表,带项目")
	public @ResponseBody List<SimpleBabyRequire> listMyRequire(
			@RequestParam(required = false) @ApiParam(name = "startDate", value = "开始时间，可不填写，默认是七天前的零点，格式为日期　yyyy-MM-dd HH:mm:ss", required = false) String startDate,
			@RequestParam(required = false) @ApiParam(name = "endDate", value = "结束时间，可不填写，默认是当前时间。格式为日期　yyyy-MM-dd HH:mm:ss", required = false) String endDate,
			@RequestParam(required = false) @ApiParam(name = "status", value = "需求状态，可不填写，默认是所有的需求", required = false) String status,
			HttpServletRequest request) {

		UserSessionInfo user = SessionUtil.getSessionUser();

		// 自动生成authCode

		Date startTime = null;
		Date endTime = null;

		if (PlStringUtils.isNotEmpty(startDate)) {
			startTime = PlDateTime.parseDate(startDate, PlDateTime.FORMAT_FUL);
		}

		if (startTime == null) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());

			c.add(Calendar.DAY_OF_YEAR, -7);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);

			startTime = c.getTime();
		}

		if (PlStringUtils.isNotEmpty(endDate)) {
			endTime = PlDateTime.parseDate(endDate, PlDateTime.FORMAT_FUL);
		}

		if (endTime == null) {
			endTime = new Date();
		}

		/*
		 * if (PlStringUtils.isEmpty(status)) { status =
		 * BabyRequireStatusEnum.NEW.getValue(); }
		 */

		List<SimpleBabyRequire> requireList = babyRequireService.listMy(user.getCode(), status, startTime, endTime);

		return requireList;
	}

	@RequestMapping(method = RequestMethod.POST, value = "apply/{requireCode}")
	@ApiOperation(value = "机构申请需求", httpMethod = "POST", notes = "机构从需求列表中申请自己合适的需求")
	public @ResponseBody void apply(
			@PathVariable @ApiParam(name = "requireCode", value = "需求编号", required = true) String requireCode,
			HttpServletRequest request) {

		UserSessionInfo user = SessionUtil.getSessionUser();

		babyRequireService.apply(requireCode, user.getCode());

	}

	@RequestMapping(method = RequestMethod.POST, value = "select/{applyId}")
	@ApiOperation(value = "选择某个机构", httpMethod = "POST", notes = "")
	public @ResponseBody void select(
			@PathVariable @ApiParam(name = "applyId", value = "接单编号", required = true) Long applyId,
			HttpServletRequest request) {

		// UserSessionInfo user = SessionUtil.getSessionUser();

		babyRequireService.select(applyId);

	}

	@RequestMapping(method = RequestMethod.POST, value = "arrive/{taskCode}")
	@ApiOperation(value = "机构到达", httpMethod = "POST", notes = "")
	public @ResponseBody void arrive(
			@PathVariable @ApiParam(name = "taskCode", value = "任务编号", required = true) String taskCode,
			HttpServletRequest request) {

		UserSessionInfo user = SessionUtil.getSessionUser();

		babyRequireService.arrive(taskCode, user.getCode());

	}

	//@RequestMapping(method = RequestMethod.POST, value = "mockPay/{requireCode}")
	//@ApiOperation(value = "模拟支付", httpMethod = "POST", notes = "")
	public @ResponseBody void mockPay(
			@PathVariable @ApiParam(name = "requireCode", value = "需求编号", required = true) String requireCode,
			HttpServletRequest request) {

		UserSessionInfo user = SessionUtil.getSessionUser();

		babyRequireService.mockPay(requireCode, user.getCode());

	}

	@RequestMapping(method = RequestMethod.POST, value = "provideFinish/{taskCode}")
	@ApiOperation(value = "机构完成", httpMethod = "POST", notes = "")
	public @ResponseBody void provideFinish(
			@PathVariable @ApiParam(name = "taskCode", value = "任务编号", required = true) String taskCode,
			HttpServletRequest request) {

		UserSessionInfo user = SessionUtil.getSessionUser();
		babyRequireService.provideFinish(taskCode, user.getCode());
	}

	@RequestMapping(method = RequestMethod.POST, value = "customerFinish/{requireCode}")
	@ApiOperation(value = "用户确认完成", httpMethod = "POST", notes = "")
	public @ResponseBody void customerFinish(
			@PathVariable @ApiParam(name = "requireCode", value = "需求编号", required = true) String requireCode,
			HttpServletRequest request) {

		UserSessionInfo user = SessionUtil.getSessionUser();
		babyRequireService.customerFinish(requireCode, user.getCode());
	}

	@RequestMapping(method = RequestMethod.GET, value = "findApply/{requireCode}")
	@ApiOperation(value = "查询单个需求的所有接单信息", httpMethod = "GET", notes = "接单信息")
	public @ResponseBody List<SimpleBabyApply> findApply(
			@PathVariable @ApiParam(name = "requireCode", value = "需求编号", required = true) String requireCode,
			@RequestParam(required = false) @ApiParam(name = "status", value = "需求状态：SUCC表示被选中的，不传此参数表示查询此需求的所有接单信息", required = false) String status,
			HttpServletRequest request) {

		// UserSessionInfo user = SessionUtil.getSessionUser();
		List<SimpleBabyApply> applyList = babyRequireService.findApply(requireCode);
		if (BabyApplyStatusEnum.SUCC.getValue().equals(status)) {
			List<SimpleBabyApply> succList = new ArrayList<>();
			for (Iterator<SimpleBabyApply> iterator = applyList.iterator(); iterator.hasNext();) {
				SimpleBabyApply babyApply = iterator.next();
				if (BabyApplyStatusEnum.SUCC.getValue().equals(babyApply.getApplyStatus())) {
					succList.add(babyApply);
				}
			}
			return succList;
		}
		return applyList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "findTaskByRequireCode/{requireCode}")
	@ApiOperation(value = "查询单个需求任务信息", httpMethod = "GET", notes = "任务信息")
	public @ResponseBody SimpleBabyTask findTaskByRequireCode(
			@PathVariable @ApiParam(name = "requireCode", value = "需求编号", required = true) String requireCode,
			HttpServletRequest request) {

		// UserSessionInfo user = SessionUtil.getSessionUser();
		SimpleBabyTask task = babyRequireService.findTask(requireCode);
		return task;
	}

	@RequestMapping(method = RequestMethod.GET, value = "findTaskByTaskCode/{taskCode}")
	@ApiOperation(value = "查询单个需求任务信息", httpMethod = "GET", notes = "任务信息")
	public @ResponseBody SimpleBabyTask findTaskByTaskCode(
			@PathVariable @ApiParam(name = "taskCode", value = "任务编号", required = true) String taskCode,
			HttpServletRequest request) {

		// UserSessionInfo user = SessionUtil.getSessionUser();
		SimpleBabyTask task = babyRequireService.findTask(taskCode);
		return task;
	}

	@RequestMapping(method = RequestMethod.GET, value = "listService")
	@ApiOperation(value = "查询所有可用的服务", httpMethod = "GET", notes = "")
	public @ResponseBody List<BabyRequireItemDict> listService() {

		// UserSessionInfo user = SessionUtil.getSessionUser();
		List<BabyRequireItemDict> result = babyRequireService.getAllService();
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "listRequire")
	@ApiOperation(value = "查询可构可接需求列表", httpMethod = "GET", notes = "如果不传startTime,默认startTime是昨天的零点。如果不传endTime,endTime为6天后的23点59分59秒")
	public @ResponseBody List<SimpleBabyRequire> listRequire(
			@RequestParam(required = false) @ApiParam(name = "startTime", value = "开始时间", required = false) String startTime,
			@RequestParam(required = false) @ApiParam(name = "endTime", value = "结束时间", required = false) String endTime) {

		UserSessionInfo user = SessionUtil.getSessionUser();

		Date startTimeObj = null;
		if (!PlStringUtils.isEmpty(startTime)) {
			startTimeObj = PlDateTime.parseDateFull(startTime);
		}

		if (startTimeObj == null) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());

			// c.add(Calendar.DAY_OF_YEAR, -1);
			// c.set(Calendar.HOUR_OF_DAY, 0);
			// c.set(Calendar.MINUTE, 0);
			// c.set(Calendar.SECOND, 0);

			startTimeObj = c.getTime();
		}

		Date endTimeObj = null;
		if (!PlStringUtils.isEmpty(endTime)) {
			endTimeObj = PlDateTime.parseDateFull(endTime);
		}

		if (endTimeObj == null) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());

			c.add(Calendar.DAY_OF_YEAR, +3);
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 23);
			c.set(Calendar.SECOND, 59);

			endTimeObj = c.getTime();
		}

		List<SimpleBabyRequire> result = babyRequireService.listRequire(user.getCode(), startTimeObj, endTimeObj);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "listTasks")
	@ApiOperation(value = "查询所在执行中的任务", httpMethod = "GET", notes = "")
	public @ResponseBody List<SimpleBabyTask> listTasks(
			@RequestParam(required = false) @ApiParam(name = "startDate", value = "开始时间，可不填写，默认是七天前的零点，格式为日期　yyyy-MM-dd HH:mm:ss", required = false) String startDate,
			@RequestParam(required = false) @ApiParam(name = "endDate", value = "结束时间，可不填写，默认是当前时间。格式为日期　yyyy-MM-dd HH:mm:ss", required = false) String endDate,
			@RequestParam(required = false) @ApiParam(name = "status", value = "任务状态，可不填写，默认是所有任务", required = false) String status) {

		UserSessionInfo user = SessionUtil.getSessionUser();

		Date startTime = null;
		Date endTime = null;

		if (PlStringUtils.isNotEmpty(startDate)) {
			startTime = PlDateTime.parseDate(startDate, PlDateTime.FORMAT_FUL);
		}

		if (startTime == null) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());

			c.add(Calendar.DAY_OF_YEAR, -7);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);

			startTime = c.getTime();
		}

		if (PlStringUtils.isNotEmpty(endDate)) {
			endTime = PlDateTime.parseDate(endDate, PlDateTime.FORMAT_FUL);
		}

		if (endTime == null) {
			endTime = new Date();
		}

		List<SimpleBabyTask> result = babyRequireService.listTasks(user.getCode(), status, startTime, endTime);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "listDoneTasks")
	@ApiOperation(value = "查询已完成的任务", httpMethod = "GET", notes = "")
	public @ResponseBody List<SimpleBabyTask> listDoneTasks() {

		UserSessionInfo user = SessionUtil.getSessionUser();
		List<SimpleBabyTask> result = babyRequireService.listDoneTasks(user.getCode());
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "timePrice")
	@ApiOperation(value = "查询每小时的费用", httpMethod = "GET", notes = "不足一小时的按一小时收费")
	public @ResponseBody Map<String, Object> timePrice() {
		
		BabyTimePrice queryBean = new BabyTimePrice();
		queryBean.setPriceCode("all_in_china");
		BabyTimePrice resultBean = timePriceService.findOne(queryBean);
		
		Map<String, Object> result = new HashMap<>();
		
		if(resultBean != null) {
			result.put("price", resultBean.getPrice());
		}else {
			result.put("price", 0.01F);
		}
		
		
		
		return result;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "cancel/{requireCode}")
	@ApiOperation(value = "需方取消需求", httpMethod = "DELETE", notes = "取消需求，只能在机构到达之前，并且未支付的情况下取消。")
	public @ResponseBody void cancel(
			@PathVariable @ApiParam(name = "requireCode", value = "需求编号", required = true) String requireCode) {
		babyRequireService.cancelRequire(SessionUtil.getSessionUser().getCode(), requireCode);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "cancelTask/{taskCode}")
	@ApiOperation(value = "供方取消需求", httpMethod = "DELETE", notes = "取消需求，只能在需方未支付的情况下取消。")
	public @ResponseBody void cancelTask(
			@PathVariable @ApiParam(name = "taskCode", value = "任务编号", required = true) String taskCode) {
		babyRequireService.cancelTask(SessionUtil.getSessionUser().getCode(), taskCode);
	}
}
