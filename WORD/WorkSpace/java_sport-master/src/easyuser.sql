/*
Navicat MySQL Data Transfer

Source Server         : Testblog
Source Server Version : 50556
Source Host           : localhost:3306
Source Database       : easyproject

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2020-05-27 14:48:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for easyuser
-- ----------------------------
DROP TABLE IF EXISTS `easyuser`;
CREATE TABLE `easyuser` (
  `id` int(24) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of easyuser
-- ----------------------------
INSERT INTO `easyuser` VALUES ('1', 'admin', '123456', '19157697517');
INSERT INTO `easyuser` VALUES ('2', 'naughty', '888888', '19157697517');
INSERT INTO `easyuser` VALUES ('3', 'flysky01', '987654', '19157697517');
INSERT INTO `easyuser` VALUES ('4', 'anglybirds', '321654', '19157697517');
INSERT INTO `easyuser` VALUES ('7', 'admin003', 'q123456', '19157697517');
INSERT INTO `easyuser` VALUES ('8', 'admin005', '123456', '19157697517');
INSERT INTO `easyuser` VALUES ('9', 'admin007', '123456', '19157697517');
