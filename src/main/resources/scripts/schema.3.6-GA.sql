-- [栏目管理] 新增栏目对外释义列, 用于栏目的链接展示和查询
alter table `mto_channel` add column flag varchar(256) COMMENT '对外释义标识, 一般可以等于关键字' after key_;

update `mto_channel` set flag=key_
where flag is null;

-- [用户信息] 新增个人域名自定义
alter table `mto_user` add column domain_hack varchar(256) COMMENT '用户自定义个性域名' after signature;

update `mto_user` set domain_hack=id
where domain_hack is null;
