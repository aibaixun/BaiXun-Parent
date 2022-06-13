package com.aibaixun.common.config;


import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.jwt.JwtUtil;
import com.aibaixun.basic.util.UserSessionUtil;
import feign.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/1
 */
public class FeignInterceptorConfig {

    private final Logger logger = LoggerFactory.getLogger(FeignInterceptorConfig.class);



    @Value("${spring.application.name}")
    private String restClient;

    @Bean
    public RequestInterceptor baseFeignInterceptor() {
        return template -> {
            try {
                var tenant = UserSessionUtil.getCurrentSessionTid();
                var user = UserSessionUtil.getCurrentSessionUid();
                template.header(JwtUtil.DEFAULT_USER_ID, user);
                template.header(JwtUtil.DEFAULT_TENANT_ID, tenant);
                template.header("restClient",restClient);
            }catch (BaseException baseException){
                logger.warn("feign interceptor is get userInfo is null");
            }
        };
    }
}
