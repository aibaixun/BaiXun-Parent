package com.aibaixun.basic.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * 基础用户信息
 * @author huanghaijiang
 * update by wangxiao rename and fix field name
 */
public  class AuthUserInfo implements UserInfo, Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String username;
    private String tenantId;
    private String type;
    /**
     * token到期时间
     */
    private Long tokenExpired;
    /**
     * 刷新token到期时间
     */
    private Long refreshTokenExpired;
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

    public Long getRefreshTokenExpired() {
        return refreshTokenExpired;
    }

    public void setRefreshTokenExpired(Long refreshTokenExpired) {
        this.refreshTokenExpired = refreshTokenExpired;
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

    public AuthUserInfo() {
    }

    public AuthUserInfo(String userId, String tenantId) {
        this.userId = userId;
        this.tenantId = tenantId;
    }
}
