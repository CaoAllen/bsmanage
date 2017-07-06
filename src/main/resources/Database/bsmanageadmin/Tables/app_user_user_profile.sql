/*
Navicat MySQL Data Transfer

Source Server         : seckill
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : bsmanageadmin

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2017-07-06 16:10:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_user_user_profile
-- ----------------------------
DROP TABLE IF EXISTS `app_user_user_profile`;
CREATE TABLE `app_user_user_profile` (
  `USER_ID` int(11) NOT NULL,
  `USER_PROFILE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_ID`,`USER_PROFILE_ID`),
  KEY `FK8w1hjq4mgw8b6xx41bb3q9yqk` (`USER_PROFILE_ID`),
  CONSTRAINT `FK8w1hjq4mgw8b6xx41bb3q9yqk` FOREIGN KEY (`USER_PROFILE_ID`) REFERENCES `user_profile` (`id`),
  CONSTRAINT `FKt3cvepgn2tno4f85jvkauv71o` FOREIGN KEY (`USER_ID`) REFERENCES `app_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
