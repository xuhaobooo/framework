package com.sz91online.bgms.module.user.exceptions;

import com.sz91online.common.exceptions.EBusinessException;

/**
 * Created by wendajun on 2016/2/25.
 */
public class EUserException extends EBusinessException{
	
	private static final long serialVersionUID = -2524275212267006703L;
	
	public EUserException(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}
	
	public static final EUserException LOGIN_NAME_EXIST_ERROR = new EUserException("10020003", "登录账号已经存在");
	public static final EUserException LOGIN_USER_NOT_EXIST_ERROR = new EUserException("10020005", "没有此用户");
	public static final EUserException PARENT_DEPT_NOT_EXIST_ERROR = new EUserException("10020014", "父部门不存在!");
	public static final EUserException MIS_CATHCHA = new EUserException("10010015", "验证码不正确!");
	public static final EUserException CANT_FIND_ALL_RECORD = new EUserException("10010016", "不能查询所有记录，请输入查询条件");;;
}
