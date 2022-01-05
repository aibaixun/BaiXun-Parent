package com.aibaixun.common.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangxiao@aibaixun.com
 * @date 2021/12/30
 */

@ConfigurationProperties(prefix = "bx.mybatis-plus")
public class MybatisPlusAutoConfigProperties {


    private TenantProperties tenant;


    private AutoFillProperties fill;


    private DbType dbType;


    public TenantProperties getTenant() {
        return tenant;
    }

    public void setTenant(TenantProperties tenant) {
        this.tenant = tenant;
    }

    public AutoFillProperties getFill() {
        return fill;
    }

    public void setFill(AutoFillProperties fill) {
        this.fill = fill;
    }

    public DbType getDbType() {
        return dbType;
    }

    public void setDbType(DbType dbType) {
        this.dbType = dbType;
    }
}
