/**
 *
 */
package com.mtons.mblog.service.util.file;

import com.mtons.mblog.base.consts.StorageConsts;
import com.mtons.mblog.service.comp.storage.NailType;
import org.apache.commons.text.RandomStringGenerator;

import java.security.SecureRandom;

/**
 * @author langhsu
 *
 */
public class FilePathUtils {
	private static final int[]  AVATAR_GRIDS = new int[] {3,3,3};
	private static final int    AVATAR_LENGTH = 9;

	private static final String Y = "/yyyy/";
	private static final SecureRandom random = new SecureRandom();
	private static RandomStringGenerator randomString = new RandomStringGenerator.Builder().withinRange('a', 'z').build();


	/**
	 * 得到【用户】的缩略图全路径。
	 *
	 * 规则为 /{/storage/avatars}/{uid}/{getAvatar}.jpg， 如 /storage/avatars/50/50_100_192329124.jpg
	 */
	public static String getUAvatar(String uid, int size) {
		StringBuilder sb = new StringBuilder();

		sb.append(String.format(NailType.PathType.avatar.getPath(), String.valueOf(uid)));
		sb.append(getAvatar(uid, size));
		sb.append(".jpg");

		return sb.toString();
	}

	/**
	 * 得到缩略图名。 规则为 /{key}_{size}_{random}， 如 50_100_25263307
	 */
	private static String getAvatar(String key, int size) {
		StringBuilder sb = new StringBuilder();

		String w = String.format("/%s_%d", key, size);
		sb.append(w);

		sb.append("_");
		// 1000以内的随机数
		sb.append(random.nextInt(1000));
		return sb.toString();
	}

	/**
	 * 生成局部路径和文件名
	 *
	 * @param originalFilename 原始文件名
	 * @param key 新文件名的全局唯一值
	 *
	 * @return 含局部路径（固定）的文件名
	 */
	public static String wholePathName(String originalFilename, String key) {
		StringBuilder builder = new StringBuilder(52);
		builder.append("/"+ StorageConsts._signature+"/");
		builder.append(key);
		builder.append(FileKit.getSuffix(originalFilename));
		return builder.toString();
	}

	/**
	 * 生成路径和文件名
	 *
	 * @param basePath 路径
	 * @param originalFilename 原始文件名
	 * @param key 新文件名的全局唯一值
	 *
	 * @return 含路径（参数输入+规则配置）的文件名
	 */
	public static String wholePathName(String basePath, String originalFilename, String key) {
		return basePath + wholePathName(originalFilename, key);
	}

//	public static void main(String[] args) {
//		String base = FilePathUtils.getAvatar("50", 100);
//		String uAvatar = FilePathUtils.getUAvatar("50", 100);
//
//		String ava100 = String.format(StorageConsts.avatarPath, String.valueOf("50"))
//				+ base + ".jpg";
//		System.out.println(ava100);
//		System.out.println(FilePathUtils.wholePathName("a.jpg", "123"));
//	}

}
