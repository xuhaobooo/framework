package com.github.wxpay.sdk;

import com.sz91online.bgms.module.payment.wechat.MyConfig;

public class WXPayConfigImpl {

	private static MyConfig config = new MyConfig("wx0591f27a4ad89186", "1333917301",
			"TRVYFEXVEPBBMWCLWGRXHFTOVKTVMYWE");

	public static MyConfig getInstance() {
		return config;
	}

}
