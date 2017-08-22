/*
Navicat MySQL Data Transfer

Source Server         : Registration
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : bsmanageadmin

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-08-14 21:04:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for local_order
-- ----------------------------
DROP TABLE IF EXISTS `local_order`;
CREATE TABLE `local_order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) NOT NULL,
  `total_fee` decimal(10,0) NOT NULL,
  `contact_id` bigint(20) NOT NULL,
  `need_invoice` tinyint(1) NOT NULL,
  `need_arrange` tinyint(1) NOT NULL,
  `need_transport` tinyint(1) NOT NULL,
  `need_jianzhi` tinyint(1) NOT NULL,
  `point` int(11) DEFAULT NULL,
  `order_detail` text NOT NULL,
  `create_time` datetime NOT NULL,
  `create_user` varchar(20) NOT NULL,
  `update_time` datetime NOT NULL,
  `update_user` varchar(20) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
