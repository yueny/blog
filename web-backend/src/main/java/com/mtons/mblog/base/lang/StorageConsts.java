/**
 * 
 */
package main.java.com.mtons.mblog.base.lang;


/**
 * 存仓有关的静态常量
 */
public interface StorageConsts {
	/**
	 * 文件存储-缩略图目录
	 */
	String thumbnailPath = "/storage/thumbnails";
	/**
	 * 文件存储-发布文章目录. %s为用户uid
	 */
	String blognailPath = "/storage/blognails/%s";

	/**
	 * 文件存储-头像目录
	 */
	String avatarPath = "/storage/avatars/%s";
	/**
	 * 文件存储-不属于上述分类的储存目录
	 */
	String vaguePath = "/storage/vague/%s";

	/**
	 * 默认头像
	 */
	String AVATAR = "https://static.codealy.com/images/default/default-none.jpeg";

	/**
	 * _signature
	 */
	String _signature = "_signature";
}
