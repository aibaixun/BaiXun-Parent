package com.aibaixun.common.autoconfig;

import com.aibaixun.common.exception.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangxiao@aibaixun.com
 * @date 2021/12/31
 */
@Configuration
public class GlobalExceptionHandlerAutoConfig {
    @Bean
    @ConditionalOnProperty(prefix = "bx.exception",value = "enable",havingValue = "true")
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
