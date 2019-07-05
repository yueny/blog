
-- ----------------------------
-- Table structure for mto_user_security 用户安全表
-- ----------------------------
DROP TABLE IF EXISTS `mto_user_security`;
CREATE TABLE `mto_user_security` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` varchar(256) COMMENT 'uid',
  `need_change_pw` TINYINT DEFAULT 0 COMMENT '是否需要修改密码， 0为不需要，1为需要修改密码',
  `salt` varchar(140) DEFAULT NULL COMMENT '密码盐值',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--  ok

-- ----------------------------
-- Table structure for view_log 用户安全表
-- ----------------------------
DROP TABLE IF EXISTS `view_log`;
CREATE TABLE `view_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_ip` varchar(16) COMMENT '客户端ip',
  `client_agent` varchar(256) COMMENT '客户端agent',
  `resource_path` varchar(512) DEFAULT NULL COMMENT '访问资源地址',
  `resource_path_desc` varchar(512) DEFAULT NULL COMMENT '访问资源地址描述',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
  KEY `IK_CLIENT_IP` (`client_ip`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

