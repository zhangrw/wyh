/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50553
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50553
File Encoding         : 65001

Date: 2016-12-24 17:37:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` varchar(128) CHARACTER SET utf8 NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `type` int(2) DEFAULT NULL COMMENT '1:菜单组  2:菜单项',
  `permission` varchar(128) CHARACTER SET utf8 DEFAULT NULL,
  `url` varchar(500) CHARACTER SET utf8 DEFAULT NULL,
  `parent_id` varchar(128) CHARACTER SET utf8 DEFAULT NULL,
  `dept_order` int(3) DEFAULT NULL,
  `description` varchar(1000) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('1', '我是某部门', '1', null, null, '0', null, '');
INSERT INTO `sys_dept` VALUES ('ccdb2a78-d2b0-4f2d-9a5a-3255c5fb0c7f', '子部门', '2', '6', '/sys/permission', '1', '3', '子部门1');
INSERT INTO `sys_dept` VALUES ('3957d919-4c36-443a-865f-a7c96da1ebea', '子部门1', '2', '3407a0df-088a-46d3-b7b9-0f4aae363e13', '/sys/role', '1', '2', '子部门2');
INSERT INTO `sys_dept` VALUES ('66ccf32c-0619-4cbb-b7aa-2e22e5726e5b', '子部门3', '2', '3', '/sys/user', '1', '1', '子部门3');
INSERT INTO `sys_dept` VALUES ('0', '根节点名称可以修改', '0', 'sys:menu', null, '0', null, '');
INSERT INTO `sys_dept` VALUES ('60b2a3c7-d33c-4c52-b8d6-46676d7fcfd9', '子部门2', '2', '3ca98c84-f557-43f2-8fc3-bfb9703b4dc1', '/sys/menu', '1', '4', '1111');
INSERT INTO `sys_dept` VALUES ('e6086ee2-cddd-40b2-82dd-54abba4ec0ae', '孙部门', null, null, null, '60b2a3c7-d33c-4c52-b8d6-46676d7fcfd9', '4', '1222');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(128) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `type` int(2) DEFAULT NULL COMMENT '1:菜单组  2:菜单项',
  `permission` varchar(128) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `parent_id` varchar(128) DEFAULT NULL,
  `menu_order` int(3) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '系统管理', '1', null, null, '0', null, null);
INSERT INTO `sys_menu` VALUES ('ccdb2a78-d2b0-4f2d-9a5a-3255c5fb0c7f', '权限管理', '2', '6', '/sys/permission', '1', '3', '');
INSERT INTO `sys_menu` VALUES ('3957d919-4c36-443a-865f-a7c96da1ebea', '角色管理', '2', '3407a0df-088a-46d3-b7b9-0f4aae363e13', '/sys/role', '1', '2', '1234');
INSERT INTO `sys_menu` VALUES ('66ccf32c-0619-4cbb-b7aa-2e22e5726e5b', '用户管理', '2', '3', '/sys/user', '1', '1', '');
INSERT INTO `sys_menu` VALUES ('0', '项目', '0', 'sys:menu', null, null, null, null);
INSERT INTO `sys_menu` VALUES ('60b2a3c7-d33c-4c52-b8d6-46676d7fcfd9', '菜单管理', '2', '3ca98c84-f557-43f2-8fc3-bfb9703b4dc1', '/sys/menu', '1', '4', '');
INSERT INTO `sys_menu` VALUES ('878e860e-b4de-49d1-9c32-ae98c79b1f40', '部门管理', '2', '96cb1daf-6c46-4720-a020-ee6acd171bd7', '/sys/dept', '1', '0', '');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` varchar(128) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  `parent_id` varchar(128) DEFAULT NULL,
  `is_delete` varchar(2) DEFAULT NULL,
  `remark` varchar(2000) DEFAULT NULL,
  `code` varchar(200) DEFAULT NULL,
  `type` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('c199ca13-b325-4b25-bede-efee2514ac22', '角色删除', '', '3407a0df-088a-46d3-b7b9-0f4aae363e13', null, '', 'role:del', '3');
