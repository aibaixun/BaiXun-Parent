package com.aibaixun.snowflake;

import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class IndexScript {
    public static final String GET_INDEX = "local index= redis.call('BITPOS', KEYS[1], 0) if index >1023 then    redis.replicate_commands();    local a=redis.call('TIME');    local time =a[1];    local result = redis.call('Zrangebyscore', KEYS[2], 0, time);     for i=1,#result do        redis.call('ZREM', KEYS[2], result[i]);        redis.call('setbit', KEYS[1], result[i],0);        end;    local index= redis.call('BITPOS', KEYS[1], 0);    if index >1023 then        return -1;    end;    redis.call('setbit', KEYS[1], index,1);    return index;else    redis.call('setbit', KEYS[1], index,1);    return index;end;";
    public static final String WATCH_DOG = "redis.replicate_commands();local a=redis.call('TIME');local time =a[1]+tonumber(ARGV[1]);return redis.call('zadd', KEYS[1],time, tonumber(ARGV[2]));";
    private final DefaultRedisScript<Long> index;
    private final DefaultRedisScript<Long> watch;

    private IndexScript(DefaultRedisScript<Long> index, DefaultRedisScript<Long> watch) {
        this.index = index;
        this.watch = watch;
    }

    public DefaultRedisScript<Long> getIndexScript() {
        return this.index;
    }

    public DefaultRedisScript<Long> getWatchScript() {
        return this.watch;
    }

    public static IndexScript instance() {
        return IndexScript.IndexScriptHolder.INDEX_SCRIPT;
    }

    private static class IndexScriptHolder {
        public static final IndexScript INDEX_SCRIPT = new IndexScript(new DefaultRedisScript("local index= redis.call('BITPOS', KEYS[1], 0) if index >1023 then    redis.replicate_commands();    local a=redis.call('TIME');    local time =a[1];    local result = redis.call('Zrangebyscore', KEYS[2], 0, time);     for i=1,#result do        redis.call('ZREM', KEYS[2], result[i]);        redis.call('setbit', KEYS[1], result[i],0);        end;    local index= redis.call('BITPOS', KEYS[1], 0);    if index >1023 then        return -1;    end;    redis.call('setbit', KEYS[1], index,1);    return index;else    redis.call('setbit', KEYS[1], index,1);    return index;end;", Long.class), new DefaultRedisScript("redis.replicate_commands();local a=redis.call('TIME');local time =a[1]+tonumber(ARGV[1]);return redis.call('zadd', KEYS[1],time, tonumber(ARGV[2]));", Long.class));

        private IndexScriptHolder() {
        }
    }
}
