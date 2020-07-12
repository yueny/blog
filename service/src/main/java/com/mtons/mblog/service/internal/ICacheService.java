package com.mtons.mblog.service.internal;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/6/13 上午12:42
 */
public interface ICacheService {
    /**
     * 默认限流的键
     */
    default String getFrequenceKey(String lkey){
        return "mblog:frequence:" + lkey;
    }

}
