package com.sz91online.bgms.test.service.user;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.sz91online.bgms.module.user.domain.SimpleAuth;
import com.sz91online.bgms.module.user.domain.User;
import com.sz91online.bgms.module.user.enums.UserStatusEnum;
import com.sz91online.bgms.module.user.exceptions.EUserException;
import com.sz91online.bgms.module.user.service.UserService;
import com.sz91online.common.ServiceApplication;
import com.sz91online.common.exceptions.EBusinessException;
import com.sz91online.common.utils.PlMD5Util;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ServiceApplication.class)
@Transactional
public class UserServiceTest {

	private final String testUserCode = "user123456";
	private final String testLoginName = "xuhaobooo";
	private final String testPassword = "123456";
	private final String testAuthCode = "TestAuth";
	private final String testRoleAuthCode = "TestRoleAuth";
	private final String testNotAssign = "TestNoAssign";

	@Autowired
	private UserService userService;

	@Rollback(true)
	@Test
	public void testUserAdd() {
		User user = new User();
		user.setFirstName("Paul2");
		user.setLastName("Xu2");
		user.setUserCode("testCode");
		user.setStatus(UserStatusEnum.NORMAL.getValue());
		user.setLoginName("testLoginName");
		user.setPassword(PlMD5Util.encrypt("test"));
		user.setCreateTime(new Date());
		user.setUserDesc("许浩波的测试账号");
		user.setDeptCode("DEPT1");
		userService.saveWithSession(user, "TEST");
		System.out.println("dfasdf");
	}

	@Rollback(true)
	@Test(expected=EBusinessException.class)
	public void testUserAddFail() throws EBusinessException{
		User user = new User();
		user.setFirstName("Paul");
		user.setLastName("Xu");
		user.setUserCode("user123456");
		user.setStatus(UserStatusEnum.NORMAL.getValue());
		user.setLoginName("testLoginName");
		user.setPassword(PlMD5Util.encrypt("test"));
		user.setCreateTime(new Date());
		user.setUserDesc("许浩波的测试账号");
		userService.saveWithSession(user, "TEST");
		System.out.println("dfasdf");
	}

	/**
	 * 测试通过userCode查询用户 有此用户
	 */
	@Test
	public void testFindUserByCode() {
		User user = userService.findUserByCode(testUserCode);
		Assert.notNull(user);
	}

	/**
	 * 测试通过userCode查询用户 无此用户
	 */
	@Test
	public void testFindUserByCodeNotExist() {
		User user = userService.findUserByCode("notExistCode");
		Assert.isNull(user);
	}

	/**
	 * 测试用户是否有某个权限，直接赋权限 有权限
	 */
	@Test
	public void testCheckAuthority() {
		boolean haveAuth = userService.checkAuthority(testUserCode, testAuthCode);
		Assert.isTrue(haveAuth);

		boolean haveAuth2 = userService.checkAuthority(testUserCode, testRoleAuthCode);
		Assert.isTrue(haveAuth2);
	}

	/**
	 * 测试用户是否有某个权限,直接赋权限 无权限
	 */
	@Test
	public void testCheckAuthorityFail() {

		boolean haveAuth = userService.checkAuthority(testUserCode, "notExistAuth");
		Assert.isTrue(!haveAuth);

		boolean haveAuth2 = userService.checkAuthority("notExistUserCode", testAuthCode);
		Assert.isTrue(!haveAuth2);

		boolean haveAuth3 = userService.checkAuthority(null, null);
		Assert.isTrue(!haveAuth3);

		boolean haveAuth4 = userService.checkAuthority(testUserCode, testNotAssign);
		Assert.isTrue(!haveAuth4);
	}

	/**
	 * 测试获取用户所有权限
	 */
	public void testFindAllAuthByUserCode() {
		List<SimpleAuth> authList = userService.findAuthByUserCode(testUserCode);
		Assert.isTrue(2 == authList.size());
	}
}
