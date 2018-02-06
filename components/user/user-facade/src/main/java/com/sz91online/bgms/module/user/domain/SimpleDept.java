package com.sz91online.bgms.module.user.domain;

public class SimpleDept extends Dept {

	private String parentDeptName;

	public String getParentDeptName() {
		return parentDeptName;
	}

	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}

}