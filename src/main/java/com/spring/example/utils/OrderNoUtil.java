package com.spring.example.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OrderNoUtil {
    private static final Logger log = LoggerFactory.getLogger(OrderNoUtil.class);
    private static Object lockObj = "lockerOrder";  
    private static long orderNumCount = 0L;  
    private static int maxPerMSECSize = 500;
    
    private OrderNoUtil() {
        throw new InstantiationError( "Must not instantiate this class" );
    }
    
    public static String createNo() {  
        synchronized (lockObj) {  
            long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));  
            if (orderNumCount >= maxPerMSECSize) {  
                orderNumCount = 0L;  
            }  
            String countStr = maxPerMSECSize + orderNumCount + "";  
            orderNumCount++;  
            if(log.isDebugEnabled()){
            	log.debug(nowLong + countStr.substring(1) + "--" + Thread.currentThread().getName());
            }
            return nowLong + countStr.substring(1); 
        }  
    }  
}
