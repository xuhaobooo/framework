package com.sz91online.bgms.module.payment.wechat;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.github.wxpay.sdk.WXPayConfig;

public class MyConfig implements WXPayConfig {

	private byte[] certData;
	
	private String appId;
	private String mchId;
	private String key;
	
	public MyConfig(String appId, String mchId, String key) {
		
		this.appId = appId;
		this.mchId = mchId;
		this.key = key;
		
		/*String certPath = "/path/to/apiclient_cert.p12";
		File file = new File(certPath);
		InputStream certStream = new FileInputStream(file);
		this.certData = new byte[(int) file.length()];
		certStream.read(this.certData);
		certStream.close();*/
	}

	public String getAppID() {
		return appId;
	}

	public String getMchID() {
		return mchId;
	}

	public String getKey() {
		return key;
	}

	public InputStream getCertStream() {
		ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
		return certBis;
	}

	public int getHttpConnectTimeoutMs() {
		return 8000;
	}

	public int getHttpReadTimeoutMs() {
		return 10000;
	}
}