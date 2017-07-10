package com.spring.example.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import javax.inject.Inject;

import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.spring.example.config.Config;
import com.spring.example.domain.Address;
import com.spring.example.domain.Community;
import com.spring.example.domain.Picture;
import com.spring.example.domain.Price;
import com.spring.example.domain.Site;
import com.spring.example.exception.ResourceNotFoundException;
import com.spring.example.model.SiteDetails;
import com.spring.example.model.SiteForm;
import com.spring.example.model.SiteItem;
import com.spring.example.model.WXSiteItem;
import com.spring.example.repository.AddressRepository;
import com.spring.example.repository.CommunityRepository;
import com.spring.example.repository.PictureRepository;
import com.spring.example.repository.PriceRepository;
import com.spring.example.repository.SiteRepository;
import com.spring.example.utils.SessionContextUtil;
import com.spring.example.utils.DTOUtils;
import com.spring.example.utils.SiteStatus;


/**
 * 
 * @author acao
 *
 */
@Service
@Transactional
public class SiteServiceImpl implements SiteSerivce{
	
    private static final Logger log = LoggerFactory.getLogger(SiteServiceImpl.class);

    private SiteRepository siteRepository;
    private AddressRepository addressRepository;
    private CommunityRepository communityRepository;
    private PriceRepository priceRepository;
    private PictureRepository pictureRepository;

    @Inject
    public SiteServiceImpl(SiteRepository siteRepository, AddressRepository addressRepository,
			CommunityRepository communityRepository, PriceRepository priceRepository,
			PictureRepository pictureRepository) {
		super();
		this.siteRepository = siteRepository;
		this.addressRepository = addressRepository;
		this.communityRepository = communityRepository;
		this.priceRepository = priceRepository;
		this.pictureRepository = pictureRepository;
	}

