package com.spring.example.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

import com.spring.example.model.SiteItem;
import com.spring.example.model.WXSiteItem;

public class SiteRepositoryImpl implements SiteRepositoryCustom{
	@PersistenceContext 
	private EntityManager em;
	
	@Override
	public List<WXSiteItem> searchSites(String city, String name, BigDecimal priceLow, BigDecimal priceHigh, String siteType, Pageable page){
		StringBuffer sqlStr = new StringBuffer("select * from (select s.site_id as id,s.name as name,s.flowrate as flowrate,a.city as city,"
				+ "a.address_detail as addressDetail,sa.sales_volumn as salesVolumn,sa.score as score,pt.path as path,pr.amount as price from site s,address a,sales sa,"
				+ "(select p1.* from price p1,site s1 where p1.site_id = s1.site_id GROUP BY p1.site_id ) pr,"
				+ "(select p2.* from picture p2,site s2 where p2.site_id = s2.site_id GROUP BY p2.site_id) pt "
				+ "where s.site_id = a.site_id and s.site_id = sa.site_id and s.site_id = pr.site_id and s.site_id = pt.site_id "
				+ "and s.status='F' and a.city = :city) t ");
		Iterator<Order> it = page.getSort().iterator();
		//only single sort
		if(it.hasNext()){
			Order order = it.next();
			sqlStr.append("order by t." + order.getProperty() + " " + order.getDirection());
		}
		int pageNo = page.getPageNumber();
		int pageSize = page.getPageSize();
		if(pageNo == 0){
			sqlStr.append(" limit " + pageSize + ";");
		}else {
			pageSize += pageNo;
			sqlStr.append(" limit " + pageNo + "," + pageSize + ";");
		}
		System.out.println(sqlStr.toString());
		Query query = this.em.createNativeQuery(sqlStr.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(WXSiteItem.class));
		@SuppressWarnings("unchecked")
		List<WXSiteItem> list = query.setParameter("city", city).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SiteItem> queryAllSites(String name, Pageable pageable) {
		StringBuffer sql = new StringBuffer("call bs_site_list (?,?,?,?)");
		Query query = this.em.createNativeQuery(sql.toString());
		query.setParameter(1, null);
		query.setParameter(2, 0);
		query.setParameter(3, 0);
		query.setParameter(4, "");
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(SiteItem.class));
		List list = query.getResultList();
		return list;
	}
}
