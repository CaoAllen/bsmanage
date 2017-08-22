package com.spring.example.api.wx.payment;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
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
import com.spring.example.domain.Result;
import com.spring.example.model.PayArgs;
import com.spring.example.model.SignInfo;
import com.spring.example.service.OrderServiceImpl;
import com.spring.example.utils.OrderNoUtil;
import com.spring.example.utils.OrderStatus;
import com.spring.example.utils.WXPayUtil;


@RestController
@RequestMapping(value = Constants.URI_WX_API)
public class WXPaymentController {
	
    private static final Logger log = LoggerFactory.getLogger(WXPaymentController.class);
    
    private OrderServiceImpl orderService;
    
    @Inject
	public WXPaymentController(OrderServiceImpl orderService) {
		this.orderService = orderService;
	}

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
			@RequestParam(value = "totalFee", required = true) String totalFee,
			@RequestParam(value = "orders", required = true) String orders,
			@RequestParam(value = "orderInfo", required = true) String orderInfo){
		//TODO
		//orderservice.addOrder
		log.debug("payment start...");
		PayArgs payArgs = new PayArgs();
		payArgs.setAppid(Config.APP_ID);
		payArgs.setMch_id(Config.MCH_ID);
		payArgs.setNonce_str(WXPayUtil.getRandomStr(32));
		payArgs.setSign_type(SIGN_TYPE.MD5.toString());
		payArgs.setBody(Config.PAY_BODY);
		payArgs.setDetail("orders");
		String orderNo = OrderNoUtil.createNo();
		payArgs.setOut_trade_no(orderNo);
		payArgs.setTotal_fee(Integer.valueOf(totalFee));
		try {
			payArgs.setSpbill_create_ip(InetAddress.getLocalHost().getHostAddress().toString());
		} catch (UnknownHostException e1) {
			if(log.isErrorEnabled()){
				log.error("unexpect excetion happen:" + e1.getMessage());
			}
		}
		payArgs.setNotify_url(Config.Notify_URL);
		payArgs.setTrade_type(TRADE_TYPE.JSAPI.toString());
		payArgs.setOpenid(openId);

		if(log.isDebugEnabled()){
			log.debug(payArgs.toString());
		}
        Map<String, String> resultMap = new HashMap<>();
		try {
			resultMap = WXPayUtil.covertStrToMap(WXPayUtil.convertStream(WXPayUtil.httpRequest(Config.getUnifiedOrderUrl(), "POST", WXPayUtil.getPayRequestXml(WXPayUtil.transBean2Map(payArgs)))));
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error("unexpect excetion happen:" + e.getMessage());
			}
		}
        if(!resultMap.isEmpty() && "SUCCESS".equalsIgnoreCase(resultMap.get("return_code"))){
			SignInfo signInfo = new SignInfo();
			signInfo.setAppId(Config.APP_ID);
			signInfo.setNonceStr(resultMap.get("nonce_str"));
			signInfo.setPrepay("prepay_id="+resultMap.get("prepay_id"));
			signInfo.setSignType(SIGN_TYPE.MD5.toString());
			signInfo.setTimeStamp(String.valueOf((System.currentTimeMillis() / 1000)));
			signInfo.setOrderNo(orderNo);
			String sign = WXPayUtil.getSign(WXPayUtil.transBean2Map(signInfo));
			signInfo.setPaySign(sign);
			
			try {
				orderService.addOrder(orderNo,Integer.valueOf(totalFee),orders,orderInfo,signInfo);
			} catch (NumberFormatException | SQLException | IOException e) {
				e.printStackTrace();
				if(log.isErrorEnabled()){
					log.error("unexpect excetion happen:" + e.getMessage());
				}
			}
			
			return new ResponseEntity<>(signInfo, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
	}
	
	@RequestMapping(value = Constants.URI_PAYMENT_FINISH, method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Result> finishPayment(@RequestParam(value = "orderNo", required = true) String orderNo){
		try {
			orderService.updatePaymentOrder(orderNo, OrderStatus.PaymentComplete);
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error("unexpect json excetion happen:" + e.getMessage());
			}
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(new Result(true), HttpStatus.OK);
	}
}
