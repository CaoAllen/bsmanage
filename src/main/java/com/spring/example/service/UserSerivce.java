package com.spring.example.service;

import com.spring.example.domain.User;

public interface UserSerivce {
	User findById(int id);

	User findByName(String sso);
}
