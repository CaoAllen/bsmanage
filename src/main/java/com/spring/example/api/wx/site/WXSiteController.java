package com.spring.example.api.wx.site;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.example.Constants;
import com.spring.example.model.Page;
import com.spring.example.model.Page.Sort;
import com.spring.example.model.SiteDetails;
import com.spring.example.model.WXSiteDetails;
import com.spring.example.model.WXSiteItem;
import com.spring.example.service.SiteServiceImpl;

@RestController
@RequestMapping(value = Constants.URI_WX_API)
public class WXSiteController {
	private static final Logger log = LoggerFactory.getLogger(WXSiteController.class);
	
	private SiteServiceImpl siteService;
	
	@Inject
	public WXSiteController(SiteServiceImpl siteService) {
		this.siteService = siteService;
	}

	@RequestMapping(value = "/getSites", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<WXSiteItem>> getSites(
			@RequestParam(value = "city", required = true) String city,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "priceLow") Long priceLow,
			@RequestParam(value = "priceHigh") Long priceHigh,
			@RequestParam(value = "siteType") String siteType,
			@RequestParam(value = "pageNo", required = true) int pageNo,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "sortName", required = true) String sortName,
			@RequestParam(value = "sortDirection") String sortDirection){
		BigDecimal low = new BigDecimal(priceLow);
		BigDecimal high = new BigDecimal(priceHigh);
		if(!Sort.DESC.toString().equalsIgnoreCase(sortDirection)){
			sortDirection = Sort.ASC.toString();
		}
		Page page = new Page(sortName, sortDirection, pageNo, pageSize);
		try {
			List<WXSiteItem> items = siteService.searchSites(city, name, low, high, siteType, page);
			return new ResponseEntity<>(items, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
    @RequestMapping(value = "/site/get", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<WXSiteDetails> getSite(@RequestParam("siteId") Long siteId){
    	if(siteId == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		try {
			WXSiteDetails details = siteService.getWXSiteDetails(siteId);
	        if(details != null){
	        	return new ResponseEntity<>(details, HttpStatus.OK);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("get site exception happen: " + e);
		}
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
