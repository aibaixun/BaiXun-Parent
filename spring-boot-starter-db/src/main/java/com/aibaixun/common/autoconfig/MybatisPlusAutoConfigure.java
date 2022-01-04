package com.aibaixun.common.autoconfig;

import com.aibaixun.basic.context.UserContextHolder;
import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.common.mybatisplus.*;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.SqlInfo;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Column;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wangxiao@aibaixun.com
 * @date 2021/12/30
 */

@Configuration
@EnableConfigurationProperties(MybatisPlusAutoConfigProperties.class)
public class MybatisPlusAutoConfigure {


    private MybatisPlusAutoConfigProperties properties;

    private TenantProperties tenantProperties;

    private AutoFillProperties autoFillProperties;

    @Autowired
    public void setProperties(MybatisPlusAutoConfigProperties properties) {
        this.properties = properties;
        this.tenantProperties = properties.getTenant();
        this.autoFillProperties = properties.getFill();
    }



    private TenantLineHandler tenantLineHandler;

    @Autowired
    public void setTenantLineHandler(TenantLineHandler tenantLineHandler) {
        this.tenantLineHandler = tenantLineHandler;
    }

    /**
     * 分页插件，自动识别数据库类型
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        var tenant = properties.getTenant();
        if (null != tenant && tenant.getEnable()) {
            interceptor.addInnerInterceptor(new BxtTenantLineInterceptor(tenantLineHandler,tenant.getIgnoreMapperStatement()));
        }
        if (null != properties.getDbType()){
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(properties.getDbType()));
        }
        return interceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "bx.mybatis-plus.fill", name = "enable", havingValue = "true", matchIfMissing = true)
    public MetaObjectHandler metaObjectHandler() {
        return new DateMetaObjectHandler(autoFillProperties);
    }



    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "bx.mybatis-plus.tenant",name = "enable", havingValue = "true")
    public TenantLineHandler tenantLineHandler () {
        return new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                try {
                    return new StringValue(UserContextHolder.getTenantId());
                } catch (BaseException e) {
                    return new NullValue();
                }
            };

            @Override
            public boolean ignoreTable(String tableName) {
                return !tenantProperties.getIgnoreTables().contains(tableName);
            }

            @Override
            public boolean ignoreInsert(List<Column> columns, String tenantIdColumn) {
                return TenantLineHandler.super.ignoreInsert(columns, tenantIdColumn);
            }
        };
    }






}
