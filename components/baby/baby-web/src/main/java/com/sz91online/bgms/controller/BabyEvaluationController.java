package com.sz91online.bgms.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz91online.bgms.foundation.web.controller.BaseController;
import com.sz91online.bgms.foundation.web.session.SessionUtil;
import com.sz91online.bgms.module.baby.domain.BabyEvaluation;
import com.sz91online.bgms.module.baby.domain.BabyRequire;
import com.sz91online.bgms.module.baby.domain.SimpleBabyEvaluation;
import com.sz91online.bgms.module.baby.exceptions.EBabyException;
import com.sz91online.bgms.module.baby.service.BabyEvaluationService;
import com.sz91online.bgms.module.baby.service.BabyRequireService;
import com.sz91online.common.db.service.ICrudService;
import com.sz91online.common.exceptions.EBusinessException;
import com.sz91online.common.utils.PlStringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping(value = "babyEvaluate")
@Api(value = "/babyEvaluate", description = "评价接口")
public class BabyEvaluationController extends BaseController<BabyEvaluation> {

	@Autowired
	private BabyEvaluationService babyEvaluationService;

	@Autowired
	private BabyRequireService babyRequireService;

	@Override
	public ICrudService<BabyEvaluation> getService() {
		return babyEvaluationService;
	}

	@Override
	@RequestMapping(method = RequestMethod.POST, value = "current")
	@ApiOperation(value = "添加评价", httpMethod = "POST", notes = "返回执行保存记录后的记录，包含数据库主键id")
	public @ResponseBody BabyEvaluation saveCurEntity(
			@RequestBody @ApiParam(name = "评价", value = "传入json格式,不包括id", required = true) BabyEvaluation babyEvaluation,
			HttpServletRequest request) {

		String requireCode = babyEvaluation.getRequireCode();
		Assert.notNull(requireCode);

		BabyRequire queryBean = new BabyRequire();
		queryBean.setRequireCode(requireCode);
		BabyRequire resultBean = babyRequireService.findOne(queryBean);
		if (resultBean == null) {
			throw EBabyException.BABY_REQUIRE_NOT_EXIST_ERROR;
		}

		BabyEvaluation queryBean2 = new BabyEvaluation();
		queryBean2.setRequireCode(requireCode);
		queryBean2.setReceiveUserCode(babyEvaluation.getReceiveUserCode());
		queryBean2.setSendUserCode(babyEvaluation.getSendUserCode());
		BabyEvaluation resultBean2 = babyEvaluationService.findOne(queryBean2);

		if (resultBean2 != null) {
			// update
			throw EBabyException.BABY_HAVE_EVALUTION_ERROR;
		} else {
			if(PlStringUtils.isEmpty(babyEvaluation.getLevel())) {
				babyEvaluation.setLevel("HIGH");
			}
			return super.saveCurEntity(babyEvaluation, request);
		}

		// babyEvaluation.setReceiveUserCode(resultBean.getUserCode());
		// babyEvaluation.setSendUserCode(SessionUtil.getSessionUser().getCode());

		
	}

	@RequestMapping(method = RequestMethod.GET, value = "findByRequireCode/{requireCode}")
	@ApiOperation(value = "通过需求Code查询评价信息", httpMethod = "GET", notes = "")
	public @ResponseBody List<BabyEvaluation> findByRequireCode(
			@PathVariable @ApiParam(name = "requireCode", value = "需求编号", required = true) String requireCode) {

		BabyEvaluation queryBean = new BabyEvaluation();
		queryBean.setRequireCode(requireCode);
		return babyEvaluationService.findBySelective(queryBean);
	}

	// 不允许访问
	@ApiIgnore
	@Override
	public @ResponseBody List<BabyEvaluation> getAllEntity(
			@RequestHeader(value = "Page_Num", required = false) @ApiParam(name = "Page_Num", value = "当前页数", required = false) Integer pageNum,
			@RequestHeader(value = "Page_Size", required = false) @ApiParam(name = "Page_Size", value = "每页的数量", required = false) Integer pageSize,
			HttpServletResponse response) {
		throw EBusinessException.BUSINESS_PENDING;
	}

	// 不允许访问
	@ApiIgnore
	@Override
	public @ResponseBody List<BabyEvaluation> getCurEntity(
			@PathVariable @ApiParam(name = "property", value = "属性名称", required = true) String property,
			@PathVariable @ApiParam(name = "value", value = "属性值", required = true) Object value,
			@RequestHeader(value = "Page_Num", required = false) @ApiParam(name = "Page_Num", value = "当前页数", required = false) Integer pageNum,
			@RequestHeader(value = "Page_Size", required = false) @ApiParam(name = "Page_Size", value = "每页的数量", required = false) Integer pageSize,
			HttpServletResponse response) {
		throw EBusinessException.BUSINESS_PENDING;
	}

	// 不允许访问
	@ApiIgnore
	@Override
	public @ResponseBody List<BabyEvaluation> getCurEntity1(
			@PathVariable @ApiParam(name = "property1", value = "属性1名称", required = true) String property1,
			@PathVariable @ApiParam(name = "value1", value = "属性1值", required = true) Object value1,
			@PathVariable @ApiParam(name = "property2", value = "属性2名称", required = true) String property2,
			@PathVariable @ApiParam(name = "value2", value = "属性2值", required = true) Object value2,
			@RequestHeader(value = "Page_Num", required = false) @ApiParam(name = "Page_Num", value = "当前页数", required = false) Integer pageNum,
			@RequestHeader(value = "Page_Size", required = false) @ApiParam(name = "Page_Size", value = "每页的数量", required = false) Integer pageSize,
			HttpServletResponse response) throws IllegalAccessException, InstantiationException {
		throw EBusinessException.BUSINESS_PENDING;
	}

	@RequestMapping(method = RequestMethod.GET, value = "findMine")
	@ApiOperation(value = "查询与我相关的评价", httpMethod = "GET", notes = "半年内，我评价别从和别人评价我的记录")
	public @ResponseBody List<SimpleBabyEvaluation> findMine() {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		c.add(Calendar.MONTH, -6);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		Date startTime = c.getTime();

		return babyEvaluationService.findMine(SessionUtil.getSessionUser().getCode(), startTime, new Date());
	}
}
