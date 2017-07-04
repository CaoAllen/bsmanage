package com.spring.example;

public final class Constants {

    /**
     * prefix of REST API
     */
    public static final String URI_API = "/api";

    public static final String URI_POSTS = "/posts";

    public static final String URI_COMMENTS = "/comments";
    
    /** 微信 rest uri**/

    public static final String URI_WX_API = "/api/wx";
    
    public static final String URI_PAYMENT_GETOPENID = "/payment/getopenId";

    public static final String URI_PAYMENT_REQUESTPAY = "/payment/requestpay";
    
    private Constants() {
        throw new InstantiationError( "Must not instantiate this class" );
    }
    
}
