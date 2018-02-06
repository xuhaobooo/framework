package com.sz91online.bgms.module.user.service.mybatis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sz91online.bgms.module.user.dao.UserMapper;
import com.sz91online.bgms.module.user.dao.UserMapperExt;
import com.sz91online.bgms.module.user.domain.Captcha;
import com.sz91online.bgms.module.user.domain.SimpleAuth;
import com.sz91online.bgms.module.user.domain.SimpleRole;
import com.sz91online.bgms.module.user.domain.SimpleUser;
import com.sz91online.bgms.module.user.domain.User;
import com.sz91online.bgms.module.user.exceptions.EUserException;
import com.sz91online.bgms.module.user.service.AuthService;
import com.sz91online.bgms.module.user.service.CaptchaService;
import com.sz91online.bgms.module.user.service.RoleService;
import com.sz91online.bgms.module.user.service.UserService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;
import com.sz91online.common.utils.PlMD5Util;
import com.sz91online.common.utils.PlStringUtils;

@Service
public class UserServiceImpl extends DefaultSearchService<User> implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserMapperExt userMapperExt;

	@Autowired
	private AuthService authService;

	@Autowired
	private RoleService roleService;

	@Autowired
	CaptchaService captchaService;

	@Override
	public ISearchableDAO getSearchMapper() {
		return userMapperExt;
	}

	@Override
	public ICrudGenericDAO<User> getCrudMapper() {
		return userMapper;
	}

	@Override
	public User findUserByCode(String userCode) {
		User queryBean = new User();
		queryBean.setUserCode(userCode);
		return findOne(queryBean);
	}

	@Override
	@Transactional
	public Integer updateWithSession(User record, String username) {
		if (record.getPassword() != null) {
			record.setPassword(PlMD5Util.encrypt(record.getPassword()));
		}
		return super.updateWithSession(record, username);
	}

	@Override
	public User findUserByLoginName(String loginName) {
		User queryBean = new User();
		queryBean.setLoginName(loginName);
		User resultBean = findOne(queryBean);
		return resultBean;
	}

	@Override
	public List<SimpleRole> findRoleByUserCode(String userCode) {
		return roleService.finRoleByUserCode(userCode);
	}

	@Override
	public boolean checkUserRole(String userCode, String roleCode) {
		if(userMapperExt.findOneUserRole(userCode, roleCode) != null) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public boolean checkUserAuth(String userCode, String authCode) {
		if(userMapperExt.findOneUserAuth(userCode, authCode) != null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean checkAuthority(String userCode, String authCode) {
		if (PlStringUtils.isEmpty(authCode) || PlStringUtils.isEmail(userCode)) {
			return false;
		}
		List<SimpleAuth> authList1 = authService.findAuthByUserCode(userCode);
		return authList1.stream().filter(article -> authCode.equals(article.getAuthCode())).findFirst().isPresent();
	}

	@Override
	public List<SimpleAuth> findAuthByUserCode(String userCode) {
		return authService.findAllAuthByUserCode(userCode);
	}

	@Override
	@Transactional
	public void addUserRole(String userCode, String roleCode) {
		userMapperExt.addUserRole(userCode, roleCode);

	}

	@Override
	@Transactional
	public void removeUserRole(String userCode, String roleCode) {
		userMapperExt.removeUserRole(userCode, roleCode);
	}

	@Override
	@Transactional
	public void addUserAuth(String userCode, String authCode) {
		userMapperExt.addUserAuth(userCode, authCode);
	}

	@Override
	@Transactional
	public void removeUserAuth(String userCode, String authCode) {
		userMapperExt.removeUserAuth(userCode, authCode);
	}

	@Override
	public Map<String, Map<String, String>> getUserByRole() {
		Map<String, Map<String, String>> result = new HashMap<>();
		List<SimpleUser> userList = userMapperExt.getAllUserWithRole();
		for (Iterator<SimpleUser> iterator = userList.iterator(); iterator.hasNext();) {
			SimpleUser simpleUser = (SimpleUser) iterator.next();
			Map<String, String> users = result.get(simpleUser.getRoleCode());
			if (users == null) {
				users = new HashMap<>();
				result.put(simpleUser.getRoleCode(), users);
			}

			if (!users.containsKey(simpleUser.getUserCode())) {
				users.put(simpleUser.getUserCode(), simpleUser.getLastName());
			}

		}
		return result;
	}

	@Override
	@Transactional
	public void modifyRoleUser(String roleId, String flag, List<String> UserCodes) {
		if ("add".equals(flag)) {
			for (Iterator<String> iterator = UserCodes.iterator(); iterator.hasNext();) {
				String userCode = (String) iterator.next();
				this.addUserRole(userCode, roleId);
			}
		} else {
			for (Iterator<String> iterator = UserCodes.iterator(); iterator.hasNext();) {
				String userCode = (String) iterator.next();
				this.removeUserRole(userCode, roleId);
			}
		}
	}

	@Override
	public List<SimpleUser> getUserByRoleCode(String roleId) {
		return userMapperExt.getUserByRoleCode(roleId);
	}

	@Override
	@Transactional
	public void saveCaptcha(String code, String mobile) {
		Captcha saveBean = new Captcha();
		saveBean.setCaptcha(code);
		saveBean.setMobilePhone(mobile);
		captchaService.saveWithSession(saveBean, "SMS");
	}

	@Override
	@Transactional
	public void forgetChangePassword(String loginName, String password, String captcha) {
		Captcha captQueryBean = new Captcha();
		captQueryBean.setMobilePhone(loginName);
		captQueryBean.setCaptcha(captcha);
		List<Captcha> captResultBean = captchaService.findBySelective(captQueryBean);
		if (captResultBean == null || captResultBean.size() == 0) {
			throw EUserException.MIS_CATHCHA;
		}

		User userQueryBean = new User();
		userQueryBean.setLoginName(loginName);
		User userResultBean = this.findOne(userQueryBean);

		if (userResultBean == null) {
			throw EUserException.LOGIN_USER_NOT_EXIST_ERROR;
		}

		userQueryBean.setPassword(PlMD5Util.encrypt(password));
		userQueryBean.setAccessCode(userResultBean.getAccessCode());
		userQueryBean.setUserCode(userResultBean.getUserCode());
		userQueryBean.setId(userResultBean.getId());
		this.updateByPrimaryKeySelective(userQueryBean, userResultBean.getUserCode());
	}

}
