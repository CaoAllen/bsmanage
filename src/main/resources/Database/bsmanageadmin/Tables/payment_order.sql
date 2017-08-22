/*
Navicat MySQL Data Transfer

Source Server         : Registration
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : bsmanageadmin

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-08-14 21:04:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for payment_order
-- ----------------------------
DROP TABLE IF EXISTS `payment_order`;
CREATE TABLE `payment_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(50) NOT NULL,
  `fee` decimal(18,2) NOT NULL,
  `nonce_str` varchar(32) DEFAULT NULL,
  `prepay_id` varchar(50) DEFAULT NULL,
  `time_stamp` varchar(50) DEFAULT NULL,
  `sign_type` varchar(50) DEFAULT NULL,
  `pay_sign` varchar(50) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL,
  `create_user` varchar(20) NOT NULL,
  `update_time` datetime NOT NULL,
  `update_user` varchar(20) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
