package com.aibaixun.common.mybatisplus;

import java.util.ArrayList;
import java.util.List;

/**
 *  租户
 * @author wangxiao@aibaixun.com
 * @date 2021/12/30
 */
public class TenantProperties {

    /**
     * 是否开启多租户 默认开启
     */
    private Boolean enable = false;

    /**
     * 配置不进行多租户隔离的表名
     */
    private List<String> ignoreTables = new ArrayList<>();

    /**
     * 配置不进行多租户隔离的sql
     * 需要配置mapper的全路径如：com.aibaixun.user.mapper.SysUserMapper.findList
     */
    private List<String> ignoreMapperStatement = new ArrayList<>();


    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<String> getIgnoreTables() {
        return ignoreTables;
    }

    public void setIgnoreTables(List<String> ignoreTables) {
        this.ignoreTables = ignoreTables;
    }

    public List<String> getIgnoreMapperStatement() {
        return ignoreMapperStatement;
    }

    public void setIgnoreMapperStatement(List<String> ignoreMapperStatement) {
        this.ignoreMapperStatement = ignoreMapperStatement;
    }
}
