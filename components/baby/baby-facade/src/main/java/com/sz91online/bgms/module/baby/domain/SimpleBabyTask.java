package com.sz91online.bgms.module.baby.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SimpleBabyTask extends BabyTask {

	private String babyName;
	private String tel;
	private String babySex;
	private Integer babyAge;

	private Float feeAmount;
	private Float payMore;
	private String creditCode;
	private String addrName;
	private Double addrPosX;
	private Double addrPosY;

	private Date requireStartTime;
	private Date requireEndTime;

	private String requireItems;
	private boolean paid;

	private List<BabyTaskStep> stepList;

	public List<BabyTaskStep> getStepList() {
		return stepList;
	}

	public void setStepList(List<BabyTaskStep> stepList) {
		this.stepList = stepList;
	}

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

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public Float getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(Float feeAmount) {
		this.feeAmount = feeAmount;
	}

	public Float getPayMore() {
		return payMore;
	}

	public void setPayMore(Float payMore) {
		this.payMore = payMore;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getAddrName() {
		return addrName;
	}

	public void setAddrName(String addrName) {
		this.addrName = addrName;
	}

	public Double getAddrPosX() {
		return addrPosX;
	}

	public void setAddrPosX(Double addrPosX) {
		this.addrPosX = addrPosX;
	}

	public Double getAddrPosY() {
		return addrPosY;
	}

	public void setAddrPosY(Double addrPosY) {
		this.addrPosY = addrPosY;
	}

}