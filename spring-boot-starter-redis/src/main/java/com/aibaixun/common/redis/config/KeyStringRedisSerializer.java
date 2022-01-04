package com.aibaixun.common.redis.config;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/4
 */
public class KeyStringRedisSerializer implements RedisSerializer<String> {


    private final String keyPrefix;

    public KeyStringRedisSerializer(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    @Override
    public String deserialize(@Nullable byte[] bytes) {
        return (bytes == null ? null : new String(bytes, StandardCharsets.UTF_8).replaceFirst(keyPrefix, ""));
    }


    @Override
    public byte[] serialize(@Nullable String string) {
        return (string == null ? null : (keyPrefix + string).getBytes(StandardCharsets.UTF_8));
    }
}
