package com.spring.example.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

import com.spring.example.config.Config;

public class WXPayUtil {

	private static final Logger log = LoggerFactory.getLogger(WXPayUtil.class);

	private WXPayUtil() {
		throw new InstantiationError("Must not instantiate this class");
	}

	public static String getRandomStr(int figures) {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < figures; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	// Bean --> Map 1: user Introspector and PropertyDescriptor 将Bean --> Map
	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> reqMap = new TreeMap<String, Object>(new Comparator<String>() {
			public int compare(String obj1, String obj2) {
				return obj1.compareTo(obj2);
			}
		});
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (!key.equals("class")) {
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					reqMap.put(key, value);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled()) {
				log.error("transBean2Map Error " + e);
			}
		}

		return reqMap;

	}

	public static String getPayRequestXml(Map<String, Object> map) {
		StringBuffer key_value = new StringBuffer();
		StringBuilder requestXml = new StringBuilder();
		requestXml.append("<xml>");
		for (String key : map.keySet()) {
			if("sign".equals(key) || map.get(key) == null){
				continue;
			}
			key_value.append(key);
			key_value.append('=');
			key_value.append(map.get(key));
			key_value.append('&');

			requestXml.append("<" + key + ">");
			// requestXml.append("<![CDATA[" + map.get(key) + "]]>");
			requestXml.append(map.get(key));
			requestXml.append("</" + key + ">");
		}
		key_value.append("key=" + Config.APP_SECRET);

		String sign = MD5.getInstance().getMD5(key_value.toString()).toUpperCase();
		requestXml.append("<sign>");
		requestXml.append(sign);
		requestXml.append("</sign>");
		requestXml.append("</xml>");
		if (log.isDebugEnabled()) {
			log.debug("key_value=" + key_value);
			log.debug("md5 sign=" + sign);
			log.debug("requestXml=" + requestXml);
		}
		return requestXml.toString();
	}

	public static String getSign(Map<String, Object> map) {
		StringBuffer sign = new StringBuffer();
		for (String key : map.keySet()) {
			sign.append(key);
			sign.append('=');
			sign.append(map.get(key));
			sign.append('&');
		}
		sign.append("key=" + Config.APP_SECRET);

		if (log.isDebugEnabled()) {
			log.debug("second sign=" + sign);
		}
		return MD5.getInstance().getMD5(sign.toString()).toUpperCase();
	}

	public static String getValueFromXml(String resultXml, String tagName) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = dbf.newDocumentBuilder();
			InputStream inputStream = new ByteArrayInputStream(resultXml.getBytes());
			return builder.parse(inputStream).getDocumentElement().getElementsByTagName(tagName).item(0).getFirstChild()
					.getNodeValue();
		} catch (Exception e) {
			e.printStackTrace();
			if (log.isErrorEnabled()) {
				log.error("parse xml error:" + e);
			}
			return null;
		}
	}
	
	public static String convertStream(InputStream is){
		StringBuffer buffer = new StringBuffer();   
		try{  
	        InputStreamReader isr = new InputStreamReader(is, "utf-8");   
	        BufferedReader br = new BufferedReader(isr);   
	        String line = null;   
	        while ((line = br.readLine()) != null) {   
                  buffer.append(line);   
	        }   
        }catch(Exception e){   
            e.printStackTrace();   
        } 
		return buffer.toString();
	}
	
	public static Map<String, String> covertStrToMap(String result) throws DocumentException{
		Map<String, String> map = new HashMap<>();   
	         InputStream in=new ByteArrayInputStream(result.getBytes());    
	        SAXReader reader = new SAXReader(); 
	        reader.setEncoding("GBK");
	        Document document = reader.read(in);   
	        Element root = document.getRootElement();   
	        List<Element> elementList = root.elements();   
	        for (Element element : elementList) {   
	            map.put(element.getName(), element.getText());   
	        } 
		return map;
	}
	
    public static InputStream httpRequest(String requestUrl,String requestMethod,String outputStr){ 
    	OutputStream os = null;
    	HttpURLConnection conn = null;
    	InputStream is = null;
        try{   
	        URL url = new URL(requestUrl);   
	        conn = (HttpURLConnection) url.openConnection();   
	        conn.setRequestMethod(requestMethod);   
	        conn.setDoOutput(true);   
	        conn.setDoInput(true);   
	        conn.connect();   
	        if(null !=outputStr){   
	            os = conn.getOutputStream();   
	            os.write(outputStr.getBytes("utf-8")); 
				os.close();
	        }
	        is = conn.getInputStream();
        } catch(Exception e){   
            e.printStackTrace();   
        }
        return is;
    }
}
