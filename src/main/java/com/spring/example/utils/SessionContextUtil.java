package com.spring.example.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SessionContextUtil {

	private SessionContextUtil() {
		throw new InstantiationError("Must not instantiate this class");
	}
	
	public static UserDetails getUserDetails(){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails;
	}
	
	public static String getUserName(){
		UserDetails userDetails = getUserDetails();
		if(userDetails != null){
			return userDetails.getUsername();
		}
		return "";
	}
}
