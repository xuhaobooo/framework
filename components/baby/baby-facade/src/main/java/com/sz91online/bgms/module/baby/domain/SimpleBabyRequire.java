package com.sz91online.bgms.module.baby.domain;

import java.util.List;

public class SimpleBabyRequire extends BabyRequire {

	private boolean applied;
	private String tel;

	private List<BabyRequireItem> items;

	private boolean isPaid;
	private Double distance;

	private String companyCode;
	private String companyName;
	private String companyTel;
	private String companyType;

	private List<SimpleBabyTaskStep> stepList;

	public List<BabyRequireItem> getItems() {
		return items;
	}

	public void setItems(List<BabyRequireItem> items) {
		this.items = items;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public boolean isApplied() {
		return applied;
	}

	public void setApplied(boolean applied) {
		this.applied = applied;
	}

	public List<SimpleBabyTaskStep> getStepList() {
		return stepList;
	}

	public void setStepList(List<SimpleBabyTaskStep> stepList) {
		this.stepList = stepList;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}