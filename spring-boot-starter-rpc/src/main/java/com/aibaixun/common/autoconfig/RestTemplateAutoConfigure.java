package com.aibaixun.common.autoconfig;


import com.aibaixun.common.config.RestTemplateProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author mall
 * @date 2018/11/17
 */
@Configuration
@EnableConfigurationProperties(RestTemplateProperties.class)
public class RestTemplateAutoConfigure {


    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
