package com.aibaixun.basic.entity;

/**
 * @author wangxiao@aibaixun.com
 * @date 2021/12/31
 */
public interface UserInfo {

    /**
     * 获取 用户id
     * @return String
     */
    String getUserId();

    /**
     * 用户 租户id
     * @return String
     */
    String getTenantId();

}
