package com.mtons.mblog.base.consts;

/**
 * Options keys 常量
 *
 * 对应表 mto_options 中的 key_
 *
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/6/20 下午8:12
 */
public interface OptionsKeysConsts {
    /**
     * 第三方回调配置
     */
    String QQ_CALLBACK = "qq_callback"; // 第三方登录-QQ回调地址
    String QQ_APP_ID = "qq_app_id";			// QQ互联APP_ID
    String QQ_APP_KEY = "qq_app_key";		// QQ互联APP_KEY

    String WEIBO_CALLBACK = "weibo_callback"; // 第三方登录-微博回调地址
    String WEIBO_CLIENT_ID = "weibo_client_id";		// 微博应用CLIENT_ID
    String WEIBO_CLIENT_SERCRET = "weibo_client_sercret";	// 微博应用CLIENT_SERCRET

    String DOUBAN_CALLBACK = "douban_callback";	// 第三方登录-豆瓣回调地址
    String DOUBAN_API_KEY = "douban_api_key";		// 豆瓣API_KEY
    String DOUBAN_SECRET_KEY = "douban_secret_key";		// 豆瓣SECRET_KEY

    String GITHUB_CALLBACK = "github_callback";	// 第三方登录-github回调地址
    String GITHUB_CLIENT_ID = "github_client_id";//github应用CLIENT_ID
    String GITHUB_SECRET_KEY = "github_secret_key";//github应用SECRET_KEY

    /**
     * 站点名称
     */
    String SITE_NAME = "site_name";
    /**
     * 站点域名
     */
    String SITE_DOMAIN = "site_domain";
    /**
     * 站点关键字
     */
    String SITE_KEYWORDS = "site_keywords";
    /**
     * 站点描述
     */
    String SITE_DESCRIPTION = "site_description";
    /**
     * 站点 site_metas
     */
    String SITE_METAS = "site_metas";
    /**
     * 站点 site_copyright
     */
    String SITE_COPYRIGHT = "site_copyright";
    /**
     * 站点域名 ICP
     */
    String SITE_ICP = "site_icp";
    /**
     * 编辑器类型
     */
    String EDITOR = "editor";
    /**
     * 站点 favicon
     */
    String SITE_FAVICON = "site_favicon";
    /**
     * 站点主题 theme
     */
    String THEME = "theme";
    /**
     * 配置邮箱密码
     */
    String MAIL_SMTP_PASSWORD = "mail_smtp_password";
    /**
     * 配置邮箱用户名
     */
    String MAIL_SMTP_USERNAME = "mail_smtp_username";
    /**
     * 配置邮箱host
     */
    String MAIL_SMTP_HOST = "mail_smtp_host";
    /**
     * mail_pw_ps 不为null, 则 decrypt="true"
     */
    String MAIL_PW_PS = "mail_pw_ps";
    /**
     * 又拍云或oss密码
     */
    String UPYUN_OSS_PASSWORD = "upyun_oss_password";
    /**
     * 又拍云或oss src
     */
    String UPYUN_OSS_SRC = "upyun_oss_src";
    /**
     * 又拍云或oss upyun_oss_operator
     */
    String UPYUN_OSS_OPERATOR = "upyun_oss_operator";
    /**
     * 又拍云或oss 域名
     */
    String UPYUN_OSS_DOMAIN = "upyun_oss_domain";
    /**
     * 又拍云或oss upyun_oss_bucket
     */
    String UPYUN_OSS_BUCKET = "upyun_oss_bucket";
    /**
     * qiniu_oss_src
     */
    String QINIU_OSS_SRC = "qiniu_oss_src";
    /**
     * qiniu_oss_domain
     */
    String QINIU_OSS_DOMAIN = "qiniu_oss_domain";
    /**
     * qiniu_oss_key
     */
    String QINIU_OSS_KEY = "qiniu_oss_key";
    /**
     * qiniu_oss_bucket
     */
    String QINIU_OSS_BUCKET = "qiniu_oss_bucket";
    /**
     * qiniu_oss_secret
     */
    String QINIU_OSS_SECRET = "qiniu_oss_secret";
    /**
     * 图片存储路径。 如 /Users/yueny/workspace/yueny09/github/mblog/blog/uploads
     */
    String IMAGE_SERVER_LOCATION = "image_server_location";
    /**
     * 图片存储相对路径， 如 blog/uploads
     */
    String IMAGE_SERVER_URI = "image_server_uri";

    /**
     * 图片存储方式， 默认  本地 native
     */
    String STORAGE_SCHEME = "storage_scheme";

    /**
     * 默认用户头像路径
     */
    String USER_AVATAR_DEFAULT_PATH = "user_avatar_default_path";

    //aliyun_oss_endpoint
    // aliyun_oss_bucket
    //aliyun_oss_src
    //aliyun_oss_key
    //aliyun_oss_secret
    //



}
