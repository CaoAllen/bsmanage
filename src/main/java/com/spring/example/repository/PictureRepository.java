package com.spring.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.example.domain.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long>{
	public List<Picture> findBySiteId(Long id);
}
