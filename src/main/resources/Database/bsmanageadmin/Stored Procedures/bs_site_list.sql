DROP PROCEDURE IF EXISTS `bs_site_list`;

CREATE DEFINER = `root`@`localhost` PROCEDURE `bs_site_list`(IN _name VARCHAR (100),
	IN _price_low DECIMAL (19, 2),
	IN _price_high DECIMAL (19, 2),
	IN _site_type VARCHAR (100))
BEGIN
	SELECT
		s.site_id AS siteId,
		s.NAME AS name,
		s.flowrate AS flowrate,
		s.site_type AS siteType,
		s.STATUS AS status,
		a.address_detail AS addressDetail,
CASE when s

	FROM
		site s,
		address a
	WHERE
		s.site_id = a.site_id
	AND (
		s.NAME LIKE CONCAT('%' ,_name, '%')
		OR _name IS NULL
	);
END;

