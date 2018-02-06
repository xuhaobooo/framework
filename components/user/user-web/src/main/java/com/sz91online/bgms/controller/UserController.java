package com.sz91online.bgms.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sz91online.bgms.foundation.web.controller.BaseController;
import com.sz91online.bgms.foundation.web.session.SessionUtil;
import com.sz91online.bgms.module.user.domain.Captcha;
import com.sz91online.bgms.module.user.domain.SimpleAuth;
import com.sz91online.bgms.module.user.domain.SimpleRole;
import com.sz91online.bgms.module.user.domain.User;
import com.sz91online.bgms.module.user.enums.UserStatusEnum;
import com.sz91online.bgms.module.user.exceptions.EUserException;
import com.sz91online.bgms.module.user.service.CaptchaService;
import com.sz91online.bgms.module.user.service.UserService;
import com.sz91online.common.db.service.ICrudService;
import com.sz91online.common.exceptions.EBusinessException;
import com.sz91online.common.utils.PlIdWork;
import com.sz91online.common.utils.PlMD5Util;
import com.sz91online.common.utils.PlStringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping(value = "user")
@Api(value = "/user", description = "用户相关接口，包括增、删、改、查用户和用户角色、权限的查询、添加和删除")
public class UserController extends BaseController<User> {

	@Autowired
	private UserService userService;

	@Autowired
	private CaptchaService captchaService;

	@Override
	public ICrudService<User> getService() {
		return userService;
	}

	// 不允许访问
	@ApiIgnore
	@Override
	public @ResponseBody List<User> getAllEntity(
			@RequestHeader(value = "Page_Num", required = false) @ApiParam(name = "Page_Num", value = "当前页数", required = false) Integer pageNum,
			@RequestHeader(value = "Page_Size", required = false) @ApiParam(name = "Page_Size", value = "每页的数量", required = false) Integer pageSize,
			HttpServletResponse response) {
		throw EBusinessException.BUSINESS_PENDING;
	}

