package com.spring.example.repository.example;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class MyTableRepositoryImpl implements MyTableRepositoryCustom {
	@PersistenceContext 
	private EntityManager em;

	@Override
	public List inOnlyTest(String inParam1) {
	    return this.em.createNativeQuery("select 1 as id").getResultList();
	}
}
