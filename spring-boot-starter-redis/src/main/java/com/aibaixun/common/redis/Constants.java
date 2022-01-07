package com.aibaixun.common.redis;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/7
 */
public class Constants {

    public static final RedisSerializer<Object> OBJECT_SERIALIZER = new JdkSerializationRedisSerializer();
    public static final RedisSerializer<String> STRING_SERIALIZER = new StringRedisSerializer();
}
