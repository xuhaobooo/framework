package com.sz91online.bgms.module.user.domain;

public class SimpleAuth extends Auth {

	private static final long serialVersionUID = -980215213158951227L;
	private String userCode;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}