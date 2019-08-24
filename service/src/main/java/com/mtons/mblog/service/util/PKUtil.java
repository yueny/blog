package com.mtons.mblog.service.util;

/**
 * 主键有效性工具类
 *
 * @Author yueny09 <deep_blue_yang@126.com>
 * @Date 2019-08-23 10:26
 */
public class PKUtil {
    /**
     * 主键数据是否有效
     *
     * @param id 主键数据
     * @return true 表示数据有效
     */
    public static boolean available(Long id) {
        if(id != null && id >0){
            return true;
        }

        // 为 null 或者 小于等于 0
        return false;
    }
}
