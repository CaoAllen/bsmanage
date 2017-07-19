package com.spring.example.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

import com.spring.example.model.Page;
import com.spring.example.model.SiteItem;
import com.spring.example.model.WXSiteItem;

public class SiteRepositoryImpl implements SiteRepositoryCustom{
	@PersistenceContext 
	private EntityManager em;
	
	@Override
	public List<WXSiteItem> searchSites(String city, String name, BigDecimal priceLow, BigDecimal priceHigh, String siteType, Page page){
		StringBuffer sql = new StringBuffer("call wx_site_list (?,?,?,?,?,?,?,?,?)");
		Query query = this.em.createNativeQuery(sql.toString());
		query.setParameter(1, city);
		if("".equals(name)){
			query.setParameter(2, null);
		}else{
			query.setParameter(2, name);
		}
		query.setParameter(3, null);
		query.setParameter(4, null);
		query.setParameter(5, null);
		query.setParameter(6, page.getSortName());
		query.setParameter(7, page.getSortDirection());
		query.setParameter(8, page.getPageStart());
		query.setParameter(9, page.getPageSize());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(WXSiteItem.class));
		List list = query.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SiteItem> queryAllSites(String name, String district) {
		StringBuffer sql = new StringBuffer("call bs_site_list (?,?)");
		Query query = this.em.createNativeQuery(sql.toString());
		if(StringUtils.isEmpty(name)){
			query.setParameter(1, null);
		}else 
			query.setParameter(1, name);
		if(StringUtils.isEmpty(district)){
			query.setParameter(2, null);
		}else 
			query.setParameter(2, district);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SiteItem.class));
		List list = query.getResultList();
		return list;
	}
}
