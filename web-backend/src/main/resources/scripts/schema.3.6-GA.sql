-- 20190403
-- [栏目管理] 新增栏目对外释义列, 用于栏目的链接展示和查询
alter table `mto_channel` add column flag varchar(256) COMMENT '对外释义标识, 一般可以等于关键字' after key_;
update `mto_channel` set flag=key_
where flag is null;

-- 20190405
-- [用户信息] 新增个人域名自定义
alter table `mto_user` add column domain_hack varchar(256) COMMENT '用户自定义个性域名' after signature;
update `mto_user` set domain_hack=id
where domain_hack is null;

-- 20190406
-- 用户信息生成uuid
alter table `mto_user` add column uid varchar(256) COMMENT 'uid' after id;
update `mto_user` set uid=id
where uid is null;
--  alter table `shiro_user_role` add column uid varchar(256) COMMENT 'uid';

-- 20190411
-- 图片资源整合
alter table `mto_resource` add column thumbnail_code varchar(128) NOT NULL DEFAULT '' COMMENT '图片资源编号' after id;
update `mto_resource` set thumbnail_code=id
where thumbnail_code is null;

alter table `mto_channel` add column thumbnail_code varchar(128) NOT NULL DEFAULT '' COMMENT '图片资源编号' after status;
alter table `mto_channel` drop column thumbnail;

-- 20190416 渠道分类区分叶子非叶子节点
alter table `mto_channel` add column node_type varchar(64) NOT NULL DEFAULT '' COMMENT '节点类型,1, 叶子节点,LEAF_NODE, 此节点包含对外展示的有效路径;  0, 子节点, V_NODE' after `flag`;
alter table `mto_channel` add column channel_code varchar(64) NOT NULL DEFAULT '' COMMENT '渠道编号' after `id`;
alter table `mto_channel` add column parent_channel_code varchar(64) NOT NULL DEFAULT '' COMMENT '父渠道编号' after `channel_code`;
update `mto_channel` set parent_channel_code='-1' where parent_channel_code is null;
update `mto_channel` set channel_code=id where channel_code is null;

-- 增加博文标示
alter table `mto_post` add column article_blog_id varchar(64) NOT NULL DEFAULT '' COMMENT '文章扩展ID' after `id`;
ALTER TABLE `mto_post` ADD UNIQUE (article_blog_id);

alter table `mto_comment` add column uid varchar(256) COMMENT 'uid' after author_id;
alter table `mto_comment` add column commit_authored_type TINYINT NOT NULL DEFAULT 1 COMMENT '是否为鉴权用户。 1 为认证用户(默认)， 0为匿名用户' after `post_id`;
alter table `mto_comment` add column client_ip varchar(16) COMMENT '客户端ip' after `status`;
alter table `mto_comment` add column client_agent varchar(256) COMMENT '客户端agent' after `client_ip`;

alter table `mto_post` add column uid varchar(256) COMMENT 'uid' after author_id;
update `mto_post` set uid=author_id where uid is null;

--  ok


