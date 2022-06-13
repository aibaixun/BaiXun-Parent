package com.aibaixun.common.autoconfig;

import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.util.UserSessionUtil;
import com.aibaixun.common.mybatisplus.BxtTenantLineInterceptor;
import com.aibaixun.common.mybatisplus.MybatisPlusAutoConfigProperties;
import com.aibaixun.common.mybatisplus.TenantProperties;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Column;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Autowired
    public void setProperties(MybatisPlusAutoConfigProperties properties) {
        this.properties = properties;
        this.tenantProperties = properties.getTenant();
    }



    /**
     * 分页插件，自动识别数据库类型
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        var tenant = properties.getTenant();
        if (null != tenant && tenant.getEnable()) {
            interceptor.addInnerInterceptor(new BxtTenantLineInterceptor(new TenantLineHandler() {
                @Override
                public Expression getTenantId() {
                    try {
                        return new StringValue(UserSessionUtil.getCurrentSessionTid());
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
            }, tenant.getIgnoreMapperStatement()));
        }
        if (null != properties.getDbType()){
            interceptor.addInnerInterceptor(new PaginationInnerInterceptor(properties.getDbType()));
        }
        return interceptor;
    }


}
