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

--  ok

