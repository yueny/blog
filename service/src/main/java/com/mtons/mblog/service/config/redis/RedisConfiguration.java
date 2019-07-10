package com.mtons.mblog.service.config.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;


/**
 * 配置前缀统一为 redis.client.
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2019/6/13 上午12:24
 *
 */
@Configuration
@EnableCaching //开启缓存注解， 注解 @Cacheable、@CacheEvict、@CachePut
public class RedisConfiguration {
    @Value("${redis.client.host}")
    private String host;
    @Value("${redis.client.port}")
    private Integer port;
    @Value("${redis.client.password}")
    private String password;
//    @Value("${spring.redis.database}")
    private Integer db = 1;

    @Value("${redis.client.connectionPoolSize}")
    private Integer connectionPoolSize;
    @Value("${redis.client.connectionTimeout}")
    private Long connectionTimeout;

    @Value("${redis.client.config.maxTotal}")
    private Integer maxTotal;
    @Value("${redis.client.config.maxIdle}")
    private Integer maxIdle;
    @Value("${redis.client.config.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;
    @Value("${redis.client.config.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;
    @Value("${redis.client.config.testOnBorrow}")
    private boolean testOnBorrow;

    @Bean
    @ConfigurationProperties("redis.client")
    public JedisConnectionFactory jedisConnectionFactory(@Qualifier("jedisPoolConfig") JedisPoolConfig jedisPoolConfig){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
        jedisConnectionFactory.setDatabase(this.db);
        jedisConnectionFactory.setHostName(this.host);
        jedisConnectionFactory.setPassword(this.password);
        jedisConnectionFactory.setPort(this.port);
        jedisConnectionFactory.setTimeout(connectionTimeout.intValue());

        return jedisConnectionFactory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(@Qualifier("jedisConnectionFactory") RedisConnectionFactory factory){
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(com.fasterxml.jackson.annotation.PropertyAccessor.ALL,
                com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);

        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(redisSerializer);
        template.setHashKeySerializer(redisSerializer);

        template.afterPropertiesSet();

        return template;
    }
    @Bean
    public StringRedisTemplate stringRedisTemplate(@Qualifier("jedisConnectionFactory") RedisConnectionFactory factory){
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);

        // 此处是为了满足  getStringRedisTemplate().<String,RetryVo>opsForHash().putIfAbsent 的情况 @袁洋
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
                Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(com.fasterxml.jackson.annotation.PropertyAccessor.ALL,
                com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);

        stringRedisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        return stringRedisTemplate;
    }

    @Bean
    @ConfigurationProperties("redis.client.config")
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        jedisPoolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        jedisPoolConfig.setTestOnBorrow(testOnBorrow);

        return jedisPoolConfig;
    }

}
