package com.spring.example.service;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.spring.example.config.Config;
import com.spring.example.domain.Address;
import com.spring.example.domain.Community;
import com.spring.example.domain.Picture;
import com.spring.example.domain.Price;
import com.spring.example.domain.Site;
import com.spring.example.exception.ResourceNotFoundException;
import com.spring.example.model.SiteForm;
import com.spring.example.model.SiteItem;
import com.spring.example.model.WXSiteItem;
import com.spring.example.repository.AddressRepository;
import com.spring.example.repository.CommunityRepository;
import com.spring.example.repository.PictureRepository;
import com.spring.example.repository.PriceRepository;
import com.spring.example.repository.SiteRepository;
import com.spring.example.utils.ContextUtil;
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
		site.setCreateUser(ContextUtil.getUserName());
		site.setUpdateTime(currentDate);
		site.setUpdateUser(ContextUtil.getUserName());
		site.setStatus(SiteStatus.P.toString());
		site = siteRepository.save(site);
		if(site.getSiteId() > 0){
			Address address = DTOUtils.map(sf.getAddress(), Address.class);
			if(log.isDebugEnabled()){
				log.debug(address.toString());
			}
			address.setSiteId(site.getSiteId());
			address.setCreateTime(currentDate);
			address.setCreateUser(ContextUtil.getUserName());
			address.setUpdateTime(currentDate);
			address.setUpdateUser(ContextUtil.getUserName());
			address = addressRepository.save(address);
			if(address.getAddressId() > 0){
				return site;
			}
		}
		return null;
	}

	public boolean updateSite(SiteForm sf) {
		Site site = siteRepository.findOne(sf.getId());
		if(site == null){
			throw new ResourceNotFoundException(sf.getId());
		}
		site = populateSite(site, sf);

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		site.setUpdateTime(new Date());
		site.setUpdateUser(userDetails.getUsername());
		site.setStatus(SiteStatus.F.toString());
		
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
		community = communityRepository.save(community);
		return community;
	}

	public Price savePrice(Price price) throws Exception{
		price = priceRepository.save(price);
		return price;
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
   	 		File uploadDir = new File(Config.IMG_BasePath + "\\site");
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
   	 		picture.setCreateUser(ContextUtil.getUserName());
   	 		picture.setUpdateTime(currentDate);
   	 		picture.setUpdateUser(ContextUtil.getUserName());
   	 		picture = pictureRepository.save(picture);
   	 		
   	 		return picture;
   	 	} catch (Exception e) {
   	 		e.printStackTrace();
   	 	}
		return null;
	}
}
