package com.aibaixun.common.redis;

import org.springframework.data.redis.serializer.*;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/7
 */
public class Constants {

    public static final RedisSerializer<Object> OBJECT_SERIALIZER = new GenericJackson2JsonRedisSerializer();
    public static final RedisSerializer<String> STRING_SERIALIZER = new StringRedisSerializer();
}
