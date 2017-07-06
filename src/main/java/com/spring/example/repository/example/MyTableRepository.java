package com.spring.example.repository.example;

import org.springframework.data.repository.CrudRepository;

import com.spring.example.model.SiteDetails;

public interface MyTableRepository extends CrudRepository<MyTable, Long>, MyTableRepositoryCustom{

}
