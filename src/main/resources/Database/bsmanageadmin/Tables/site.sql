/*
Navicat MySQL Data Transfer

Source Server         : seckill
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : bsmanageadmin

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-07-06 16:08:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for site
-- ----------------------------
DROP TABLE IF EXISTS `site`;
CREATE TABLE `site` (
  `site_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age_structure` varchar(10) DEFAULT NULL,
  `consumption` varchar(10) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `create_user` varchar(20) NOT NULL,
  `flowrate` int(11) DEFAULT NULL,
  `history` varchar(255) DEFAULT NULL,
  `male_vs_female` varchar(10) DEFAULT NULL,
  `prohibite_goods` varchar(255) DEFAULT NULL,
  `property_req` varchar(255) DEFAULT NULL,
  `site_details` varchar(1000) DEFAULT NULL,
  `site_type` varchar(20) DEFAULT NULL,
  `stall_position` varchar(40) DEFAULT NULL,
  `stall_size` varchar(20) DEFAULT NULL,
  `stall_time_end` time DEFAULT NULL,
  `stall_time_start` time DEFAULT NULL,
  `supporting_facilities` varchar(255) DEFAULT NULL,
  `total_area` int(11) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `update_user` varchar(20) NOT NULL,
  `user_participation` varchar(10) DEFAULT NULL,
  `status` varchar(2) NOT NULL,
  PRIMARY KEY (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
