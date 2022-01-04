package com.aibaixun.common.autoconfig;

import com.aibaixun.common.SwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wangxiao@aibaixun.com
 * @date 2021/12/31
 */
@Configuration
@EnableSwagger2
@Import({
        SwaggerProperties.class
})
public class SwaggerAutoConfiguration {


    private SwaggerProperties swaggerProperties;

    @Bean
    @ConditionalOnProperty(prefix = "bx.swagger",value = "enable",havingValue = "true")
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                .apis(null == swaggerProperties.getBasePackage()?RequestHandlerSelectors.any():RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(null == swaggerProperties.getPath()? PathSelectors.any():PathSelectors.ant(swaggerProperties.getPath()))
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(String.format("%s API DOCS",swaggerProperties.getApplicationName()))
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .build();
    }

    @Autowired
    public void setSwaggerProperties(SwaggerProperties swaggerProperties) {
        this.swaggerProperties = swaggerProperties;
    }
}
