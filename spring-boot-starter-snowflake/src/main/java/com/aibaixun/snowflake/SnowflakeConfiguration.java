package com.aibaixun.snowflake;

import com.aibaixun.snowflake.config.SnowProperties;
import com.aibaixun.snowflake.util.StringUtils;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
@Configuration
@ConditionalOnClass({StringRedisTemplate.class})
@EnableConfigurationProperties({SnowProperties.class})
@ConditionalOnProperty(
        prefix = "bx.snow",
        value = {"enable"},
        matchIfMissing = true,
        havingValue = "true"
)
public class SnowflakeConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SnowflakeConfiguration.class);
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    public SnowflakeConfiguration() {
    }

    @Bean
    @ConditionalOnClass({IdentifierGenerator.class})
    @ConditionalOnMissingBean
    @DependsOn({"stringRedisTemplate"})
    IdentifierGenerator identifierGenerator(@Value("${spring.application.name:}") String prefix, SnowProperties snowProperties, StringRedisTemplate redisTemplate) {
        if (snowProperties.getWatchDogExpireTime() < snowProperties.getWatchDogScheduleTime()) {
            throw new IllegalArgumentException("刷新key的时间间隔必须小于key的过期时间");
        } else if (StringUtils.isEmpty(prefix)) {
            throw new IllegalArgumentException("spring.application.name 必填");
        } else {
            Long index = redisTemplate.execute(IndexScript.instance().getIndexScript(), Arrays.asList(StringUtils.appendBrackets(prefix) + ":bitmap", StringUtils.appendBrackets(prefix) + ":bitset"), new Object[0]);
            if (index != null && index != -1L) {
                logger.info("当前服务获取的index为：{}", index);
                String bit = StringUtils.leftPad(Long.toBinaryString(index), 10, "0");
                int dataCenterId = Integer.valueOf(bit.substring(0, 5), 2);
                int workerId = Integer.valueOf(bit.substring(5, 10), 2);
                logger.info("workerId：{}，dataCenterId：{}", workerId, dataCenterId);
                this.executor.scheduleWithFixedDelay(() -> {
                    try {
                        redisTemplate.execute(IndexScript.instance().getWatchScript(), Collections.singletonList(StringUtils.appendBrackets(prefix) + ":bitset"), new Object[]{String.valueOf(snowProperties.getWatchDogExpireTime()), String.valueOf(index)});
                    } catch (Throwable e) {
                        logger.warn("看门狗出问题了", e);
                    }

                }, 0L, snowProperties.getWatchDogScheduleTime(), TimeUnit.SECONDS);
                return new DefaultIdentifierGenerator((long)workerId, (long)dataCenterId);
            } else {
                throw new IllegalArgumentException("centerId和 dataId 10bit耗尽 或者其他异常");
            }
        }
    }
}
