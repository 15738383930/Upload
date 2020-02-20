/*
Navicat MySQL Data Transfer

Source Server         : zhou
Source Server Version : 50528
Source Host           : 127.0.0.1:3306
Source Database       : upload

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2020-02-20 18:41:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for upload
-- ----------------------------
DROP TABLE IF EXISTS `upload`;
CREATE TABLE `upload` (
  `file_url` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of upload
-- ----------------------------
INSERT INTO `upload` VALUES ('http://localhost:8888/files/2020-02/250b8119-9b7c-4c58-a65b-cb665105e8e7.png', '1');
