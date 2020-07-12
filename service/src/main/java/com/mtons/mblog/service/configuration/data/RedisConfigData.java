package com.mtons.mblog.service.configuration.data;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 未加载出来  TODO
 */
@Deprecated
@Component
@ConfigurationProperties(prefix = "redis.client", ignoreUnknownFields = true)
public class RedisConfigData extends AbstractMaskBo {
//    @Value("${redis.client.host}")
    @Getter
    @Setter
    private String host;

//    @Value("${redis.client.port}")
    @Getter
    @Setter
    private Integer port;

//    @Value("${redis.client.password}")
    @Getter
    @Setter
    private String password;

//    //    @Value("${spring.redis.database}")
    @Getter
    @Setter
    private Integer db = 1;

//    @Value("${redis.client.connectionPoolSize}")
    @Getter
    @Setter
    private Integer connectionPoolSize;

//    @Value("${redis.client.connectionTimeout}")
    @Getter
    @Setter
    private Long connectionTimeout;

    @Getter
    @Setter
    private Config config;

    @Getter
    @Setter
    public static class Config {
//        @Value("${redis.client.config.maxTotal}")
        private Integer maxTotal;

//        @Value("${redis.client.config.maxIdle}")
        private Integer maxIdle;

//        @Value("${redis.client.config.timeBetweenEvictionRunsMillis}")
        private Long timeBetweenEvictionRunsMillis;

//        @Value("${redis.client.config.minEvictableIdleTimeMillis}")
        private Long minEvictableIdleTimeMillis;

//        @Value("${redis.client.config.testOnBorrow}")
        private boolean testOnBorrow;
    }
}
