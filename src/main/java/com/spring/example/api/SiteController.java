package com.spring.example.api;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.example.Constants;
import com.spring.example.domain.Community;
import com.spring.example.domain.Picture;
import com.spring.example.domain.Price;
import com.spring.example.domain.Result;
import com.spring.example.domain.Site;
import com.spring.example.exception.InvalidRequestException;
import com.spring.example.model.ResponseMessage;
import com.spring.example.model.SiteDetails;
import com.spring.example.model.SiteForm;
import com.spring.example.model.SiteItem;
import com.spring.example.model.WXSiteItem;
import com.spring.example.service.SiteServiceImpl;
import com.spring.example.utils.SiteUtil;

@RestController
@RequestMapping(value = Constants.URI_API + "/site")
public class SiteController {
    private static final Logger log = LoggerFactory.getLogger(SiteController.class);
    
	private SiteServiceImpl siteService;
	
	@Inject
	public SiteController(SiteServiceImpl siteService) {
		this.siteService = siteService;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<SiteItem>> getSites(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "pageNo", required = true) int pageNo,
			@RequestParam(value = "pageSize", required = true) int pageSize,
			@RequestParam(value = "sortName", required = true) String sortName,
			@RequestParam(value = "sortDirection") String sortDirection){
		if(!"DESC".equalsIgnoreCase(sortDirection)){
			sortDirection = "ASC";
		}
		PageRequest pageRequest = new PageRequest(pageNo, pageSize, Direction.fromString(sortDirection), sortName);
		try {
			List<SiteItem> sites = siteService.queryAllSites(name,pageRequest);
			return new ResponseEntity<>(sites, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Site> createSite(@RequestBody @Valid SiteForm sf, BindingResult errResult){
        log.debug("create a new site");
        if (errResult.hasErrors()) {
            throw new InvalidRequestException(errResult);
        }
        if(!SiteUtil.validateSiteForm(sf)){
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println(sf.toString());
		try {
			Site site = siteService.saveSite(sf);
	        log.debug("new site id: "+site.getSiteId());
	        if(site != null && site.getSiteId() > 0){
	        	return new ResponseEntity<>(site, HttpStatus.CREATED);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("save site exception happen: " + e);
		}
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<SiteDetails> getSite(@RequestParam("siteId") Long siteId){
    	if(!(siteId > 0)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
		try {
			SiteDetails details = siteService.getSite(siteId);
	        if(details != null){
	        	return new ResponseEntity<>(details, HttpStatus.OK);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("get site exception happen: " + e);
		}
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Long> updateSite(@RequestBody @Valid SiteForm sf, BindingResult errResult){
        log.debug("update site");
        if (errResult.hasErrors()) {
            throw new InvalidRequestException(errResult);
        }
        log.debug(sf.toString());
        if(!(sf.getId() > 0)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        boolean updated;
		try {
			updated = siteService.updateSite(sf);
	        if(updated){
	        	return new ResponseEntity<>(HttpStatus.OK);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Result> finishSite(@RequestParam("siteId") Long siteId){
	    if(!(siteId > 0)){
	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
        boolean updated;
		try {
			updated = siteService.finishSite(siteId);
	        if(updated){
	        	return new ResponseEntity<>(new Result(true), HttpStatus.OK);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @RequestMapping(value = "/community/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Result> addCommunity(@RequestBody @Valid Community community, BindingResult errResult){
	    if(!(community.getSiteId() > 0)){
	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
        log.debug("add community for site, siteId:" + community.getSiteId());
        if (errResult.hasErrors()) {
            throw new InvalidRequestException(errResult);
        }
        log.debug(community.toString());
		try {
			Community comm = siteService.saveCommunity(community);
	        log.debug("new id: "+comm.getCommunityId());
	        if(comm != null && comm.getCommunityId() > 0){
	        	return new ResponseEntity<>(new Result(true), HttpStatus.CREATED);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("save community exception happen: " + e);
		}
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @RequestMapping(value = "/price/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Result> addPrice(@RequestParam("prices") String prices, @RequestParam("siteId") Long siteId){
	    if(!(siteId > 0)){
	    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
        log.debug("add price for site, siteId:" + siteId);
        log.debug(prices.toString());
        ObjectMapper mapper = new ObjectMapper();
		try {
			JSONArray jsonArray = new JSONArray(prices);
			List<Price> priceList = new ArrayList<>();
			for (int i =0; i < jsonArray.length(); i++) {
				Price price = mapper.readValue(jsonArray.getJSONObject(i).toString(), Price.class);
				priceList.add(price);
			}
			List<Price> result = siteService.savePrices(priceList,siteId);
	        if(result != null && result.size() > 0){
	        	return new ResponseEntity<>(new Result(true), HttpStatus.CREATED);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("save price exception happen: " + e);
		}
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @RequestMapping(value = "/picture/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Result> uploadPicture(@RequestParam("file") MultipartFile uploadFile, @RequestParam("siteId") Long siteId){

		try {
			Picture p = siteService.uploadPicture(uploadFile, siteId);
	        log.debug("new id: "+p.getPictureId());
	        if(p != null && p.getPictureId() > 0){
	        	return new ResponseEntity<>(new Result(p,true), HttpStatus.CREATED);
	        }
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("upload picture exception happen: " + e);
		}
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @RequestMapping(value = "/picture/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Result> deletePicture(@RequestParam("pictureId") Long pictureId){
		try {
			siteService.deletePicutre(pictureId);
        	return new ResponseEntity<>(new Result(true), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug("delete picture exception happen: " + e);
		}
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
