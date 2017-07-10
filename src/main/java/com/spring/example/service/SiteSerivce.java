package com.spring.example.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.spring.example.domain.Community;
import com.spring.example.domain.Picture;
import com.spring.example.domain.Price;
import com.spring.example.domain.Site;
import com.spring.example.model.SiteDetails;
import com.spring.example.model.SiteForm;
import com.spring.example.model.SiteItem;
import com.spring.example.model.WXSiteItem;

public interface SiteSerivce {

	public List<WXSiteItem> searchSites(String city, String name, BigDecimal priceLow, BigDecimal priceHigh, String siteType, Pageable page);
	
	public Site saveSite(SiteForm fm) throws Exception;
	
	public SiteDetails getSite(Long siteId) throws Exception;
	
	public boolean updateSite(SiteForm sf) throws Exception;
	
	public List<SiteItem> queryAllSites(String name, Pageable pageable) throws SQLException;
	
	public Community saveCommunity(Community community) throws SQLException;
	
	public Price savePrice(Price price) throws Exception;
	
	public List<Price> savePrices(List<Price> prices, Long siteId) throws Exception;
	
	public void updatePrices(List<Price> prices, Long siteId) throws Exception;
	
	public Picture uploadPicture(MultipartFile uploadFile, Long siteId) throws Exception;
	
	public void deletePicutre(Long pictureId) throws Exception;
	
	public void updateCommunity(Community community) throws Exception;
}
