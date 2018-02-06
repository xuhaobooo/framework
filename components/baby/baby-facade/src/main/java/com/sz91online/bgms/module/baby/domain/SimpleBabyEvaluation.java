package com.sz91online.bgms.module.baby.domain;

import java.util.Date;

public class SimpleBabyEvaluation extends BabyEvaluation {
	private String babyName;
	private String babySex;
	private Integer babyAge;
	private Float feeAmount;
	private Date requireStartTime;
	private Date requireEndTime;

	private String userCode;
	private String jigouCode;
	private String jigouName;

	private String requireItems;

	public String getBabyName() {
		return babyName;
	}

	public void setBabyName(String babyName) {
		this.babyName = babyName;
	}

	public String getBabySex() {
		return babySex;
	}

	public void setBabySex(String babySex) {
		this.babySex = babySex;
	}

	public Integer getBabyAge() {
		return babyAge;
	}

	public void setBabyAge(Integer babyAge) {
		this.babyAge = babyAge;
	}

	public Float getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Float feeAmount) {
		this.feeAmount = feeAmount;
	}

	public Date getRequireStartTime() {
		return requireStartTime;
	}

	public void setRequireStartTime(Date requireStartTime) {
		this.requireStartTime = requireStartTime;
	}

	public Date getRequireEndTime() {
		return requireEndTime;
	}

	public void setRequireEndTime(Date requireEndTime) {
		this.requireEndTime = requireEndTime;
	}

	public String getRequireItems() {
		return requireItems;
	}

	public void setRequireItems(String requireItems) {
		this.requireItems = requireItems;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getJigouCode() {
		return jigouCode;
	}

	public void setJigouCode(String jigouCode) {
		this.jigouCode = jigouCode;
	}

	public String getJigouName() {
		return jigouName;
	}

	public void setJigouName(String jigouName) {
		this.jigouName = jigouName;
	}

}