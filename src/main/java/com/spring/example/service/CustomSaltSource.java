package com.spring.example.service;

import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomSaltSource implements SaltSource{

	@Override
	public Object getSalt(UserDetails arg0) {
		return "_system";
	}

}
