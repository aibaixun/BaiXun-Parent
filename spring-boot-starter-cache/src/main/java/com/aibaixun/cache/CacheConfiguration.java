package com.aibaixun.cache;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
@Configuration
@ConditionalOnClass({StringRedisTemplate.class})
@AutoConfigureAfter({RedisAutoConfiguration.class})
@EnableConfigurationProperties({CacheProperties.class})
@ConditionalOnProperty(
        prefix = "bx.cache",
        value = {"enable"},
        havingValue = "true"
)
public class CacheConfiguration {

    @ConditionalOnMissingBean
    @Bean
    public BxCache bxCache(StringRedisTemplate template, CacheProperties properties) {
        return new BxCache(template, properties);
    }
}
