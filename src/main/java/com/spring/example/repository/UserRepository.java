package com.spring.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.example.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
    User findById(int id);  
    
    User findByName(String name); 
}
