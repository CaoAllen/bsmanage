package com.spring.example.repository.example;

import org.springframework.data.repository.CrudRepository;

import com.spring.example.domain.SiteDetails;

public interface MyTableRepository extends CrudRepository<SiteDetails, Long>, MyTableRepositoryCustom{

}
