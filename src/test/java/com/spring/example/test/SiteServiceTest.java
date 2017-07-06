package com.spring.example.test;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.spring.example.config.AppConfig;
import com.spring.example.config.DataJpaConfig;
import com.spring.example.config.DataSourceConfig;
import com.spring.example.config.JpaConfig;
import com.spring.example.domain.Site;
import com.spring.example.model.SiteDetails;
import com.spring.example.model.SiteItem;
import com.spring.example.model.WXSiteItem;
import com.spring.example.repository.SiteRepository;
import com.spring.example.repository.SitesGetRepository;
import com.spring.example.repository.example.MyTableRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, DataSourceConfig.class, DataJpaConfig.class, JpaConfig.class})
public class SiteServiceTest {
	private static final Logger log = LoggerFactory.getLogger(SiteServiceTest.class);
	
	@Inject
	private SiteRepository siteRepository;
	@Inject
	private SitesGetRepository sitesGetRepository;
	@Autowired
	MyTableRepository myTableRepository;
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetSites(){
		Page<SiteItem> items;
		try {
			Sort sort = new Sort(Direction.DESC, "sales.sales_volumn");
			PageRequest pageRequest = new PageRequest(0, 10, sort);
//			items = siteRepository.queryAllSites("上海市",pageRequest);
//			assertNotNull(items.getNumber());
//			log.debug(items.getNumber() + "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testGetSites1(){
		PageRequest pageRequest = new PageRequest(0, 10);
		BigDecimal low = new BigDecimal(0L);
		BigDecimal high = new BigDecimal(4000L);
		try {
			
			List<SiteDetails> siteDetails = sitesGetRepository.searchSitesByCriteria("", low, high, "");
			log.debug(siteDetails.size() + "");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();;
		}
	}

	@Test
	public void test2(){
		// 调用存储过程
		BigDecimal low = new BigDecimal(0L);
		BigDecimal high = new BigDecimal(4000L);
		PageRequest pageRequest = new PageRequest(0, 10, Direction.DESC, "salesVolumn");
		try {
			List<WXSiteItem> list = siteRepository.searchSites("上海市", "", low, high, "", pageRequest);
			log.debug(list.size() + "");
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Sort sort = new Sort(Direction.DESC, "sales.sales_volumn", "sales.sales_volumn");
		PageRequest pageRequest = new PageRequest(0, 10, sort);
		System.out.println(pageRequest.getSort().iterator().next().getProperty());
		System.out.println(pageRequest.getSort().getOrderFor("sales.sales_volumn").getDirection());
	}
}
