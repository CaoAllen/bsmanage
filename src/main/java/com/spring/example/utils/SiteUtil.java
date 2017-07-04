package com.spring.example.utils;

import com.spring.example.model.SiteForm;

public class SiteUtil {
    private SiteUtil() {
        throw new InstantiationError( "Must not instantiate this class" );
    }
    
    public static boolean validateSiteForm(SiteForm sf){
    	if(sf.getAddress() == null){
    		return false;
    	}
    	return true;
    }
}
