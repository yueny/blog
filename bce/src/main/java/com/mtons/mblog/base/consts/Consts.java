/**
 * 
 */
package com.mtons.mblog.base.consts;


import com.mtons.mblog.base.enums.BlogFeaturedType;

/**
 * @author langhsu
 *
 */
public interface Consts {
	/**
	 * 分隔符
	 */
	String SEPARATOR = ",";

	String SEPARATOR_X = "x";

	String ROLE_ADMIN = "admin";

	long ADMIN_ID = 1;

	int PAGE_DEFAULT_SIZE = 10;

	int IDENTITY_STEP = 1; // 自增步进

	int DECREASE_STEP = -1; // 递减

	int TIME_MIN = 1000; // 最小时间单位, 1秒

	// 忽略值
	int IGNORE = -1;

	int ZERO = 0;


	/*  */
	int STATUS_NORMAL = 0;
//	/* 状态-锁定 */
//	int STATUS_LOCKED = 1;
//	int STATUS_HIDDEN = 1;

	/**
	 * 排序
	 */
	interface order {
		String FEATURED = "featured";
		String NEWEST = "newest";
		String HOTTEST = "hottest";
		String FAVOR = "favors";
	}

	int CODE_BIND = 1;   // bind email
	int CODE_FORGOT = 2; // forgot password
	int CODE_REGISTER = 3;

	int CODE_STATUS_INIT = 0;      // 验证码-初始
	int CODE_STATUS_CERTIFIED = 1; // 验证码-已使用

	@Deprecated
	int FEATURED_DEFAULT = BlogFeaturedType.FEATURED_DEFAULT.getValue(); // 推荐状态-默认
	@Deprecated
	int FEATURED_ACTIVE = BlogFeaturedType.FEATURED_ACTIVE.getValue();  // 推荐状态-推荐

	/**
	 * 活跃值
	 */
	int ACTIVE = BlogFeaturedType.FEATURED_ACTIVE.getValue();

	/**
	 * 未读
	 */
	int UNREAD = 0;

	/**
	 * 已读
	 */
	int READED = 1;

	/**
	 * 缓存键， @CacheConfig(cacheNames = Consts.CACHE_POST_MANAGER)
	 */
	String CACHE_USER = "userCaches";
	String CACHE_POST = "postCaches";


	String EMAIL_TEMPLATE_CODE = "email_code.ftl";

	String EDITOR_MARKDOWN = "markdown";

	String STORAGE_LIMIT_SIZE = "storage_limit_size";
	String STORAGE_MAX_WIDTH = "storage_max_width";

	String THUMBNAIL_POST_SIZE = "thumbnail_post_size";
}
