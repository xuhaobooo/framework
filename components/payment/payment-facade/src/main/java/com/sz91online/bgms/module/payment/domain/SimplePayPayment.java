package com.sz91online.bgms.module.payment.domain;

import java.util.List;

public class SimplePayPayment extends PayPayment {

	private static final long serialVersionUID = -456818932369973281L;

	private String busiType;

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

}