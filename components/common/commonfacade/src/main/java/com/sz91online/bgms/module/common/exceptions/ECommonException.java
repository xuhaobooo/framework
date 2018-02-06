package com.sz91online.bgms.module.common.exceptions;

import com.sz91online.common.exceptions.EBusinessException;

public class ECommonException extends EBusinessException{
	
	private static final long serialVersionUID = -1422746504005500030L;

	public ECommonException(String resultCode, String resultMsg) {
		super(resultCode, resultMsg);
	}
	
	public static final ECommonException OPTION_IS_READONLY_ERROR = new ECommonException("1011001", "选项不能修改，只能删除后重录入");
}
