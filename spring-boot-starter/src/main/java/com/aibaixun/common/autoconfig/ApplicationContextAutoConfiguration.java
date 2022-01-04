package com.aibaixun.common.autoconfig;

import com.aibaixun.common.factory.ApplicationContextHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangxiao@aibaixun.com
 * @date 2021/12/31
 */
@Configuration
public class ApplicationContextAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ApplicationContextHelper.class)
    public ApplicationContextHelper applicationContextHelper() {
        return new ApplicationContextHelper();
    }
}
