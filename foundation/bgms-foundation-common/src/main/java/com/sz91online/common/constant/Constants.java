package com.sz91online.common.constant;

/**
 * Created by wendajun on 2016/2/25.
 */
public interface Constants {

    String MSG_TYPE_IMAGE = "image";
    
    String MSG_TYPE_AUDIO = "audio";
    
    String MSG_TYPE_MSG = "msg";
    
    String Repeat="repeat";
    
    Long Captcha = 30*60l;

    String SessionUser = "BaseUser";

    String BgmsClient = "Bgmsclient";

    String Token = "Token";

    String AlipayPartner = "2088221558491430";

//    String AlipayNotifyUrl = "http://notify.msp.hk/notify.htm";
    String AlipayNotifyUrl = "%s/patientPackage/notifyUrl";
    //商家唯一id
    String AlipaySellerId="sz91online_mh_ibgs@163.com";

    //支付成功标识
    String PaySuccessStatue="success";
    //失败
    String PayFailureStatue="failure";
    //初始化
    String PayInitStatue="init";
    //微信支付AppId
    String WechatAppId = "wx0591f27a4ad89186";
    //微信支付AppSecret
    String WechatAppSecret = "8256ec9078285d0cdc345350babdef47";
    //商户号
    String WechatMchId="1333917301";
    //商户密钥
    String WechatPrivateKey="TRVYFEXVEPBBMWCLWGRXHFTOVKTVMYWE";
    
    String TEST_SCHEDULE_TYPE_TEMPLATE = "template";
    
    String TEST_SCHEDULE_TYPE_CUSTOM = "custom";
    
    String CUSTOM_TEST_SCHEDULE = "自定义";

    Long WechatValidTime=30*60*1000l;

    Long ServerValidTime=10*24*60*60*1000l;
    
    int [] date={3,7,14,30};
}
