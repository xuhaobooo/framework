package com.sz91online.bgms.controller;

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
import com.sz91online.bgms.module.baby.domain.BabyUserInfo;
import com.sz91online.bgms.module.baby.domain.SimpleBabyUserInfo;
import com.sz91online.bgms.module.baby.service.BabyUserInfoService;
import com.sz91online.common.db.service.ICrudService;
import com.sz91online.common.exceptions.EBusinessException;
import com.sz91online.common.utils.PlStringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping(value = "babyUserInfo")
@Api(value = "/babyUserInfo", description = "用户基本信息")
public class BabyUserInfoController extends BaseController<BabyUserInfo> {

	@Autowired
	private BabyUserInfoService babyUserInfoService;

	@Override
	public ICrudService<BabyUserInfo> getService() {
		return babyUserInfoService;
	}

	// 不允许访问
	@ApiIgnore
	@Override
	public @ResponseBody List<BabyUserInfo> getAllEntity(
			@RequestHeader(value = "Page_Num", required = false) @ApiParam(name = "Page_Num", value = "当前页数", required = false) Integer pageNum,
			@RequestHeader(value = "Page_Size", required = false) @ApiParam(name = "Page_Size", value = "每页的数量", required = false) Integer pageSize,
			HttpServletResponse response) {
		throw EBusinessException.BUSINESS_PENDING;
	}

	// 不允许访问
	@ApiIgnore
	@Override
	public @ResponseBody List<BabyUserInfo> getCurEntity(
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
	public @ResponseBody List<BabyUserInfo> getCurEntity1(
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
	@ApiOperation(value = "添加用户信息", httpMethod = "POST", notes = "返回执行保存记录后的记录，包含数据库主键id")
	// 不允许访问
	@ApiIgnore
	public @ResponseBody BabyUserInfo saveCurEntity(
			@RequestBody @ApiParam(name = "用户信息", value = "传入json格式,不包括id. user_code必须传，为用户注册时系统生成的code", required = true) BabyUserInfo userInfo,
			HttpServletRequest request) {
		
		throw EBusinessException.BUSINESS_PENDING;
		
	}

	@RequestMapping(method = RequestMethod.POST, value = "addUserInfo")
	@ApiOperation(value = "添加用户信息", httpMethod = "POST", notes = "返回执行保存记录后的记录，包含数据库主键id")
	public @ResponseBody BabyUserInfo addUserInfo(
			@RequestBody @ApiParam(name = "用户信息", value = "传入json格式,不包括id. user_code必须传,为用户注册时系统生成的code.visitCode为邀请码", required = true) SimpleBabyUserInfo userInfo,
			HttpServletRequest request) {

		if (PlStringUtils.isEmpty(userInfo.getUserCode()) || PlStringUtils.isEmpty(userInfo.getUserName()) || PlStringUtils.isEmpty(userInfo.getAddrName())
				|| PlStringUtils.isEmpty(userInfo.getUserRole()) || PlStringUtils.isEmpty(userInfo.getTel())
				|| userInfo.getAddrPosX() == null || userInfo.getAddrPosY() == null) {
			throw EBusinessException.MIS_PARAMETER_ERROR;
		}

		BabyUserInfo result = babyUserInfoService.addUserInfo(userInfo);
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, value = "findByCode/{userCode}")
	@ApiOperation(value = "通过Code查询用户信息", httpMethod = "GET", notes = "")
	public @ResponseBody BabyUserInfo findByCode(
			@PathVariable @ApiParam(name = "userCode", value = "用户编号", required = true) String userCode) {

		BabyUserInfo queryBean = new BabyUserInfo();
		queryBean.setUserCode(userCode);
		return getService().findOne(queryBean);
	}

	@Override
	@ApiOperation(value = "增量更新记录", httpMethod = "PATCH", notes = "数据库主健id必须有，此操作为增量更新，未上传的字段不会被修改。返回执行保存记录后的记录")
	@RequestMapping(method = RequestMethod.PATCH, value = "update/{id}")
	public @ResponseBody BabyUserInfo updateCurEntity(
			@PathVariable @ApiParam(name = "id", value = "操作记录的数据库主健", required = true) String id,
			@RequestBody @ApiParam(name = "用户信息", value = "传入json格式", required = true) BabyUserInfo t) {
		
		t.setTel(null);
		return super.updateCurEntity(id, t);
	}
}
