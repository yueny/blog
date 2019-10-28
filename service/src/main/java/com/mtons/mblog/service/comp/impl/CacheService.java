package com.mtons.mblog.service.comp.impl;

import com.mtons.mblog.service.comp.ICacheService;
import lombok.Getter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 *
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/6/13 上午12:41
 */
@Component
public class CacheService implements ICacheService {
    @Resource(name = "redisTemplate")
    @Getter
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "stringRedisTemplate")
    @Getter
    private StringRedisTemplate stringRedisTemplate;

}
