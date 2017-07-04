package com.spring.example.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.example.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{
	public Address findBySiteId(Long id);
}
