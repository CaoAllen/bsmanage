package com.spring.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.spring.example.model.Page;
import com.spring.example.model.SiteItem;
import com.spring.example.model.WXSiteItem;

public interface SiteRepositoryCustom {
	
	List<WXSiteItem> searchSites(String city, String name, BigDecimal priceLow, BigDecimal priceHigh, String siteType, Page page);

	List<SiteItem> queryAllSites(String name, String district);
}
