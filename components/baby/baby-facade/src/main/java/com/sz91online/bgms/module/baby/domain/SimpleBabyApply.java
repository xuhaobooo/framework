package com.sz91online.bgms.module.baby.domain;

public class SimpleBabyApply extends BabyApply {

	private String userName;

	private String tel;
	private String userRole;

	private String note;

	private String addrName;
	private Double addrPosX;
	private Double addrPosY;

	private Double distance;

	private Integer creditValue;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
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

	public Integer getCreditValue() {
		return creditValue;
	}

	public void setCreditValue(Integer creditValue) {
		this.creditValue = creditValue;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}