
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

