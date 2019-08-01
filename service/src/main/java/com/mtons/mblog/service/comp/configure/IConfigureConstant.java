package com.mtons.mblog.service.comp.configure;

/**
 * 配置中心配置动态获取
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/7/8 8:55
 */
public interface IConfigureConstant {
	///////////////////////////////////////////////////////
	String SITE_VERSION_KEY = "site.version";

	///////////////////////////////////////////////////////
	/**
	 *  controls 的一系列配置 site.controls.*
	 */
	/* 注册开关 */
	String SITE_CONTROLS_REGISTER_KEY = "site.controls.register";
	/*  登陆开关是否显示 */
	String SITE_CONTROLS_LOGIN_SHOW_KEY = "site.controls.login_show";
	/* 注册开启邮箱验证，配置中心配置 */
	String SITE_CONTROLS_REGISTER_EMAIL_VALIDATE_KEY = "site.controls.register_email_validate";
	/* 发布文章开关 */
	String SITE_CONTROLS_POST_KEY = "site.controls.post";
	/* 评论开关, true 为允许评论 */
	String SITE_CONTROLS_COMMENT_KEY = "site.controls.comment";
	/* 是否允许匿名评论开关, true 为允许匿名评论 */
	String SITE_CONTROLS_COMMENT_ALLOW_ANONYMOUS_KEY = "site.controls.comment.allow.anonymous";

	///////////////////////////////////////////////////////
	/**
	 *  default 的一系列配置 site.settings.*
	 */
	/* 用户的默认头像 */
	String SITE_SETTINGS_USERAVATAR_KEY = "site.settings.userAvatar";


	///////////////////////////////////////////////////////
	/**
	 *  security系列配置 security.ip.*
	 */
	/* 公用白名单 */
	String SECURITY_IP_WHITE_KEY = "security.ip.white";
	/* 攻击行为地址标签 */
	String SECURITY_ATTACK_URL_KEY = "security.attack.url";


	///////////////////////////////////////////////////////
	/**
	 *  应用配置
	 */
	/* 验证码连续发送频繁的强制间隔时间， 单位秒 */
	String CODE_FREQUENCE_BLOCK_SECOND_KEY = "code.frequence.block.second";
	/* 验证码连续发送次数 */
	String CODE_FREQUENCE_MAX_COUNT_KEY = "code.frequence.max.count";

	///////////////////////////////////////////////////////
	/* 每日箴言 */
	String SITE_TALKER_KEY = "site.talker";
	/* 是否显示时钟 */
	String SITE_SHOW_LOCKER_KEY = "site.show.locker";
}
