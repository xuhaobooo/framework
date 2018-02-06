package com.sz91online.bgms.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sz91online.bgms.foundation.web.controller.BaseController;
import com.sz91online.bgms.module.user.domain.Auth;
import com.sz91online.bgms.module.user.exceptions.EUserException;
import com.sz91online.bgms.module.user.service.AuthService;
import com.sz91online.common.db.service.ICrudService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping(value = "auth")
@Api(value = "/auth", description = "权限相关接口，包括增、删、改、查权限。　给角色添加、删除权限的接口")
@ApiIgnore
public class AuthController extends BaseController<Auth> {

	@Autowired
	private AuthService autService;

	@Override
	public ICrudService<Auth> getService() {
		return autService;
	}

	/**
	 * 新增
	 * 
	 * @param t
	 * @return
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST, value = "current")
	@ApiOperation(value = "添加新权限", httpMethod = "POST", notes = "返回执行保存记录后的记录，包含数据库主键id")
	public @ResponseBody Auth saveCurEntity(
			@RequestBody @ApiParam(name = "权限对象", value = "传入json格式,不包括id和authCode", required = true) Auth auth,
			HttpServletRequest request) {
		// 自动生成authCode
		auth.setAuthCode("AU" + new Date().getTime());
		return super.saveCurEntity(auth, request);
	}

	/**
	 * 通过roleCode查询权限
	 */
	@RequestMapping(method = RequestMethod.GET, value = "getAuthByRoleCode/{roleCode}")
	@ApiOperation(value = "通过roleCode查询权限", httpMethod = "GET", notes = "")
	public @ResponseBody List<Auth> getAuthByRoleCode(
			@PathVariable @ApiParam(name = "roleCode", value = "权限编号，业务主键", required = true) String roleCode) {

		if (StringUtils.isEmpty(roleCode)) {
			throw EUserException.MIS_PARAMETER_ERROR;
		}
		return autService.getAuthByRoleCode(roleCode);
	}

	/**
	 * 添加删除权限
	 */
	@ApiOperation(value = "给角色添加或删除权限", httpMethod = "POST", notes = "无返回")
	@RequestMapping(method = RequestMethod.POST, value = "modifyRoleAuth/{roleCode}/{flag}")
	public @ResponseBody void modifyRoleAuth(
			@PathVariable @ApiParam(name = "roleCode", value = "权限编号，业务主键", required = true) String roleCode,
			@PathVariable @ApiParam(name = "flag", value = "操作，值为add时添加权限，其他值为删除权限", required = true) String flag,
			@RequestBody @ApiParam(name = "auths", value = "权限编号列表", required = true) List<String> auths) {

		if (StringUtils.isEmpty(roleCode)) {
			throw EUserException.MIS_PARAMETER_ERROR;
		}

		if (auths == null || auths.size() == 0) {
			throw EUserException.MIS_PARAMETER_ERROR;
		}
		if (StringUtils.isEmpty(flag)) {
			flag = "add";
		}
		autService.modifyRoleAuth(roleCode, flag, auths);
	}

}
