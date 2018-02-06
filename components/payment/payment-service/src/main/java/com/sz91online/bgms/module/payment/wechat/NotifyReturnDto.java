package com.sz91online.bgms.module.payment.wechat;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 */
public class NotifyReturnDto {
	@XStreamAlias("return_code")
	private String returnCode = "SUCCESS";
	@XStreamAlias("return_msg")
	private String returnMsg = "OK";
	
	public NotifyReturnDto() {
		
	}
	
	public NotifyReturnDto(String returnMsg) {
		this.returnCode = "FAIL";
		this.returnMsg = returnMsg;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

}