INSERT INTO `sys_permission` VALUES ('1', '用户数据查询', null, '3', null, null, 'user:query', '3');
INSERT INTO `sys_permission` VALUES ('60ec28a9-7d51-43d4-86c6-2efd776b6ddd', '角色新增', '', '3407a0df-088a-46d3-b7b9-0f4aae363e13', null, '', 'role:add', '3');
INSERT INTO `sys_permission` VALUES ('8', '权限更新', null, '6', null, null, 'permission:update', '3');
INSERT INTO `sys_permission` VALUES ('cc52480a-405f-4483-b4c3-b73ea2e72d32', '角色更新', '', '3407a0df-088a-46d3-b7b9-0f4aae363e13', null, '', 'role:update', '3');
INSERT INTO `sys_permission` VALUES ('2', '系统管理', '', '0', null, '', 'sys:mp', '2');
INSERT INTO `sys_permission` VALUES ('6', '权限管理', '/sys/permission', '2', null, '', 'sys:pmp', '2');
INSERT INTO `sys_permission` VALUES ('4', '用户删除', null, '3', null, null, 'user:del', '3');
INSERT INTO `sys_permission` VALUES ('5', '用户更新', null, '3', null, null, 'user:update', '3');
INSERT INTO `sys_permission` VALUES ('3407a0df-088a-46d3-b7b9-0f4aae363e13', '角色管理', '/sys/role', '2', null, '', 'sysmgp:role', '2');
INSERT INTO `sys_permission` VALUES ('0', '项目名称', '', '', null, '', 'sys:sysmgp', '1');
INSERT INTO `sys_permission` VALUES ('3', '用户管理', '/sys/user', '2', null, '', 'sys:usermgp', '2');
INSERT INTO `sys_permission` VALUES ('7', '权限新增', null, '6', null, null, 'permission:add', '3');
INSERT INTO `sys_permission` VALUES ('17b8c11c-da09-46e3-ace1-f3a3fb87c0ca', '用户新增', '', '3', null, '', 'user:add', '3');
INSERT INTO `sys_permission` VALUES ('0115bb96-c0bb-4538-a0fa-723317913991', '菜单新建', '/sys/menu/add', '3ca98c84-f557-43f2-8fc3-bfb9703b4dc1', null, '', 'sysmenu:add', '3');
INSERT INTO `sys_permission` VALUES ('3ca98c84-f557-43f2-8fc3-bfb9703b4dc1', '菜单管理', '/sys/menu', '2', null, '', 'sys:menu', '2');
INSERT INTO `sys_permission` VALUES ('62a7bea1-40c8-4b91-9f80-5bf5b8f6d3df', '菜单删除', '/sys/menu/delete', '3ca98c84-f557-43f2-8fc3-bfb9703b4dc1', null, '', 'sysmenu:del', '3');
INSERT INTO `sys_permission` VALUES ('7445e521-dfef-4aa4-aeb1-d0ff472dbad8', '菜单更新', '/sys/menu/update', '3ca98c84-f557-43f2-8fc3-bfb9703b4dc1', null, '', 'sysmenu:update', '3');
INSERT INTO `sys_permission` VALUES ('f640c6cd-f97f-48ee-a46e-a43a5bf77070', '权限删除', '/sys/permission/delete', '6', null, '', 'permission:del', '3');
INSERT INTO `sys_permission` VALUES ('96cb1daf-6c46-4720-a020-ee6acd171bd7', '部门管理', '/sys/dept', '2', null, '', 'sys:dept', '2');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(128) NOT NULL,
  `rolename` varchar(45) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'sys', '系统管理员权限(用户/角色/权限 的维护)');
INSERT INTO `sys_role` VALUES ('4d857666-8669-4902-a4c6-3e73d1b46161', 'menu_role', '菜单维护');
INSERT INTO `sys_role` VALUES ('c9eee320-f07d-4fcd-af1b-ee29d9e8e9f1', '部门信息维护', '');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `permission_id` varchar(128) DEFAULT NULL,
  `role_id` varchar(128) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('2', '1');
INSERT INTO `sys_role_permission` VALUES ('3ca98c84-f557-43f2-8fc3-bfb9703b4dc1', '1');
INSERT INTO `sys_role_permission` VALUES ('0115bb96-c0bb-4538-a0fa-723317913991', '1');
INSERT INTO `sys_role_permission` VALUES ('62a7bea1-40c8-4b91-9f80-5bf5b8f6d3df', '1');
INSERT INTO `sys_role_permission` VALUES ('7445e521-dfef-4aa4-aeb1-d0ff472dbad8', '1');
INSERT INTO `sys_role_permission` VALUES ('6', '1');
INSERT INTO `sys_role_permission` VALUES ('7', '1');
INSERT INTO `sys_role_permission` VALUES ('8', '1');
INSERT INTO `sys_role_permission` VALUES ('3', '1');
INSERT INTO `sys_role_permission` VALUES ('17b8c11c-da09-46e3-ace1-f3a3fb87c0ca', '1');
INSERT INTO `sys_role_permission` VALUES ('4', '1');
INSERT INTO `sys_role_permission` VALUES ('1', '1');
INSERT INTO `sys_role_permission` VALUES ('5', '1');
INSERT INTO `sys_role_permission` VALUES ('3407a0df-088a-46d3-b7b9-0f4aae363e13', '1');
INSERT INTO `sys_role_permission` VALUES ('60ec28a9-7d51-43d4-86c6-2efd776b6ddd', '1');
INSERT INTO `sys_role_permission` VALUES ('c199ca13-b325-4b25-bede-efee2514ac22', '1');
INSERT INTO `sys_role_permission` VALUES ('cc52480a-405f-4483-b4c3-b73ea2e72d32', '1');
INSERT INTO `sys_role_permission` VALUES ('2', '4d857666-8669-4902-a4c6-3e73d1b46161');
INSERT INTO `sys_role_permission` VALUES ('3ca98c84-f557-43f2-8fc3-bfb9703b4dc1', '4d857666-8669-4902-a4c6-3e73d1b46161');
INSERT INTO `sys_role_permission` VALUES ('0115bb96-c0bb-4538-a0fa-723317913991', '4d857666-8669-4902-a4c6-3e73d1b46161');
INSERT INTO `sys_role_permission` VALUES ('62a7bea1-40c8-4b91-9f80-5bf5b8f6d3df', '4d857666-8669-4902-a4c6-3e73d1b46161');
INSERT INTO `sys_role_permission` VALUES ('7445e521-dfef-4aa4-aeb1-d0ff472dbad8', '4d857666-8669-4902-a4c6-3e73d1b46161');
INSERT INTO `sys_role_permission` VALUES ('2', 'c9eee320-f07d-4fcd-af1b-ee29d9e8e9f1');
INSERT INTO `sys_role_permission` VALUES ('96cb1daf-6c46-4720-a020-ee6acd171bd7', 'c9eee320-f07d-4fcd-af1b-ee29d9e8e9f1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(128) NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `salt` varchar(16) DEFAULT NULL,
  `loginname` varchar(255) DEFAULT NULL,
  `dept` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '超级管理员', '74682ec506b25cadf1da4fcd37ec7cf227d5919d', '6042ef4277a7c58f', 'admin', null, '');
