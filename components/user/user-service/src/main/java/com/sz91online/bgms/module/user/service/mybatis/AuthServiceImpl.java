package com.sz91online.bgms.module.user.service.mybatis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sz91online.bgms.module.user.dao.AuthMapper;
import com.sz91online.bgms.module.user.dao.AuthMapperExt;
import com.sz91online.bgms.module.user.domain.Auth;
import com.sz91online.bgms.module.user.domain.Dept;
import com.sz91online.bgms.module.user.domain.SimpleAuth;
import com.sz91online.bgms.module.user.domain.User;
import com.sz91online.bgms.module.user.exceptions.EUserException;
import com.sz91online.bgms.module.user.service.AuthService;
import com.sz91online.bgms.module.user.service.DeptService;
import com.sz91online.bgms.module.user.service.UserService;
import com.sz91online.common.db.service.DefaultSearchService;
import com.sz91online.common.db.service.ICrudGenericDAO;
import com.sz91online.common.db.service.ISearchableDAO;

@Service
public class AuthServiceImpl extends DefaultSearchService<Auth> implements AuthService {

	@Autowired
	private AuthMapper authMapper;

	@Autowired
	private AuthMapperExt authMapperExt;

	@Override
	public ISearchableDAO getSearchMapper() {
		return authMapperExt;
	}

	@Override
	public ICrudGenericDAO<Auth> getCrudMapper() {
		return authMapper;
	}

	@Override
	public List<SimpleAuth> findAuthByUserCode(String userCode) {
		return authMapperExt.findAuthByUserCode(userCode);
	}

	@Override
	public List<SimpleAuth> findAuthByUserCodeWithRole(String userCode) {
		return authMapperExt.findAuthByUserCodeWithRole(userCode);
	}

	@Override
	public List<SimpleAuth> findAllAuthByUserCode(String userCode) {

		List<SimpleAuth> allAuth = new ArrayList<>();

		// 用来排除相同的权限
		Set<String> allAuthCode = new HashSet<String>();

		List<SimpleAuth> authList1 = findAuthByUserCode(userCode);
		for (Iterator<SimpleAuth> iterator = authList1.iterator(); iterator.hasNext();) {
			SimpleAuth auth = iterator.next();
			if (!allAuthCode.contains(auth.getAuthCode())) {
				allAuth.add(auth);
				allAuthCode.add(auth.getAuthCode());
			}
		}

		List<SimpleAuth> authList2 = findAuthByUserCodeWithRole(userCode);
		for (Iterator<SimpleAuth> iterator = authList2.iterator(); iterator.hasNext();) {
			SimpleAuth auth = iterator.next();
			if (!allAuthCode.contains(auth.getAuthCode())) {
				allAuth.add(auth);
				allAuthCode.add(auth.getAuthCode());
			}
		}

		SimpleAuth baseAuth = new SimpleAuth();
		baseAuth.setAuthCode("user");
		baseAuth.setAuthName("基础权限");
		baseAuth.setAuthDesc("每个员工都有的权限");
		baseAuth.setAuthType(true);
		baseAuth.setUserCode(userCode);
		allAuth.add(baseAuth);

		return allAuth;
	}

	private SimpleAuth cloneAuth(SimpleAuth auth) {
		SimpleAuth simpleAuth = new SimpleAuth();
		simpleAuth.setAuthCode(auth.getAuthCode());
		simpleAuth.setAuthDesc(auth.getAuthDesc());
		simpleAuth.setAuthName(auth.getAuthName());
		simpleAuth.setAuthType(auth.getAuthType());
		simpleAuth.setUserCode(auth.getUserCode());
		simpleAuth.setId(auth.getId());
		return simpleAuth;
	}

	@Override
	public List<Auth> getAuthByRoleCode(String roleCode) {
		return authMapperExt.getAuthByRoleCode(roleCode);
	}

	@Override
	public void modifyRoleAuth(String roleCode, String flag, List<String> auths) {
		if ("add".equals(flag)) {
			for (Iterator<String> iterator = auths.iterator(); iterator.hasNext();) {
				String authCode = (String) iterator.next();
				authMapperExt.addRoleAuth(roleCode, authCode);
			}
		} else {
			for (Iterator<String> iterator = auths.iterator(); iterator.hasNext();) {
				String authCode = (String) iterator.next();
				authMapperExt.removeRoleAuth(roleCode, authCode);
			}
		}
	}

}
