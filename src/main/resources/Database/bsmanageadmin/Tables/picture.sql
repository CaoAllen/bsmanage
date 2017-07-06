/*
Navicat MySQL Data Transfer

Source Server         : seckill
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : bsmanageadmin

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-07-06 16:09:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for picture
-- ----------------------------
DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `picture_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `site_id` bigint(20) NOT NULL,
  `path` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  `create_user` varchar(20) NOT NULL,
  `update_time` datetime NOT NULL,
  `update_user` varchar(20) NOT NULL,
  PRIMARY KEY (`picture_id`),
  KEY `FKdxeswpjdg14mqxcv0ew87c3g1` (`site_id`),
  CONSTRAINT `FKdxeswpjdg14mqxcv0ew87c3g1` FOREIGN KEY (`site_id`) REFERENCES `site` (`site_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
