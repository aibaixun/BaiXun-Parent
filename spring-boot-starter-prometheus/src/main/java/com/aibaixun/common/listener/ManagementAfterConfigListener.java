package com.aibaixun.common.listener;

import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.Ordered;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/7
 */
public class ManagementAfterConfigListener implements SmartApplicationListener, Ordered {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        return ApplicationEnvironmentPreparedEvent.class.isAssignableFrom(aClass) || ApplicationPreparedEvent.class.isAssignableFrom(aClass);

    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ApplicationEnvironmentPreparedEvent) {
            System.setProperty("management.endpoints.web.exposure.include", "prometheus,info,health");
            System.setProperty("management.metrics.tags.application", "${spring.application.name}");
        }

    }

    @Override
    public int getOrder() {
        return ConfigFileApplicationListener.DEFAULT_ORDER + 1;
    }
}
