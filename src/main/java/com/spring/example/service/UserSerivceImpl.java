package com.spring.example.service;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.example.domain.User;
import com.spring.example.repository.UserRepository;

@Service("userService")
@Transactional
public class UserSerivceImpl implements UserSerivce {

	private UserRepository userRepository;
	
	@Inject
	public UserSerivceImpl(UserRepository userRepository){
		this.userRepository = userRepository;
	}

	public User findById(int id) {
		return userRepository.findById(id);
	}

	public User findByName(String name) {
		return userRepository.findByName(name);
	}
}