INSERT INTO `sys_user` VALUES ('4e690c01-0353-461e-b355-97ac0b52c02b', '测试导入', 'ba2d6697a815b8f3bb41d2947057fe59274d09dc', '7745934d4488213d', 'test', null, null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` varchar(128) DEFAULT NULL,
  `role_id` varchar(128) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');

-- ----------------------------
-- Table structure for t_index_user
-- ----------------------------
DROP TABLE IF EXISTS `t_index_user`;
CREATE TABLE `t_index_user` (
  `id` varchar(64) CHARACTER SET utf8 NOT NULL COMMENT '主键ID',
  `job_number` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '工号',
  `name` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '姓名',
  `sex` int(11) DEFAULT NULL COMMENT '1:男；2:女',
  `id_number` varchar(32) CHARACTER SET utf8 DEFAULT NULL COMMENT '身份证号',
  `bank_number` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '银行卡号',
  `dept_id` varchar(64) CHARACTER SET utf8 DEFAULT NULL COMMENT '所属单位(id)',
  `bz` varchar(256) CHARACTER SET utf8 DEFAULT NULL COMMENT '备注信息',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of t_index_user
-- ----------------------------
INSERT INTO `t_index_user` VALUES ('1', '000001', '00000', '1', '123456789012345678', null, null, null, '2016-12-24 17:35:27');
INSERT INTO `t_index_user` VALUES ('4e690c01-0353-461e-b355-97ac0b52c02b', '006321', '测试导入', '1', '123456789012345678', '370784198910084334', 'e6086ee2-cddd-40b2-82dd-54abba4ec0ae', '备注信息', '2016-12-24 17:35:24');

-- ----------------------------
-- Table structure for t_trans_info
-- ----------------------------
DROP TABLE IF EXISTS `t_trans_info`;
CREATE TABLE `t_trans_info` (
  `serial_number` varchar(32) DEFAULT NULL COMMENT '流水号',
  `trans_value` varchar(32) DEFAULT NULL COMMENT '转账金额',
  `user_id` varchar(64) DEFAULT NULL,
  `srcbank_number` varchar(64) DEFAULT NULL,
  `srcbank_name` varchar(200) DEFAULT NULL,
  `targetbank_name` varchar(200) DEFAULT NULL,
  `targetbank_number` varchar(64) DEFAULT NULL,
  `state` int(1) DEFAULT NULL COMMENT '1:等待中,2:已转失败,3:已转成功',
  `bz` varchar(1000) DEFAULT NULL COMMENT '备注信息',
  `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_trans_info
-- ----------------------------

-- ----------------------------
-- Function structure for queryParent
-- ----------------------------
DROP FUNCTION IF EXISTS `queryParent`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` FUNCTION `queryParent`(areaId VARCHAR(1000)) RETURNS varchar(4000) CHARSET utf8
BEGIN
DECLARE sTemp VARCHAR(4000);
DECLARE sTempChd VARCHAR(4000);

SET sTemp = '$';
SET sTempChd = cast(areaId as char);

WHILE sTempChd is not NULL DO
SET sTemp = CONCAT(sTemp,',',sTempChd);
SELECT group_concat(parent_Id) INTO sTempChd FROM sys_menu where FIND_IN_SET(id,sTempChd)>0;
END WHILE;
return sTemp;
END
;;
DELIMITER ;
