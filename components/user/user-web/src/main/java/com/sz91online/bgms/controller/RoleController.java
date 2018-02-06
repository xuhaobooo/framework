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
import com.sz91online.bgms.module.user.domain.Role;
import com.sz91online.bgms.module.user.domain.SimpleUser;
import com.sz91online.bgms.module.user.domain.User;
import com.sz91online.bgms.module.user.exceptions.EUserException;
import com.sz91online.bgms.module.user.service.RoleService;
import com.sz91online.bgms.module.user.service.UserService;
import com.sz91online.common.db.service.ICrudService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping(value = "role")
@Api(value = "/role", description = "角色相关接口，包括增、删、改、查权限")
@ApiIgnore
public class RoleController extends BaseController<Role> {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Override
	public ICrudService<Role> getService() {
		return roleService;
	}

	/**
	 * 新增
	 * 
	 * @param t
	 * @return
	 */
	@Override
	@RequestMapping(method = RequestMethod.POST, value = "current")
	@ApiOperation(value = "添加新角色", httpMethod = "POST", notes = "返回执行保存记录后的记录，包含数据库主键id")
	public @ResponseBody Role saveCurEntity(
			@RequestBody @ApiParam(name = "角色对象", value = "传入json格式,不包括id和roleCode", required = true) Role role,
			HttpServletRequest request) {

		// 自动生成roleCode和默认密码
		role.setRoleCode("RL" + new Date().getTime());
		role.setStatus("N");
		return super.saveCurEntity(role, request);
	}

	/**
	 * 通过roleCode查询权限
	 */
	@ApiOperation(value = "通过roleCode查询权限", httpMethod = "POST", notes = "返回执行保存记录后的记录，包含数据库主键id")
	@RequestMapping(method = RequestMethod.GET, value = "getUserByRoleCode/{roleCode}")
	public @ResponseBody List<SimpleUser> getUserByRoleCode(
			@PathVariable @ApiParam(name = "roleCode", value = "角色编号，业务主键", required = true) String roleCode) {

		if (StringUtils.isEmpty(roleCode)) {
			throw EUserException.MIS_PARAMETER_ERROR;
		}
		return userService.getUserByRoleCode(roleCode);
	}

	/**
	 * 给角色添加或删除用户
	 */
	@ApiOperation(value = "给角色添加或删除用户", httpMethod = "POST", notes = "无返回")
	@RequestMapping(method = RequestMethod.POST, value = "modifyRoleUser/{roleCode}/{flag}")
	public @ResponseBody void modifyRoleUser(
			@PathVariable @ApiParam(name = "roleCode", value = "权限编号，业务主键", required = true) String roleCode,
			@PathVariable @ApiParam(name = "flag", value = "操作，值为add时添加权限，其他值为删除权限", required = true) String flag,
			@RequestBody @ApiParam(name = "userCodes", value = "用户编号列表", required = true) List<String> userCodes) {

		if (StringUtils.isEmpty(roleCode)) {
			throw EUserException.MIS_PARAMETER_ERROR;
		}
		if (StringUtils.isEmpty(flag)) {
			flag = "add";
		}
		userService.modifyRoleUser(roleCode, flag, userCodes);
	}
}
