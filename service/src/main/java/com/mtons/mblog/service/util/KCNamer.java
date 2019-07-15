package com.mtons.mblog.service.util;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Iterator;

/**
 * 随机生成中文汉字
 *
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/7/15 下午4:21
 */
public class KCNamer {
    /**
     *  随机产生常用汉字
     *
     * @param count 要产生汉字的个数
     * @return 常用汉字
     *
     */
    public static String getChineseWord(int count) {
        HashSet<String> set = new HashSet<String>();

        for (int i = 0; i < count; i++) {
            String chineseName = getRandomJianHan(1);
            if (!set.contains(chineseName)) {
                set.add(chineseName);
            }
        }

        StringBuilder sb = new StringBuilder();
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
        }

        return sb.toString();
    }

    private static String getRandomJianHan(int len) {
        String ret = "";
        for (int i = 0; i < len; i++) {
            String str = null;
            int hightPos, lowPos; // 定义高低位

            SecureRandom random = new SecureRandom();
            hightPos = (176 + Math.abs(random.nextInt(39))); // 获取高位值
            lowPos = (161 + Math.abs(random.nextInt(93))); // 获取低位值
            byte[] b = new byte[2];
            b[0] = (new Integer(hightPos).byteValue());
            b[1] = (new Integer(lowPos).byteValue());
            try {
                str = new String(b, "GBK"); // 转成中文
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            ret += str;
        }
        return ret;
    }

}
