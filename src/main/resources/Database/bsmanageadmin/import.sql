/* For Remember-Me token storage purpose */  
CREATE TABLE persistent_logins (  
    username VARCHAR(64) NOT NULL,  
    series VARCHAR(64) NOT NULL,  
    token VARCHAR(64) NOT NULL,  
    last_used TIMESTAMP NOT NULL,  
    PRIMARY KEY (series)  
);  
   
   
/*All User's gets stored in APP_USER table*/  
create table APP_USER (  
   id BIGINT NOT NULL AUTO_INCREMENT,  
   name VARCHAR(30) NOT NULL,  
   password VARCHAR(100) NOT NULL,   
   email VARCHAR(30) NOT NULL,  
   state VARCHAR(30) NOT NULL,    
   PRIMARY KEY (id),  
   UNIQUE (name)  
);  
    
/* USER_PROFILE table contains all possible roles */    
create table USER_PROFILE(  
   id BIGINT NOT NULL AUTO_INCREMENT,  
   type VARCHAR(30) NOT NULL,  
   PRIMARY KEY (id),  
   UNIQUE (type)  
);  
    
/* JOIN TABLE for MANY-TO-MANY relationship*/     
CREATE TABLE APP_USER_USER_PROFILE (  
    user_id BIGINT NOT NULL,  
    user_profile_id BIGINT NOT NULL,  
    PRIMARY KEY (user_id, user_profile_id),  
    CONSTRAINT FK_APP_USER FOREIGN KEY (user_id) REFERENCES APP_USER (id),  
    CONSTRAINT FK_USER_PROFILE FOREIGN KEY (user_profile_id) REFERENCES USER_PROFILE (id)  
);  
   
/* Populate USER_PROFILE Table */  
INSERT INTO USER_PROFILE(type)  
VALUES ('USER');  
   
INSERT INTO USER_PROFILE(type)  
VALUES ('ADMIN');  
   
INSERT INTO USER_PROFILE(type)  
VALUES ('DBA');  
   
/* Populate one Admin User. We need only one user to demonstrate this example. You can add more as done in previous posts*/  
INSERT INTO APP_USER(name, password, email, state)  
VALUES ('admin','test123', 'admin@admin.com', 'Active');  
   
/* Populate JOIN Table */  
INSERT INTO APP_USER_USER_PROFILE (user_id, user_profile_id)  
  SELECT user.id, profile.id FROM app_user user, user_profile profile  
  where user.name='admin' and profile.type='ADMIN'; 
  
 -- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `community_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NOT NULL,
  `year` int(11) DEFAULT NULL,
  `property_fee` decimal(11,2) DEFAULT NULL,
  `housing_price` int(11) DEFAULT NULL,
  `households` int(11) DEFAULT NULL,
  `occupancy_rate` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`community_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `picture_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NOT NULL,
  `path` varchar(20) NOT NULL,
  PRIMARY KEY (`picture_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for price
-- ----------------------------
DROP TABLE IF EXISTS `price`;
CREATE TABLE `price` (
  `price_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NOT NULL,
  `price` decimal(11,2) NOT NULL,
  `stall_size` varchar(20) NOT NULL,
  `time_unit` varchar(10) NOT NULL,
  `special_category` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`price_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for site
-- ----------------------------
DROP TABLE IF EXISTS `site`;
CREATE TABLE `site` (
  `site_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `stall_size` varchar(20) DEFAULT NULL,
  `stall_time_start` time DEFAULT '00:00:00',
  `stall_time_end` time DEFAULT '00:00:00',
  `total_area` int(11) DEFAULT NULL,
  `stall_position` varchar(20) DEFAULT NULL,
  `property_req` varchar(200) DEFAULT NULL,
  `prohibite_goods` varchar(100) DEFAULT NULL,
  `supporting_facilities` varchar(100) DEFAULT NULL,
  `user_participation` varchar(10) DEFAULT NULL,
  `male_vs_female` varchar(10) DEFAULT NULL,
  `age_structure` varchar(30) DEFAULT NULL,
  `consumption` varchar(10) DEFAULT NULL,
  `site_details` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

