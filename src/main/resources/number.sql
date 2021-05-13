/*
 Navicat Premium Data Transfer

 Source Server         : 笔记本电脑mysql
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : number

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 06/05/2021 10:44:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `name` varchar(255) ,
  `password` varchar(255)
);

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('admin', 'root');

-- ----------------------------
-- Table structure for number
-- ----------------------------
DROP TABLE IF EXISTS `number`;
CREATE TABLE `number`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nub` varchar(255) ,
  `high_1` varchar(255),
  `high_2` varchar(255) ,
  `high_3` varchar(255) ,
  `high_4` varchar(255) ,
  `time` datetime(0),
  PRIMARY KEY (`id`) USING BTREE
) ;

-- ----------------------------
-- Records of number
-- ----------------------------
INSERT INTO `number` VALUES (1, '10', '30', '20', '50', '10', '2021-03-23 23:26:20');
INSERT INTO `number` VALUES (2, '30', '40', '10', '20', '25', '2021-03-03 00:01:52');
INSERT INTO `number` VALUES (3, '20', '10', '30', '40', '10', '2021-03-04 12:10:22');
INSERT INTO `number` VALUES (4, '10', '20', '30', '40', '50', '2021-03-24 12:25:28');
INSERT INTO `number` VALUES (5, '20', '30', '50', '60', '80', '2021-03-24 13:42:11');
INSERT INTO `number` VALUES (6, '50', '60', '10', '50', '23', '2021-03-24 13:45:11');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) ,
  `password` varchar(255) ,
  `salt` varchar(255) ,
  `username` varchar(255) ,
  `email` varchar(255) ,
  `sex` varchar(1) ,
  `status` int(1),
  PRIMARY KEY (`id`) USING BTREE
);

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'ed8d789d95aa12d629a0b264d13cd1bc', '1620268198184', '张三', '123@qq.com', '男', 0);
INSERT INTO `user` VALUES (8, 'admin', 'f5f902793265c7e6aa73601e7eca93c8', '1620268646931', 'admin', '123456@qq.com', '男', 0);

SET FOREIGN_KEY_CHECKS = 1;
