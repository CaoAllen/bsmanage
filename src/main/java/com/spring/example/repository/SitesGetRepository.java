package com.spring.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.spring.example.domain.SiteDetails; 

public interface SitesGetRepository extends CrudRepository<SiteDetails, Long>{
	
    @Procedure(name = "list")
	public List<SiteDetails> searchSitesByCriteria(
			@Param("name") String name,
			@Param("price_low") BigDecimal priceLow, 
			@Param("price_high") BigDecimal priceHigh, 
			@Param("site_type") String siteType);

}
