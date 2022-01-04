package com.aibaixun.common.config;


import com.aibaixun.basic.context.UserContextHolder;
import com.aibaixun.basic.exception.BaseException;
import feign.RequestInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/1
 */
public class FeignInterceptorConfig {

    private final Logger logger = LoggerFactory.getLogger(FeignInterceptorConfig.class);

    private final String tenantId = "tenantId";

    private final String userId = "userId";

    private final String userLabel ="userLabel";

    @Value("${spring.application.name}")
    private String restClient;

    @Bean
    public RequestInterceptor baseFeignInterceptor() {
        return template -> {
            try {
                var tenant = UserContextHolder.getTenantId();
                var user = UserContextHolder.getUserId();
                var label = UserContextHolder.getUserLabel();
                template.header(tenantId, tenant);
                template.header(userId, user);
                template.header(userLabel, label);
                template.header("restClient",restClient);
            }catch (BaseException baseException){
                logger.warn("feign interceptor is get userInfo is null");
            }
        };
    }
}
