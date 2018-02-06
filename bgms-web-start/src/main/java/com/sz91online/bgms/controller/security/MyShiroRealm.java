package com.sz91online.bgms.controller.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.sz91online.bgms.foundation.web.session.SessionUtil;
import com.sz91online.bgms.foundation.web.session.UserSessionInfo;
import com.sz91online.bgms.module.user.domain.SimpleAuth;
import com.sz91online.bgms.module.user.domain.SimpleRole;
import com.sz91online.bgms.module.user.domain.User;
import com.sz91online.bgms.module.user.service.UserService;

public class MyShiroRealm extends AuthorizingRealm {
	// 用于获取用户信息及用户权限信息的业务接口
	@Autowired
	private UserService userService;

	// 获取授权信息
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// String loginName = (String)
		// principals.fromRealm(getName()).iterator().next();
		User currentUser = (User) super.getAvailablePrincipal(principals);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<SimpleRole> roleList = userService.findRoleByUserCode(currentUser.getUserCode());
		Set<String> roles = new HashSet<>();
		roleList.forEach(o -> roles.add(o.getRoleName()));
		if (roles != null && !roles.isEmpty()) {
			info.addRoles(roles);
		}

		List<SimpleAuth> authList = userService.findAuthByUserCode(currentUser.getUserCode());
		Set<String> auths = new HashSet<>();
		authList.forEach(o -> auths.add(o.getAuthName()));
		if (auths != null && !auths.isEmpty()) {
			info.addStringPermissions(auths);
		}

		return info;
	}

	// 获取认证信息
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 通过表单接收的用户名
		String loginName = token.getUsername();

		if (loginName != null && !"".equals(loginName)) {
			try {
				User account = userService.findUserByLoginName(loginName);
				if (account != null) {
					return new SimpleAuthenticationInfo(account, account.getPassword(), getName());
				} else {
					account = userService.findUserByCode(loginName);
					if (account != null) {
						return new SimpleAuthenticationInfo(account, account.getAccessCode(), getName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return null;
	}

}
