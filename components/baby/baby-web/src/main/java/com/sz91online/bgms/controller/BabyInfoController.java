package com.sz91online.bgms.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.sz91online.bgms.foundation.web.session.UserSessionInfo;
import com.sz91online.bgms.module.baby.domain.BabyInfo;
import com.sz91online.bgms.module.baby.domain.BabyUserInfo;
import com.sz91online.bgms.module.baby.service.BabyInfoService;
import com.sz91online.common.db.service.ICrudService;
import com.sz91online.common.exceptions.EBusinessException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping(value = "babyInfo")
@Api(value = "/babyInfo", description = "宝贝信息接口")
public class BabyInfoController extends BaseController<BabyInfo> {

	@Autowired
	private BabyInfoService babyInfoService;

	@Override
	public ICrudService<BabyInfo> getService() {
		return babyInfoService;
	}

	// 不允许访问
	@ApiIgnore
	@Override
	public @ResponseBody List<BabyInfo> getAllEntity(
			@RequestHeader(value = "Page_Num", required = false) @ApiParam(name = "Page_Num", value = "当前页数", required = false) Integer pageNum,
			@RequestHeader(value = "Page_Size", required = false) @ApiParam(name = "Page_Size", value = "每页的数量", required = false) Integer pageSize,
			HttpServletResponse response) {
		throw EBusinessException.BUSINESS_PENDING;
	}

	// 不允许访问
	@ApiIgnore
	@Override
	public @ResponseBody List<BabyInfo> getCurEntity(
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
	public @ResponseBody List<BabyInfo> getCurEntity1(
			@PathVariable @ApiParam(name = "property1", value = "属性1名称", required = true) String property1,
			@PathVariable @ApiParam(name = "value1", value = "属性1值", required = true) Object value1,
			@PathVariable @ApiParam(name = "property2", value = "属性2名称", required = true) String property2,
			@PathVariable @ApiParam(name = "value2", value = "属性2值", required = true) Object value2,
			@RequestHeader(value = "Page_Num", required = false) @ApiParam(name = "Page_Num", value = "当前页数", required = false) Integer pageNum,
			@RequestHeader(value = "Page_Size", required = false) @ApiParam(name = "Page_Size", value = "每页的数量", required = false) Integer pageSize,
			HttpServletResponse response) throws IllegalAccessException, InstantiationException {
		throw EBusinessException.BUSINESS_PENDING;
	}

	// 不允许访问
	@ApiIgnore
	@Override
	public @ResponseBody void removeCruEntity(
			@PathVariable @ApiParam(name = "id", value = "操作记录的数据库主健", required = true) String id)
			throws IllegalAccessException, InstantiationException {
		throw EBusinessException.BUSINESS_PENDING;
	}

	@Override
	@RequestMapping(method = RequestMethod.POST, value = "current")
	@ApiOperation(value = "添加宝贝", httpMethod = "POST", notes = "返回执行保存记录后的记录，包含数据库主键id")
	public @ResponseBody BabyInfo saveCurEntity(
			@RequestBody @ApiParam(name = "宝贝信息", value = "传入json格式,不包括id和babyCode", required = true) BabyInfo babyInfo,
			HttpServletRequest request) {
		// 自动生成authCode
		babyInfo.setBabyCode("Bb" + new Date().getTime());
		BabyInfo result = super.saveCurEntity(babyInfo, request);

		// 添加用户和宝贝的关系
		UserSessionInfo user = SessionUtil.getSessionUser();
		babyInfoService.addBabyToUser(user.getCode(), result.getBabyCode());

		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "findByCode/{babyCode}")
	@ApiOperation(value = "通过宝贝Code查询宝贝信息", httpMethod = "GET", notes = "")
	public @ResponseBody BabyInfo findByCode(
			@PathVariable @ApiParam(name = "babyCode", value = "宝贝编号", required = true) String babyCode) {

		BabyInfo queryBean = new BabyInfo();
		queryBean.setBabyCode(babyCode);
		return getService().findOne(queryBean);
	}

	@RequestMapping(method = RequestMethod.GET, value = "findByUserCode")
	@ApiOperation(value = "通过用户Code查询用户信息", httpMethod = "GET", notes = "")
	public @ResponseBody List<BabyInfo> findByUserCode() {

		return babyInfoService.findByUserCode(SessionUtil.getSessionUser().getCode());
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "removeBaby/{babyCode}")
	@ApiOperation(value = "删除用户的宝贝信息", httpMethod = "DELETE", notes = "")
	public @ResponseBody void removeBaby(
			@PathVariable @ApiParam(name = "babyCode", value = "宝贝编号", required = true) String babyCode) {

		// 删除用户和宝贝的关系
		UserSessionInfo user = SessionUtil.getSessionUser();
		babyInfoService.removeBabyFromUser(user.getCode(), babyCode);

		BabyInfo queryBean = new BabyInfo();
		queryBean.setBabyCode(babyCode);
		BabyInfo result = babyInfoService.findOne(queryBean);
		babyInfoService.removeWithSession(result, SessionUtil.getSessionUser().getCode());
	}
}
