/*
Navicat MySQL Data Transfer

Source Server         : seckill
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : bsmanageadmin

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-07-06 16:09:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sales
-- ----------------------------
DROP TABLE IF EXISTS `sales`;
CREATE TABLE `sales` (
  `sales_id` int(11) NOT NULL AUTO_INCREMENT,
  `site_id` int(11) NOT NULL,
  `sales_volumn` int(11) NOT NULL DEFAULT '0',
  `score` int(2) NOT NULL DEFAULT '10',
  PRIMARY KEY (`sales_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
