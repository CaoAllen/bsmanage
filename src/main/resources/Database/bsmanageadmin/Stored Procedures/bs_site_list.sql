DROP PROCEDURE IF EXISTS `bs_site_list`;

CREATE PROCEDURE `bs_site_list`(IN _name VARCHAR (100),
	IN _district VARCHAR (100))
BEGIN
	SELECT
		s.site_id AS siteId,
		s.NAME AS name,
		s.flowrate AS flowrate,
		p.amount as amount,
		s.site_type AS siteType,
		s.STATUS AS status,
		CONCAT(IFNULL(a.district,''),a.address_detail) AS addressDetail,
		IFNULL(ss.sales_volumn,0) as salesVolumn

	FROM
		site s 
	LEFT JOIN price p on s.site_id = p.site_id
	LEFT JOIN address a on s.site_id = a.site_id
	LEFT JOIN sales ss on s.site_id = ss.site_id
	
	WHERE  s.status in ('P','F') 
	AND (
		s.NAME LIKE CONCAT('%' ,_name, '%')
		OR _name IS NULL)
	AND (a.district = _district OR _district IS null)
	GROUP BY s.site_id;
END;

