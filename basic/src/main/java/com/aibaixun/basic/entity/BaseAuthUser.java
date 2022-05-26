package com.aibaixun.basic.entity;

import java.io.Serializable;
import java.util.Set;

public class BaseAuthUser implements UserInfo, Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String username;
    private String tenantId;
    private String type;
    //token到期时间
    private Long tokenExpired;
    //刷新token到期时间
    private Long reflashTokenExpired;
    private Set<String> roleIds;

    @Override
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 用户名称
     */
    @Override
    public String getUserLabel() {
        return username;
    }

    public void setUserLabel(String userLabel) {
        username = userLabel;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(Long tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    public Long getReflashTokenExpired() {
        return reflashTokenExpired;
    }

    public void setReflashTokenExpired(Long reflashTokenExpired) {
        this.reflashTokenExpired = reflashTokenExpired;
    }

    public Set<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Set<String> roleIds) {
        this.roleIds = roleIds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
