package com.spring.example.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncryptUtil {
	private static final PasswordEncoder encoder = new BCryptPasswordEncoder(10);

	public static String encrypt(String rawPassword) {
		return encoder.encode(rawPassword);
	}

	public static boolean match(String rawPassword, String password) {
		return encoder.matches(rawPassword, password);
	}
	
	public static void main(String[] args) {
		System.out.println(match("test123", "$2a$10$QXPhCszGOL5x3gcXLKAuz..do1kU2rXX.tCPw2yABnizi3EOjzZEK"));
	}
}
