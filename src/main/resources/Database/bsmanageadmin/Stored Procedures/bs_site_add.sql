DROP PROCEDURE IF EXISTS `bs_site_add`;

CREATE PROCEDURE bs_site_add (
	IN name VARCHAR(100), 
	IN stall_size VARCHAR(20), 
	IN stall_time_start time, 
	IN stall_time_end time, 
	IN total_area INT,
	IN stall_position VARCHAR(20), 
	IN property_req VARCHAR(200), 
	IN prohibite_goods VARCHAR(100), 
	IN supporting_facilities VARCHAR(100), 
	IN user_participation VARCHAR(10), 
	IN male_vs_female VARCHAR(10), 
	IN age_structure VARCHAR(30), 
	IN consumption VARCHAR(10), 
	IN site_details VARCHAR(1000) 
)  
MODIFIES SQL DATA
BEGIN  
   INSERT INTO `site` (
	`site_id`,
	`name`,
	`stall_size`,
	`stall_time_start`,
	`stall_time_end`,
	`total_area`,
	`stall_position`,
	`property_req`,
	`prohibite_goods`,
	`supporting_facilities`,
	`user_participation`,
	`male_vs_female`,
	`age_structure`,
	`consumption`,
	`site_details`
)
VALUES
	(
		NULL,
		name,
		stall_size,
		stall_time_start,
		stall_time_end,
		total_area,
		stall_position,
		property_req,
		prohibite_goods,
		supporting_facilities,
		user_participation,
		male_vs_female,
		age_structure,
		consumption,
		site_details
	); 
END 
