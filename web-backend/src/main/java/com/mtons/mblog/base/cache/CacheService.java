package main.java.com.mtons.mblog.base.cache;

import lombok.Getter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <code>
 *
 * </code>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2019/6/13 上午12:41
 */
@Component
public class CacheService implements ICacheService{
    @Resource(name = "redisTemplate")
    @Getter
    private RedisTemplate<String, Object> redisTemplate;

    @Resource(name = "stringRedisTemplate")
    @Getter
    private StringRedisTemplate stringRedisTemplate;

}
