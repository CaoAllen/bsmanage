/*
Navicat MySQL Data Transfer

Source Server         : seckill
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : bsmanageadmin

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-07-06 16:09:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for price
-- ----------------------------
DROP TABLE IF EXISTS `price`;
CREATE TABLE `price` (
  `price_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL,
  `create_user` varchar(20) NOT NULL,
  `amount` decimal(19,2) DEFAULT NULL,
  `site_id` bigint(20) NOT NULL,
  `special_category` varchar(255) DEFAULT NULL,
  `stall_size` varchar(255) DEFAULT NULL,
  `time_unit` varchar(255) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  `update_user` varchar(20) NOT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`price_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
