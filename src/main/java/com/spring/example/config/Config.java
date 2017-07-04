package com.spring.example.config;

public class Config {
	public final static String IMG_BasePath = "E:\\work folder\\bsImgResource";
	//** use from wechat**//
	public final static String APP_ID = "wxab2fb32c8ddd48b8";
	public final static String APP_SECRET = "7934f1d256e72213061cf601b71f1dd1";
	public final static String MCH_ID = "1481779342";
	public final static String PAY_BODY = "测试";
	public final static String Notify_URL = "http://localhost:8080/BSManageAdmin/api/wx/payment/notify";
	
	public enum TRADE_TYPE{
		JSAPI
	}
	
	public enum SIGN_TYPE{
		MD5
	}

	private Config(){
		throw new InstantiationError( "Must not instantiate this class" );
	}
	
	public static String getOpenIdUrl(String code){
		return "https://api.weixin.qq.com/sns/jscode2session?appid="+APP_ID+"&secret="+APP_SECRET+"&js_code="+code+"&grant_type=authorization_code";
	}
	
	public static String getUnifiedOrderUrl(){
		return "https://api.mch.weixin.qq.com/pay/unifiedorder";
	}
}
