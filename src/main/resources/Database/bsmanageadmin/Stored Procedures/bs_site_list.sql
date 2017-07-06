DROP PROCEDURE IF EXISTS `bs_site_list`;

CREATE PROCEDURE `bs_site_list`(IN _name VARCHAR (100),
	IN _price_low DECIMAL (19, 2),
	IN _price_high DECIMAL (19, 2),
	IN _site_type VARCHAR (100))
BEGIN
	SELECT
		s.site_id AS siteId,
		s.NAME AS name,
		s.flowrate AS flowrate,
		p.amount as amount,
		s.site_type AS siteType,
		s.STATUS AS status,
		a.address_detail AS addressDetail,
		IFNULL(ss.sales_volumn,0) as salesVolumn

	FROM
		site s 
	LEFT JOIN (price p, address a, sales ss) on (s.site_id = p.site_id and s.site_id = a.site_id and s.site_id = ss.site_id)

	WHERE (
		s.NAME LIKE CONCAT('%' ,_name, '%')
		OR _name IS NULL)
	GROUP BY s.site_id;
END;

