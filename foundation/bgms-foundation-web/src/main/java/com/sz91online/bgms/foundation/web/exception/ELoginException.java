package com.sz91online.bgms.foundation.web.exception;

import com.sz91online.common.exceptions.EBusinessException;

public class ELoginException extends EBusinessException{
	
	private static final long serialVersionUID = 1L;
	public ELoginException(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}
	
	public static final ELoginException USER_NEED_LOGIN = new ELoginException("10010000", "请先登录!");
	public static final ELoginException USER_NO_AUTH = new ELoginException("10010001", "没有权限访问");
	public static final ELoginException NAME_PWD_ERROR = new ELoginException("10010002", "你输入的账号或密码错误，请重新输入");
	public static final ELoginException LOGIN_FAIL_ERROR = new ELoginException("10010004", "登录失败");
	public static final ELoginException LOGIN_USER_NOT_EXIST_ERROR = new ELoginException("10010005", "用户已被锁定");
	public static final ELoginException LOGIN_USER_BEEN_LOCKED = new ELoginException("10010006", "用户不存在");
}
