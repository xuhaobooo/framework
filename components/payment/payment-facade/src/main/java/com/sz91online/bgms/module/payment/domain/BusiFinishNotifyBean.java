package com.sz91online.bgms.module.payment.domain;

import java.util.Date;

public class BusiFinishNotifyBean {
	private String userCode;
	private Float amount;
	private Date time;
	private String busiCode;
	private String busiType;
	private String inviteUserCode;

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getInviteUserCode() {
		return inviteUserCode;
	}

	public void setInviteUserCode(String inviteUserCode) {
		this.inviteUserCode = inviteUserCode;
	}

}
