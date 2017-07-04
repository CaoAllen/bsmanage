package com.spring.example.api.wx.payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.example.Constants;
import com.spring.example.config.Config;
import com.spring.example.config.Config.SIGN_TYPE;
import com.spring.example.config.Config.TRADE_TYPE;
import com.spring.example.domain.Order;
import com.spring.example.model.PayArgs;
import com.spring.example.model.PayReturnInfo;
import com.spring.example.model.SignInfo;
import com.spring.example.utils.WXPayUtil;
import com.thoughtworks.xstream.XStream;

@RestController
@RequestMapping(value = Constants.URI_WX_API)
public class WXPaymentController {
	
    private static final Logger log = LoggerFactory.getLogger(WXPaymentController.class);
	
	@RequestMapping(value = Constants.URI_PAYMENT_GETOPENID, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> getOpenId(@RequestParam(value = "code", required = true) String code){
		log.debug("get openid start...");
		try {
			HttpGet httpGet = new HttpGet(Config.getOpenIdUrl(code));
			HttpClient httpClient = HttpClients.createDefault();
			HttpResponse res = httpClient.execute(httpGet);
			HttpEntity entity = res.getEntity();
			String result = EntityUtils.toString(entity, "UTF-8");
			if(log.isDebugEnabled()){
				log.debug(result);
			}
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			if(log.isErrorEnabled()){
				log.error("get openid failed:"+e);
			}
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		} catch (ParseException e) {
			e.printStackTrace();
			if(log.isErrorEnabled()){
				log.error("get openid failed :"+e);
			}
			return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@RequestMapping(value = Constants.URI_PAYMENT_REQUESTPAY, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<SignInfo> requestPayment(@RequestParam(value = "openId", required = true) String openId,
			@RequestParam(value = "totalFee", required = true) String totalFee){
		//TODO
		//orderservice.addOrder
		log.debug("payment start...");
		PayArgs payArgs = new PayArgs();
		payArgs.setAppid(Config.APP_ID);
		payArgs.setMch_id(Config.MCH_ID);
		payArgs.setNonce_str(WXPayUtil.getRandomStr(32));
		payArgs.setSign_type(SIGN_TYPE.MD5.toString());
		payArgs.setBody(Config.PAY_BODY);
//		payArgs.setDetail("");
		payArgs.setOut_trade_no("20150806125346");
		payArgs.setTotal_fee(Integer.valueOf(totalFee));
		payArgs.setSpbill_create_ip("123.12.12.123");
		payArgs.setNotify_url(Config.Notify_URL);
		payArgs.setTrade_type(TRADE_TYPE.JSAPI.toString());
		payArgs.setOpenid(openId);

		if(log.isDebugEnabled()){
			log.debug(payArgs.toString());
		}
//		HttpResponse response = null;
//		try {
//	        HttpPost post = new HttpPost(Config.getUnifiedOrderUrl());  
//	        HttpClient httpClient = HttpClients.createDefault();
//	        List<BasicNameValuePair> parameters = new ArrayList<>();  
//	        parameters.add(new BasicNameValuePair("xml", WXPayUtil.getPayRequestXml(WXPayUtil.transBean2Map(payArgs))));  
//        	post.setEntity(new UrlEncodedFormEntity(parameters,"UTF-8"));  
//        	response = httpClient.execute(post);
//		} catch (Exception e) {
//			e.printStackTrace();
//			if(log.isErrorEnabled()){
//				log.error("pay failed :"+e);
//			}
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//        
//		HttpEntity entity = response.getEntity();
//		if(log.isDebugEnabled()){
//			log.debug("wx reuqet return :"+entity);
//		}
        Map<String, String> resultMap = new HashMap<>();
		try {
			resultMap = WXPayUtil.covertStrToMap(WXPayUtil.convertStream(WXPayUtil.httpRequest(Config.getUnifiedOrderUrl(), "POST", WXPayUtil.getPayRequestXml(WXPayUtil.transBean2Map(payArgs)))));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(!resultMap.isEmpty() && "SUCCESS".equalsIgnoreCase(resultMap.get("return_code"))){
			//TODO
			//orderserivce.updateorder
			SignInfo signInfo = new SignInfo();
			signInfo.setAppId(Config.APP_ID);
			signInfo.setNonceStr(resultMap.get("nonce_str"));
			signInfo.setPrepay("prepay_id="+resultMap.get("prepay_id"));
			signInfo.setSignType(SIGN_TYPE.MD5.toString());
			signInfo.setTimeStamp(String.valueOf((System.currentTimeMillis() / 1000)));
			String sign = WXPayUtil.getSign(WXPayUtil.transBean2Map(signInfo));
			signInfo.setPaySign(sign);
			
			return new ResponseEntity<>(signInfo, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
	}
}
