package com.aibaixun.snowflake;

import com.aibaixun.snowflake.util.IdWorkerUtil;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
@Configuration
@AutoConfigureAfter({SnowflakeConfiguration.class})
public class IdWorkerConfiguration {
    public IdWorkerConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean
    IdWorkerUtil idWorkerUtil(IdentifierGenerator identifierGenerator) {
        return new IdWorkerUtil(identifierGenerator);
    }

}
