package com.spring.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.example.domain.Community;

public interface CommunityRepository extends JpaRepository<Community, Long>{
	public Community findBySiteId(Long id);
}
