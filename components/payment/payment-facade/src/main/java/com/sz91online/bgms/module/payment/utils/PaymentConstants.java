package com.sz91online.bgms.module.payment.utils;

import org.springframework.stereotype.Component;

@Component
public class PaymentConstants {

	public String pay_callback = "http://www.co-mama.cn/bgms/alipay/notifyUrl";

	// ######################以下为支付宝配置
	// 字符编码格式 目前支持 gbk 或 utf-8
	public String charset = "utf-8";

	// public String alipayGateway = "https://openapi.alipay.com/gateway.do";
	public String alipayGateway = "http://openapi.alipaydev.com/gateway.do";

	public String alipayAppId = "2018010401578779";

	public String alipaySellerId = "2088921410304192";

	// 商户的私钥
	public String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCxlZqn+3mc8myS\r\n"
			+ "0saQ9PGrFlWcGRYLUjwNPQyzhq+fkP9Y80/4eoVsDlYumQEf+QWpHi8ji3sk8Cuw\r\n"
			+ "IJ9Qcdn3ig2FIlb4Vn9SJRn5jvleI3aC5HjlqzpzkJaGUJun4g7ANNWaMcBx1foK\r\n"
			+ "kGI3VfOF3bGsbEeFQF80ZG8MYhxR9qmZ7rsP8Q6fsBITaluFYC7Rv1zMdUzovBg7\r\n"
			+ "B3GUQNJiiWaCq8QP/IyRJUkn0aFslkttwN5cWYNDBpR8Bv4wR9P4FFqcoagR1AWk\r\n"
			+ "w04ZlqENx5VslQJzoWs3y3ySy4GTH2vgKr5T4fPsFWYetEtvSIau0/JU4WFrciRm\r\n"
			+ "6DBwvQkDAgMBAAECggEAU6XagYHM2Qa/6bDq42DdbO9KSArw/UgxDTNShkXM9+Z+\r\n"
			+ "wNFrO+nKawmdwrlDdGICLpUPMzkCOnX+vim/oU7fkuAuWiXAvZ1Qv8rGH20fPR6L\r\n"
			+ "NkCUSeqLz9+tZ3sUttGejR7V9pm0wmo+Kzeza8lKuiWhCUy/q56UucMNI9Ia8Ofr\r\n"
			+ "s/IzNul4B0xxIw0YALXiX8LG2WASuC+PTY596gEV4jtm688vOsG9TO4AwMtoxB8B\r\n"
			+ "rjD/Izllnsatp4ikK2SJd7QurFOxk4BuPd9ZYKVWfl2KC0gVnVhqnz9pO9OWdBGX\r\n"
			+ "UlCneT8M6UT3UvcKHBPghVIVZOoMOYebT5p3oBPR8QKBgQDlpFGwuaiJyz4iyFkZ\r\n"
			+ "KtB3Jg7wQAeo3kcdJZqkjX805KIUFP/Q9OdXjnM+6CSlqJCNIVUxvAIHuwTXIpar\r\n"
			+ "YCx5iWGO6C610jvbk0SmXw6YvC2Qyai81+w7Ks5r2nDBVo9p45OdCdN92iMpUOyT\r\n"
			+ "qiPr6IaVW6h5Y4tA/L3H+eNDrQKBgQDF96d/Ds9WCQjLY7H7d9pHFwnqUQb9wCrY\r\n"
			+ "13It1Tkh7yPVZT7KZ68fKWEJxxixzCa74jeEli/V9HmEkyeMSDKqY9ZzqfJ9sN/i\r\n"
			+ "CMHqled9f8JiXzPZKMCMlcU3ou+BbfAPfPr2BJ/d2BRuqTSpCGGmyg/oRLVW3CI5\r\n"
			+ "lXkDleuVbwKBgFwcF8QqA+cZRZgQAmTs3mccNdu54xih56ZSQgAE3WQq3LPvZFi9\r\n"
			+ "Etn0wlcWKDTmMuzY1OI5BhtxsVRVhFfDILOzHhWDHdFCPU4qeNu+vw0WVbSAnpT4\r\n"
			+ "isGEvv1VxepWvPl9FeVkXpdZWscktW/ZS8VBJFJeTGo8Okvb1IeUvMqVAoGBAIWc\r\n"
			+ "iGewzy76Gf1r5Awc8C1a105WManBsSl4/47/gfftNvonwyFIqWEWxG+c+IXHi7zz\r\n"
			+ "CRVraA23WuynhOGhONDXYT3fTdKXNgOQdZCma/6/46oRJyVB4/L+goTya0Ont4Y8\r\n"
			+ "jATPSjaoR+IIQYp0JbQ0XFgfR2TAXjkPMQXCgaXxAoGAOV5NFHx+B0dj3OLKJlIb\r\n"
			+ "POs0pmP2nosODENq8euNtOBd0ozWX/imq45+wasr9MnYeRdVUGMQT0Xa9dTzFmSg\r\n"
			+ "38u3oq4sLgvjSV9vR7rejFM90YFCrNuIAXJU05l4q9JbqAQsCTbw48qx7VhFj4bK\r\n" + "o2lbHtefiZ2FhQrX0nICIVs=";

	// 支付宝的公钥，无需修改该值
	public String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwDLvyUPAuRjQK0Nrc4iiLwO8f0VhekTcQ6jx7gKMAsgwoqGA6wU3/8HAUpPJe0uxfcB5SjMugXqzdC50h4H8QVERydXkkG51Pi7kO8RR66HMuFRJdXkj5L5OJkWzqjcma5Co2vPMq+s6/aIc/auNOgqH//Ujmvw5nhx9wCVg0/L2f4DXBxHMDLRYwpQiN5WQXJ/pWeC9C+UjWGniS92zKWuxNjQHrXLnqy9wJCUkId31tm8QCoO/jQzDSatEL0rN2Ks9TDCXhpHq0fH24ZQoWRXeDqLS0273JV28q5ySPc1Wkah8THLPbxCQoKTuGvCUh0YlJGx2Uh4DuetXqqXOgwIDAQAB";
	// 签名方式 不需修改
	public String signType = "RSA2";

	// 支付成功标识
	public String AliPaySuccessStatue = "success";
	// 失败
	public String AliPayFailureStatue = "failure";

	// ######################以下为微信配置
	// 微信回调路径
	public String wechatNotifyUrl = "http://www.co-mama.cn/bgms/wxpay/notifyUrl";
	// 微信支付AppId
	public String wechatAppId = "wx1751ba892d97acaf";
	// 微信支付AppSecret
	public String wechatAppSecret = "63c897b7738faa8da41e15751f9d8b25";
	// 商户号
	public String wechatMchId = "1496466012";
	// 商户密钥
	public String wechatPrivateKey = "2349hejrtgjse0943trr043twejgfjs0";
	// 微信验证路径
	public String wechatVerifyPath = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 支付成功标识
	public String wechatPaySuccessStatue = "success";
	// 失败
	public String wechatPayFailureStatue = "failure";

}
