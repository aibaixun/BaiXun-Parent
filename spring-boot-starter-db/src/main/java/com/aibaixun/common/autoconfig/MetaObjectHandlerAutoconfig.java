package com.aibaixun.common.autoconfig;

import com.aibaixun.common.mybatisplus.DateMetaObjectHandler;
import com.aibaixun.common.mybatisplus.MybatisPlusAutoConfigProperties;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/7
 */
@Configuration
@EnableConfigurationProperties(MybatisPlusAutoConfigProperties.class)
@ConditionalOnProperty(prefix = "bx.mybatis-plus.fill", name = "enable", havingValue = "true")
public class MetaObjectHandlerAutoconfig {

    private MybatisPlusAutoConfigProperties properties;

    @Bean
    public MetaObjectHandler metaObjectHandler() {
        return new DateMetaObjectHandler(properties.getFill());
    }

    @Autowired
    public void setProperties(MybatisPlusAutoConfigProperties properties) {
        this.properties = properties;
    }
}
