/*
Navicat MySQL Data Transfer

Source Server         : seckill
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : bsmanageadmin

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-07-06 16:09:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for community
-- ----------------------------
DROP TABLE IF EXISTS `community`;
CREATE TABLE `community` (
  `community_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NOT NULL,
  `year` int(11) DEFAULT NULL,
  `property_fee` int(11) DEFAULT NULL,
  `housing_price` int(11) DEFAULT NULL,
  `households` int(11) DEFAULT NULL,
  `occupancy_rate` varchar(10) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `create_user` varchar(20) NOT NULL,
  `update_time` datetime NOT NULL,
  `update_user` varchar(20) NOT NULL,
  PRIMARY KEY (`community_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
