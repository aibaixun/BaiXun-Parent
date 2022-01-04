package com.aibaixun.basic.entity;

/**
 * @author wangxiao@aibaixun.com
 * @date 2021/12/31
 */
public interface UserInfo {

    /**
     * 用户id
     */
    String getUserId();

    /**
     * 用户 租户
     */
    String getTenantId();

    /**
     * 用户名称
     */
    String getUserLabel();
}
