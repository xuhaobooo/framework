package com.sz91online.bgms.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sz91online.bgms.foundation.web.exception.ELoginException;
import com.sz91online.bgms.foundation.web.session.SessionUtil;
import com.sz91online.bgms.foundation.web.session.UserSessionInfo;
import com.sz91online.bgms.module.user.domain.SimpleRole;
import com.sz91online.bgms.module.user.domain.User;
import com.sz91online.bgms.module.user.exceptions.EUserException;
import com.sz91online.bgms.module.user.service.UserService;
import com.sz91online.bgms.shiro.SecurityCodeUtil;
import com.sz91online.common.exceptions.EBusinessException;
import com.sz91online.common.utils.PlMD5Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/security")
@Api(value = "/security", description = "安全相关接口,登录,注销等")
public class SecurityController {

	@Autowired
	private UserService userService;

	// 没登录时的跳转接口,因现在只支持AJAX请求，所以返回JSON值而不是跳转。所以目前此接口不能使用
	@ApiIgnore
	@RequestMapping(value = "/needLogin")
	public @ResponseBody void needLogin(HttpServletRequest req, HttpServletResponse res) {
		throw ELoginException.USER_NEED_LOGIN;
	}

	@ApiOperation(value = "账号密码登录接口", httpMethod = "POST", notes = "如果登录成功，返回json对象，内容请参与下面的模型")
	@RequestMapping(value = "/login")
	public @ResponseBody UserSessionInfo login(
			@ApiParam(name = "loginName", value = "用户账号", required = true) @RequestParam String loginName,
			@ApiParam(name = "password", value = "用户密码", required = true) @RequestParam String password) {

		if (SecurityUtils.getSubject().isAuthenticated() || SecurityUtils.getSubject().getSession() != null) {
			SecurityUtils.getSubject().logout();
		}

		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)) {
			throw EUserException.MIS_PARAMETER_ERROR;
		}

		UsernamePasswordToken token = new UsernamePasswordToken(loginName, PlMD5Util.encrypt(password));

		Subject currentUser = SecurityUtils.getSubject();

		// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
		// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
		// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
		token.setRememberMe(true);
		currentUser.login(token);
		// 验证是否登录成功
		if (!currentUser.isAuthenticated()) {
			throw ELoginException.LOGIN_FAIL_ERROR;
		}

		User loginedUser = (User) currentUser.getPrincipal();
		
		UserSessionInfo userInfo = convertToSessionUser(loginedUser);
		//查询角色
		List<SimpleRole> roleList = userService.findRoleByUserCode(loginedUser.getUserCode());
		Set<String> roles = new HashSet<>();
		roleList.forEach(o -> roles.add(o.getRoleName()));
		if (roles != null && !roles.isEmpty()) {
			userInfo.setUserRole(roles);
		}
		
		if (userInfo != null) {
			SessionUtil.setSessionUser(userInfo);
		}
		return userInfo;
	}

	private UserSessionInfo convertToSessionUser(User account) {
		if (account == null) {
			return null;
		}
		UserSessionInfo info = new UserSessionInfo();
		info.setCode(account.getUserCode());
		info.setLoginName(account.getLoginName());
		info.setUserName(account.getLastName() + account.getFirstName());
		info.setId(account.getId());
		info.setAccessCode(account.getAccessCode());
		return info;
	}

	@ApiOperation(value = "token登录接口", httpMethod = "POST", notes = "如果登录成功，返回json对象，内容请参与下面的模型")
	@RequestMapping(value = "/tokenLogin", method = RequestMethod.POST)
	public @ResponseBody UserSessionInfo tokens(
			@ApiParam(name = "userCode", value = "用户代码", required = true) @RequestParam String userCode,
			@ApiParam(name = "token", value = "用户身份标识字符串", required = true) @RequestParam String token) {

		if (SecurityUtils.getSubject() != null && SecurityUtils.getSubject().getSession() != null) {
			SecurityUtils.getSubject().logout();
		}

		if (StringUtils.isEmpty(token) || StringUtils.isEmpty(userCode)) {
			throw EUserException.MIS_PARAMETER_ERROR;
		}

		UsernamePasswordToken validToken = new UsernamePasswordToken(userCode, token);
		validToken.setRememberMe(true);
		Subject currentUser = SecurityUtils.getSubject();
		// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
		// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
		// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
		currentUser.login(validToken);
		// 验证是否登录成功

		if (!currentUser.isAuthenticated()) {
			throw ELoginException.LOGIN_FAIL_ERROR;
		}
		User loginedUser = (User) currentUser.getPrincipal();

		UserSessionInfo userInfo = convertToSessionUser(loginedUser);
		if (userInfo != null) {
			SessionUtil.setSessionUser(userInfo);
		}
		return userInfo;
	}

	@ApiOperation(value = "登录接口", httpMethod = "GET", notes = "退出时清理")
	@RequestMapping(value = "/logout")
	public @ResponseBody void logout() {
		SecurityUtils.getSubject().logout();
	}

	@ApiIgnore
	@RequestMapping(value = "/authLock")
	public @ResponseBody void authLock(HttpServletRequest req, HttpServletResponse res) {
		throw ELoginException.USER_NO_AUTH;
	}

	@ApiIgnore
	@RequestMapping(value = "/loginSucc")
	public @ResponseBody void loginSucc(HttpServletRequest req, HttpServletResponse res) {
	}

	/**
	 * 获取手机验证码
	 * 
	 * @param mobiles
	 *            手机号码
	 * @return
	 */
	@RequestMapping(value = "/captcha/{mobile}", method = RequestMethod.POST)
	@ApiOperation(value = "/captcha/{mobile}", httpMethod = "POST", notes = "手机验证码发送接口")
	@ResponseBody
	public void sendMobileCode(
			@ApiParam(name = "mobile", value = "手机号码", required = true) @PathVariable(value = "mobile") String mobile) {
		Boolean isLegal = SecurityCodeUtil.isMobile(mobile);
		if (!isLegal) {
			throw new EBusinessException("00000011", "手机号码不合法");
		}
		// 调用短信网关发短
		String code = SecurityCodeUtil.sendMobileCode(mobile);
		userService.saveCaptcha(code, mobile);
	}

	/**
	 * 忘记密码
	 * 
	 * @param mobiles
	 *            手机号码
	 * @return
	 */
	@RequestMapping(value = "/forgetChangePassword", method = RequestMethod.POST)
	@ApiOperation(value = "/forgetChangePassword", httpMethod = "POST", notes = "手机验证码发送接口")
	@ResponseBody
	public void forgetChangePassword(
			@ApiParam(name = "loginName", value = "登录账号", required = true) @RequestParam(value = "loginName") String loginName,
			@ApiParam(name = "password", value = "密码", required = true) @RequestParam(value = "password") String password,
			@ApiParam(name = "captcha", value = "手机验证码", required = true) @RequestParam(value = "captcha") String captcha) {

		userService.forgetChangePassword(loginName, password, captcha);
	}
}