	/**
     * User for wexin interface
     */
    public List<WXSiteItem> searchSites(String city, String name, BigDecimal priceLow, BigDecimal priceHigh, String siteType, Pageable page) {
    	if(log.isDebugEnabled()){
    		log.debug("city:"+city);
    		try {
				log.debug(new String(city.getBytes("ISO-8859-1"), "UTF-8").toString());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		log.debug("name:"+name);
    		log.debug("priceLow:"+priceLow);
    		log.debug("priceHigh:"+priceHigh);
    		log.debug("siteType:"+siteType);
    		log.debug("pageNum :"+page.getPageNumber());
    		log.debug("pageSize :"+page.getPageSize());
    		log.debug("sort :"+page.getSort());
    	}
    	return siteRepository.searchSites(city, name, priceLow, priceHigh, siteType, page);
    }
    
    public List<SiteItem> queryAllSites(String name, Pageable pageable) throws SQLException{
    	List<SiteItem> sites = siteRepository.queryAllSites(name, pageable);
    	return sites;
    }

	public Site saveSite(SiteForm sf) throws Exception{
		Site site = populateSite(new Site(), sf);;
		if(log.isDebugEnabled()){
			log.debug(site.toString());
		}
		Date currentDate = new Date();
		site.setCreateTime(currentDate);
		site.setCreateUser(SessionContextUtil.getUserName());
		site.setUpdateTime(currentDate);
		site.setUpdateUser(SessionContextUtil.getUserName());
		site.setStatus(SiteStatus.P.toString());
		site = siteRepository.save(site);
		if(site.getSiteId() != null){
			Address address = DTOUtils.map(sf.getAddress(), Address.class);
			if(log.isDebugEnabled()){
				log.debug(address.toString());
			}
			address.setSiteId(site.getSiteId());
			address.setCreateTime(currentDate);
			address.setCreateUser(SessionContextUtil.getUserName());
			address.setUpdateTime(currentDate);
			address.setUpdateUser(SessionContextUtil.getUserName());
			address = addressRepository.save(address);
			if(address.getAddressId() != null){
				return site;
			}
		}
		return null;
	}
	
	public SiteDetails getSite(Long siteId) throws Exception{
		SiteDetails result = new SiteDetails();
		Site site = siteRepository.findOne(siteId);
		if(site == null){
			throw new ResourceNotFoundException(siteId);
		}
		result.setSite(site);
		Address address = addressRepository.findBySiteId(siteId);
		if(address != null){
			result.setAddress(address);
		}
		Community community = communityRepository.findBySiteId(siteId);
		if(community != null){
			result.setCommunity(community);
		}
		List<Price> prices = priceRepository.findBySiteId(siteId);
		if(prices != null){
			result.setPrices(prices);
		}
		List<Picture> pictures = pictureRepository.findBySiteId(siteId);
		if(pictures != null){
			result.setPictures(pictures);
		}
		return result;
	}

	public boolean updateSite(SiteForm sf) throws Exception{
		Site site = siteRepository.findOne(sf.getId());
		if(site == null){
			throw new ResourceNotFoundException(sf.getId());
		}
		site = populateSite(site, sf);

		site.setUpdateTime(new Date());
		site.setUpdateUser(SessionContextUtil.getUserName());
		Site result = siteRepository.save(site);
		
		Address address = addressRepository.findBySiteId(sf.getId());
		if(sf.getAddress() != null && address != null){
			address.setCity(sf.getAddress().getCity());
			address.setDistrict(sf.getAddress().getDistrict());
			address.setAddressDetail(sf.getAddress().getAddressDetail());
			address.setLatitude(sf.getAddress().getLatitude());
			address.setLongitude(sf.getAddress().getLongitude());
			address.setUpdateTime(new Date());
			address.setUpdateUser(SessionContextUtil.getUserName());
			addressRepository.save(address);
		}
		if(result != null){
			return true;
		}
		return false;
	}
	
	public boolean finishSite(Long siteId) throws Exception{
		Site site = siteRepository.findOne(siteId);
		if(site == null){
			throw new ResourceNotFoundException(siteId);
		}
		site.setUpdateTime(new Date());
		site.setUpdateUser(SessionContextUtil.getUserName());
		site.setStatus(SiteStatus.F.toString());
		Site result = siteRepository.save(site);
		if(result != null){
			return true;
		}
		return false;
	}
	
	private Site populateSite(Site site, SiteForm sf){
		if(site == null){
			site = new Site();
		}
		site.setName(sf.getName());
		site.setAgeStructure(sf.getAgeStructure());
		site.setConsumption(sf.getConsumption());
		site.setFlowrate(sf.getFlowrate());
		site.setHistory(sf.getHistory());
		site.setMaleVsFemale(sf.getMaleVsFemale());
		site.setProhibiteGoods(sf.getProhibiteGoods());
		site.setPropertyReq(sf.getPropertyReq());
		site.setSiteDetails(sf.getSiteDetails());
		site.setSiteType(sf.getSiteType());
		site.setStallPosition(sf.getStallPosition());
		site.setStallSize(sf.getStallSize());
		site.setStallTimeStart(sf.getStallTimeEnd());
		site.setStallTimeEnd(sf.getStallTimeStart());
		site.setSupportingFacilities(sf.getSupportingFacilities());
		site.setTotalArea(sf.getTotalArea());
		site.setUserParticipation(sf.getUserParticipation());
		return site;
	}

	public Community saveCommunity(Community community) throws SQLException{
		Date currentDate = new Date();
		community.setCreateTime(currentDate);
		community.setCreateUser(SessionContextUtil.getUserName());
		community.setUpdateTime(currentDate);
		community.setUpdateUser(SessionContextUtil.getUserName());
		community = communityRepository.save(community);
		return community;
	}

	public void updateCommunity(Community community) throws SQLException{
		if(community.getCommunityId() != null && community.getCommunityId() > 0){
			Community communityDB = communityRepository.findOne(community.getCommunityId());
			if(communityDB == null){
				throw new ResourceNotFoundException(community.getCommunityId());
			}
			DTOUtils.mapTo(community, communityDB);
			communityDB.setUpdateTime(new Date());
			communityDB.setUpdateUser(SessionContextUtil.getUserName());
			communityRepository.save(communityDB);
		}else{
			saveCommunity(community);
		}
	}

	public Price savePrice(Price price) throws Exception{
		price = priceRepository.save(price);
		return price;
	}

	public List<Price> savePrices(List<Price> prices, Long siteId) throws Exception{
		for (Price price : prices) {
			price.setSiteId(siteId);
			Date currentDate = new Date();
			price.setCreateTime(currentDate);
			price.setCreateUser(SessionContextUtil.getUserName());
			price.setUpdateTime(currentDate);
			price.setUpdateUser(SessionContextUtil.getUserName());
		}
		List<Price> result = priceRepository.save(prices);
		return result;
	}

	public void updatePrices(List<Price> prices, Long siteId) throws Exception{
		List<Price> pricesDB = priceRepository.findBySiteId(siteId);
		if(pricesDB == null){
			savePrices(prices, siteId);
		}else{
			List<Price> pricesUpdate = new ArrayList<>();
			List<Price> pricesAdd = new ArrayList<>();
			Map<Long, Price> map = new HashMap<>();//price need to update 
			for (Price p1 : prices) {
				if(p1.getSiteId() == null){
					pricesAdd.add(p1);
				}else{
					for (Price p2 : pricesDB) {
						if(p1.getPriceId() == p2.getPriceId()){
							if(!map.containsKey(p2.getPriceId())){
								map.put(p2.getPriceId(), p2);
							}
					        PropertyMap<Price,Price> propertyMap = new PropertyMap<Price, Price>() {
					            @Override
					            protected void configure() {
					                skip(destination.getPriceId());
					                skip(destination.getSiteId());
					                skip(destination.getCreateTime());
					                skip(destination.getCreateUser());
					            }
					        };
							DTOUtils.mapTo(p1, p2, propertyMap);
							p2.setUpdateTime(new Date());
							p2.setUpdateUser(SessionContextUtil.getUserName());
							pricesUpdate.add(p2);
						}
					}
				}
			}
			if(pricesAdd.size() > 0){//price which need to add
				savePrices(pricesAdd, siteId);
			}
			if(pricesUpdate.size() > 0){//price which need to update
				priceRepository.save(pricesUpdate);
			}
			if(pricesDB.size() > 0){//price which need to delete
				Predicate<Price> predicate = (s) -> map.containsKey(s.getPriceId());
				boolean removed = pricesDB.removeIf(predicate);
				if(removed){
					priceRepository.delete(pricesDB);
				}
			}
			
		}
	}
	
	public Picture uploadPicture(MultipartFile uploadFile, Long siteId) throws Exception{
		String fileName = uploadFile.getOriginalFilename();
		String suffix = "." + fileName.substring(fileName.lastIndexOf(".") + 1);
		File dst = null;
   	 	try {
//   	 		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
//   	 		ServletContext sc = wac.getServletContext();
//   	 		String root = System.getProperty("catalina.base");
   	 		//store img file into dist
   	 		File uploadDir = new File(Config.IMG_SITEPATH);
   	 		if (!uploadDir.exists()) {
   	 			uploadDir.mkdir();
   	 		}
   	 		String imgFile = UUID.randomUUID().toString() + suffix;
   	 		dst = new File(uploadDir,imgFile);
   	 		uploadFile.transferTo(dst);
   	 		//store img file path into db
   	 		Picture picture = new Picture();
   	 		picture.setSiteId(siteId);
   	 		picture.setPath("site" + "/" + imgFile);
   	 		Date currentDate = new Date();
   	 		picture.setCreateTime(currentDate);
   	 		picture.setCreateUser(SessionContextUtil.getUserName());
   	 		picture.setUpdateTime(currentDate);
   	 		picture.setUpdateUser(SessionContextUtil.getUserName());
   	 		picture = pictureRepository.save(picture);
   	 		
   	 		return picture;
   	 	} catch (Exception e) {
   	 		e.printStackTrace();
   	 	}
		return null;
	}

	public void deletePicutre(Long pictureId) throws Exception{
		Picture picture = pictureRepository.findOne(pictureId);
		if(picture != null){
			String imgName = picture.getPath().split("\\/")[1];
			File imgFile = new File(Config.IMG_SITEPATH + "\\" + imgName);
			if(imgFile.exists()){
				imgFile.delete();
			}
			pictureRepository.delete(pictureId);
		}
	}
}