	// 不允许访问
	@ApiIgnore
	@Override
	public @ResponseBody List<User> getCurEntity(
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
	public @ResponseBody List<User> getCurEntity1(
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
	@ApiOperation(value = "添加新用户", httpMethod = "POST", notes = "返回执行保存记录后的记录，包含数据库主键id")
	public @ResponseBody User saveCurEntity(
			@RequestBody @ApiParam(name = "user", value = "传入json格式,必传login_name,last_name,password.login_name必须是手机号码", required = true) User user,
			HttpServletRequest request) {

		if (PlStringUtils.isEmpty(user.getLoginName()) || PlStringUtils.isEmpty(user.getLastName())) {
			throw EBusinessException.MIS_PARAMETER_ERROR;
		}

		if (PlStringUtils.isEmpty(user.getCathcha())) {
			throw EBusinessException.MIS_PARAMETER_ERROR;
		}

		Captcha captQueryBean = new Captcha();
		captQueryBean.setMobilePhone(user.getLoginName());
		captQueryBean.setCaptcha(user.getCathcha());
		List<Captcha> captResultBean = captchaService.findBySelective(captQueryBean);
		if (captResultBean == null || captResultBean.size() == 0) {
			throw EUserException.MIS_CATHCHA;
		}

		// 判断loginName 重复
		User paramBean = new User();
		paramBean.setLoginName(user.getLoginName());
		User queryBean = getService().findOne(paramBean);

		if (queryBean != null) {
			throw EUserException.LOGIN_NAME_EXIST_ERROR;
		}

		// 自动生成userCode和默认密码
		user.setUserCode("US" + new Date().getTime());
		if (user.getPassword() == null) {
			user.setPassword(PlMD5Util.encrypt("123456"));
		} else {
			user.setPassword(PlMD5Util.encrypt(user.getPassword()));
		}

		// 设置用户身份字符串
		user.setAccessCode(new PlIdWork().nextString());

		user.setStatus(UserStatusEnum.NORMAL.getValue());
		user.setCreateTime(new Date());

		// baseVo.setData(t);
		userService.saveWithSession(user, "system");

		user.setPassword(null);
		return user;
	}

	/**
	 * 全量修改
	 * 
	 * @param t
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PATCH, value = "current/{id}")
	@Override
	@ApiOperation(value = "全量修改记录", httpMethod = "PATCH", notes = "数据库主健id必须有，此操作为全量更新，未上传的字段会被替换为空值。如果只更新某些字段，请使用。返回执行保存记录后的记录")
	public @ResponseBody User modifyCurEntity(
			@PathVariable @ApiParam(name = "id", value = "数据库主键,id", required = true) String id,
			@RequestBody @ApiParam(name = "用户对象", value = "传入json格式", required = true) User t) {

		if (id == null) {
			throw EBusinessException.MIS_PARAMETER_ERROR;
		} else {
			Long curId = t.getId();
			if (curId == null) {
				t.setId(Long.parseLong(id));
			}
			User queryBean = getService().findByPrimaryKey(Long.parseLong(id));
			if (queryBean == null) {
				throw EBusinessException.DB_RECORD_NOT_EXIST;
			} else {
				t.setPassword(queryBean.getPassword());
			}

			if (queryBean.getLoginName().equals("13312312312") || queryBean.getLoginName().equals("13412312312")) {
				throw new EBusinessException("12323123", "游客用户不能修改个人信息");
			}

			int count = getService().updateWithSession(t, SessionUtil.getSessionUser().getCode());
			if (count == 0) {
				throw EBusinessException.DB_UPDATE_RESULT_0;
			}
			return t;
		}

	}

	@Override
	@ApiOperation(value = "增量更新用户信息", httpMethod = "PATCH", notes = "修改密码可用此接口，只需传id,userCode和密码")
	@RequestMapping(method = RequestMethod.PATCH, value = "update/{id}")
	public @ResponseBody User updateCurEntity(
			@PathVariable @ApiParam(name = "id", value = "操作记录的数据库主健", required = true) String id,
			@RequestBody @ApiParam(name = "业务对象", value = "传入json格式", required = true) User user) {

		if (id == null) {
			throw EBusinessException.MIS_PARAMETER_ERROR;
		} else {

			User queryBean = getService().findByPrimaryKey(Long.parseLong(id));
			if (queryBean == null) {
				throw EBusinessException.DB_RECORD_NOT_EXIST;
			}

			if (queryBean.getLoginName().equals("13312312312") || queryBean.getLoginName().equals("13412312312")) {
				throw new EBusinessException("12323123", "游客用户不能修改个人信息");
			}

			user.setPassword(PlMD5Util.encrypt(user.getPassword()));

			int count = getService().updateByPrimaryKeySelective(user, SessionUtil.getSessionUser().getCode());

			if (count == 0) {
				throw EBusinessException.DB_UPDATE_RESULT_0;
			}

			return user;
		}
	}

	@ApiOperation(value = "通过用户编号查询用户", httpMethod = "GET", notes = "")
	@RequestMapping(method = RequestMethod.GET, value = "findByCode/{userCode}")
	public @ResponseBody User findByCode(
			@PathVariable @ApiParam(name = "userCode", value = "用户编号，业务主键", required = true) String userCode) {

		User queryBean = new User();
		queryBean.setUserCode(userCode);
		return userService.findOne(queryBean);
	}

	@RequiresRoles(value = { "admin" })
	@ApiOperation(value = "通过用户编号查询用户角色", httpMethod = "GET", notes = "返回权限的set集合，不重复。格式为 用户编号_部门编号_权限编号。如果部门编号为public，表明是通用权限，与部门无关")
	@RequestMapping(method = RequestMethod.GET, value = "userRole/{userCode}")
	public @ResponseBody Set<String> userRole(
			@PathVariable @ApiParam(name = "userCode", value = "用户编号，业务主键", required = true) String userCode) {

		Set<String> myRoleSet = new HashSet<>();
		List<SimpleRole> roleList = userService.findRoleByUserCode(userCode);
		roleList.forEach(userRole -> myRoleSet.add(userRole.getRoleCode()));
		return myRoleSet;
	}

	@RequiresRoles(value = { "admin" })
	@ApiOperation(value = "给用户添加角色", httpMethod = "PATCH", notes = "无返回值")
	@RequestMapping(method = RequestMethod.PATCH, value = "userRole/{userCode}/{deptCode}/{roleCode}")
	public @ResponseBody void addUserRole(
			@PathVariable @ApiParam(name = "userCode", value = "用户编号，业务主键", required = true) String userCode,
			@PathVariable @ApiParam(name = "deptCode", value = "部门编号，业务主键", required = true) String deptCode,
			@PathVariable @ApiParam(name = "roleCode", value = "角色编号，业务主键", required = true) String roleCode) {

		boolean flag = userService.checkUserRole(userCode, roleCode);
		if (flag) {
			throw EUserException.DB_RECORD_EXIST;
		}

		userService.addUserRole(userCode, roleCode);
	}

	@RequiresRoles(value = { "admin" })
	@ApiOperation(value = "移除用户角色", httpMethod = "DELETE", notes = "无返回值")
	@RequestMapping(method = RequestMethod.DELETE, value = "userRole/{userCode}/{deptCode}/{roleCode}")
	public @ResponseBody void removeUserRole(
			@PathVariable @ApiParam(name = "userCode", value = "用户编号，业务主键", required = true) String userCode,
			@PathVariable @ApiParam(name = "deptCode", value = "部门编号，业务主键", required = true) String deptCode,
			@PathVariable @ApiParam(name = "roleCode", value = "角色编号，业务主键", required = true) String roleCode) {

		boolean flag = userService.checkUserRole(userCode, roleCode);
		if (!flag) {
			throw EUserException.DB_RECORD_NOT_EXIST;
		}

		userService.removeUserRole(userCode, roleCode);
	}

	@ApiIgnore
	@RequiresRoles(value = { "admin" })
	@ApiOperation(value = "查询用户权限", httpMethod = "GET", notes = "权限编号的set集合，不重复。格式为：  用户编号_部门编号_权限编号")
	@RequestMapping(method = RequestMethod.GET, value = "userAuth/{userCode}")
	public @ResponseBody Set<String> userAuth(
			@PathVariable @ApiParam(name = "userCode", value = "用户编号，业务主键", required = true) String userCode) {

		Set<String> myAuthSet = new HashSet<>();
		List<SimpleAuth> authList = userService.findAuthByUserCode(userCode);
		authList.forEach(userAuth -> myAuthSet.add(userAuth.getAuthCode()));
		return myAuthSet;
	}

	@RequiresRoles(value = { "admin" })
	@ApiOperation(value = "给用户添加权限", httpMethod = "PATCH", notes = "无返回")
	@RequestMapping(method = RequestMethod.PATCH, value = "userAuth/{userCode}/{deptCode}/{authCode}")
	public @ResponseBody void addUserAuth(
			@PathVariable @ApiParam(name = "userCode", value = "用户编号，业务主键", required = true) String userCode,
			@PathVariable @ApiParam(name = "deptCode", value = "部门编号，业务主键", required = true) String deptCode,
			@PathVariable @ApiParam(name = "authCode", value = "权限编号，业务主键", required = true) String authCode) {

		boolean flag = userService.checkUserAuth(userCode, authCode);
		if (flag) {
			throw EUserException.DB_RECORD_EXIST;
		}

		userService.addUserAuth(userCode, authCode);
	}

	@RequiresRoles(value = { "admin" })
	@ApiOperation(value = "移除用户权限", httpMethod = "DELETE", notes = "无返回值")
	@RequestMapping(method = RequestMethod.DELETE, value = "userAuth/{userCode}/{deptCode}/{authCode}")
	public @ResponseBody void removeUserAuth(
			@PathVariable @ApiParam(name = "userCode", value = "用户编号，业务主键", required = true) String userCode,
			@PathVariable @ApiParam(name = "deptCode", value = "部门编号，业务主键", required = true) String deptCode,
			@PathVariable @ApiParam(name = "authCode", value = "权限编号，业务主键", required = true) String authCode) {

		boolean flag = userService.checkUserAuth(userCode, authCode);
		if (!flag) {
			throw EUserException.DB_RECORD_NOT_EXIST;
		}

		userService.removeUserAuth(userCode, authCode);
	}

	/**
	 * 用来查看用户所有被赋予和继承的权限
	 * 
	 * @param userCode
	 * @return
	 */
	@ApiIgnore
	@RequiresRoles(value = { "admin" })
	@ApiOperation(value = "查询用户所有权限，包括直被赋予的权限和角色赋予胡权限", httpMethod = "GET", notes = "权限编号的set集合，不重复。格式为：  用户编号_部门编号_权限编号")
	@RequestMapping(method = RequestMethod.GET, value = "viewUserAuth/{userCode}")
	public @ResponseBody Set<String> viewUserAuth(
			@PathVariable @ApiParam(name = "userCode", value = "用户编号，业务主键", required = true) String userCode) {

		Set<String> myAuthSet = new HashSet<>();
		List<SimpleAuth> publicAuthList = userService.findAuthByUserCode(userCode);
		publicAuthList.forEach(userAuth -> myAuthSet.add(userAuth.getAuthCode()));
		return myAuthSet;
	}

	/**
	 * 查询每个角色被赋予了哪些用户
	 * 
	 * @return
	 */
	@ApiOperation(value = "查询每个角色被赋予了哪些用户", httpMethod = "GET", notes = "返回Map,key是权限编号，值为一个Map，key为用户编号，值为用户名称")
	@RequestMapping(method = RequestMethod.GET, value = "getUserByRole")
	@RequiresRoles(value = { "admin" })
	public @ResponseBody Map<String, Map<String, String>> getUserByRole() {

		return userService.getUserByRole();
	}

	/**
	 * 
	 * @return
	 */
	@RequiresRoles(value = { "admin" })
	@ApiOperation(value = "查询用户记录", httpMethod = "GET", notes = "必须最少一个查询条件")
	@RequestMapping(value = "listUser", method = RequestMethod.GET)
	public @ResponseBody List<User> listUser(@RequestParam Map<String, String> map, HttpServletResponse response) {
		String loginName = map.get("loginName");
		String status = map.get("status");
		String lastName = map.get("lastName");
		
		boolean flag = false;
		
		User queryBean = new User();
		if(PlStringUtils.isNotEmpty(loginName)){
			queryBean.setLoginName(loginName);
			flag = true;
		}
		if(PlStringUtils.isNotEmpty(status)){
			queryBean.setStatus(status);
			flag = true;
		}
		if(PlStringUtils.isNotEmpty(lastName)){
			queryBean.setLastName(lastName);
			flag = true;
		}
		
		String currentPage = map.get("currentPage");
		if(currentPage == null){
			currentPage = "1";
		}
		String pageSize = map.get("pageSize");
		if(pageSize == null){
			pageSize = "10";
		}
		
		int curPageInt = Integer.parseInt(currentPage);
		int pageSizeInt = Integer.parseInt(pageSize);
		if(pageSizeInt >50){
			pageSizeInt = 50;
		}
		
		PageHelper.startPage(curPageInt, pageSizeInt);
		
		if(flag) {
			List<User> userList = userService.findBySelective(queryBean);
			
			PageInfo<User> pageUser = new PageInfo<User>(userList);

			response.setHeader("x-total-count", String.valueOf(pageUser.getTotal())); // 总记录数
			response.setHeader("x-current-page", String.valueOf(pageUser.getPageNum())); // 当前显示第几页
			response.setHeader("x-page-size", String.valueOf(pageUser.getPageSize())); // 每页的记录数
			response.setHeader("x-page-count", String.valueOf(pageUser.getPages())); // 总共多少页
			response.setHeader("x-cur-page-record-size", String.valueOf(pageUser.getSize())); // 当前页有多少条记录

			
			for (Iterator iterator = userList.iterator(); iterator.hasNext();) {
				User user = (User) iterator.next();
				user.setPassword(null);
			}
			
			return userList;
		}else {
			throw EUserException.CANT_FIND_ALL_RECORD;
		}
		
	}
	
	@ApiOperation(value = "查询当前登录用户", httpMethod = "GET", notes = "")
	@RequestMapping(value = "currentUser", method = RequestMethod.GET)
	public @ResponseBody User currentUser() {
		String userCode = SessionUtil.getSessionUser().getCode();
		return userService.findUserByCode(userCode);
	}
	
}
