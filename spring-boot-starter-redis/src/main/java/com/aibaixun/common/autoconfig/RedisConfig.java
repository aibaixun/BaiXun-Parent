package com.aibaixun.common.autoconfig;

import com.aibaixun.common.redis.config.CacheManagerProperties;
import com.aibaixun.common.redis.util.RedisRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.aibaixun.common.redis.Constants.OBJECT_SERIALIZER;
import static com.aibaixun.common.redis.Constants.STRING_SERIALIZER;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/1
 */
@EnableCaching
@ConditionalOnClass(RedisRepository.class)
@EnableConfigurationProperties({RedisProperties.class, CacheManagerProperties.class})
public class RedisConfig implements InitializingBean {


    private CacheManagerProperties cacheManagerProperties;


    private RedisTemplate<String,Object> redisTemplate;


    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setCacheManagerProperties(CacheManagerProperties cacheManagerProperties) {
        this.cacheManagerProperties = cacheManagerProperties;
    }

    @Bean
    @ConditionalOnMissingBean(name = {
            "redisTemplate"
    })
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        return redisTemplate;
    }


    @Bean
    @ConditionalOnMissingBean
    public RedisRepository redisRepository(RedisTemplate<String, Object> redisTemplate) {
        return new RedisRepository(redisTemplate);
    }

    @Bean(name = "cacheManager")
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration difConf = getDefConf().entryTtl(Duration.ofHours(1));
        int configSize = cacheManagerProperties.getCacheManages() == null ? 0 : cacheManagerProperties.getCacheManages().size();
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>(configSize);
        if (configSize > 0) {
            cacheManagerProperties.getCacheManages().forEach(e -> {
                RedisCacheConfiguration conf = getDefConf().entryTtl(Duration.ofSeconds(e.getSecond()));
                redisCacheConfigurationMap.put(e.getKey(), conf);
            });
        }
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(difConf)
                .withInitialCacheConfigurations(redisCacheConfigurationMap)
                .build();
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":").append(method.getName()).append(":");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    private RedisCacheConfiguration getDefConf() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .computePrefixWith(cacheName -> "cache".concat(":").concat(cacheName).concat(":"))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()));
    }

    @Override
    public void afterPropertiesSet() {
        redisTemplate.setKeySerializer(STRING_SERIALIZER);
        redisTemplate.setHashKeySerializer(STRING_SERIALIZER);
        redisTemplate.setValueSerializer(OBJECT_SERIALIZER);
        redisTemplate.afterPropertiesSet();
    }
}
