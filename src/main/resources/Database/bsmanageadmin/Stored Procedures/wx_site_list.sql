DROP PROCEDURE IF EXISTS `wx_site_list`;

CREATE PROCEDURE `wx_site_list` (
	IN _city VARCHAR (20),
	IN _name VARCHAR (100),
	IN _site_type VARCHAR (100),
	IN _price_low DECIMAL (19, 2),
	IN _price_high DECIMAL (19, 2),
	IN _sortName VARCHAR (20),
	IN _sortDirection VARCHAR (5),
	IN _pageNo INT,
	IN _pageSize INT
)
BEGIN
			DROP TABLE IF EXISTS tempSite;
			CREATE TEMPORARY TABLE tempSite
			SELECT
				s.site_id AS id,
				s. NAME AS name,
				s.flowrate AS flowrate,
				s.create_time as createTime,
				a.city AS city,
				a.address_detail AS addressDetail,
				sa.sales_volumn AS salesVolumn,
				sa.score AS score,
				pt.path AS path,
				pr.amount AS price
			FROM
				site s,
				address a,
				sales sa,
				(
					SELECT
						p1.*
					FROM
						price p1,
						site s1
					WHERE
						p1.site_id = s1.site_id
					GROUP BY
						p1.site_id
				) pr,
				(
					SELECT
						p2.*
					FROM
						picture p2,
						site s2
					WHERE
						p2.site_id = s2.site_id
					GROUP BY
						p2.site_id
				) pt
			WHERE
				s.site_id = a.site_id
			AND s.site_id = sa.site_id
			AND s.site_id = pr.site_id
			AND s.site_id = pt.site_id
			AND s.STATUS = 'F'
			AND a.city = _city
			AND (s.`name` like CONCAT('%',_name,'%') or IFNULL(_name,null) is null)
			AND (s.site_type = _site_type or _site_type is null)
		;

		set @strsql = CONCAT('SELECT * FROM tempSite ORDER BY ',_sortName,' ',_sortDirection,' LIMIT ',_pageNo,',',_pageSize);
		prepare strsql from @strsql;
		execute strsql;
END

