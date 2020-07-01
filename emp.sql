/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50720
Source Host           : 127.0.0.1:3306
Source Database       : emp

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-06-30 18:49:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_emp`
-- ----------------------------
DROP TABLE IF EXISTS `t_emp`;
CREATE TABLE `t_emp` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  `path` varchar(100) DEFAULT NULL,
  `salary` double(10,2) DEFAULT NULL,
  `age` int(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_emp
-- ----------------------------
INSERT INTO `t_emp` VALUES ('11', '张飞12', 'a3329f7e-a275-438e-a160-cd1c9e23a116.jpg', '1.00', '2');
INSERT INTO `t_emp` VALUES ('14', '刘备', '64be6a87-8979-43aa-bbeb-e07ff4f862e8.jpg', '120.00', '119');
INSERT INTO `t_emp` VALUES ('25', '曹操', '239fe08c-4b14-457e-8a53-0b67f5fc0ab0.jpg', '1.00', '2');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `username` varchar(60) DEFAULT NULL,
  `realname` varchar(60) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `sex` varchar(4) DEFAULT NULL,
  `status` varchar(4) DEFAULT NULL,
  `register_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('2', 'admin', '小陈', '123', '男', '已激活', '2020-06-20 08:43:35');
INSERT INTO `t_user` VALUES ('4', 'zhangsan', '小张', '123', '男', '已激活', '2020-06-20 08:53:22');
INSERT INTO `t_user` VALUES ('8', 'qwe', '123', '123', '女', '已激活', '2020-06-26 10:30:49');
INSERT INTO `t_user` VALUES ('10', 'lisi', '李四', '123', '女', '已激活', '2020-06-28 14:37:38');
INSERT INTO `t_user` VALUES ('11', '123', '321', '123', '男', '已激活', '2020-06-30 01:13:42');
INSERT INTO `t_user` VALUES ('13', 'hehe', '呵呵大', '123', '女', '已激活', '2020-06-30 09:57:49');
