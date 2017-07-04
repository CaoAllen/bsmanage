package com.spring.example.repository;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.example.domain.Site;
import com.spring.example.model.SiteItem;

public interface SiteRepository extends JpaRepository<Site, Long>,JpaSpecificationExecutor<Site>,SiteRepositoryCustom{
	
//	@Query(value = "SELECT s.site_id as siteId, s. NAME as name, s.flowrate as flowrate, s.site_type as siteType,"
//			+ "s.STATUS as status, a.address_detail as addressDetail FROM site s, address a Where s.site_id = a.site_id "
//			+ "and (s.name like CONCAT('%',:name,'%') or :name is null) ORDER BY \n#pageable\n",nativeQuery = true)
//	List<SiteItem> queryAllSites(@Param("name") String name,Pageable pageable) throws SQLException;
}
 