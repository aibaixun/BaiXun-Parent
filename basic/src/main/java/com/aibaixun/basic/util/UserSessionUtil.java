package com.aibaixun.basic.util;

import com.aibaixun.basic.entity.AuthUserInfo;
import com.aibaixun.basic.entity.UserInfo;

import java.util.Optional;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class UserSessionUtil {
    private static final ThreadLocal<UserInfo> CURRENT_SESSION_ID = new InheritableThreadLocal<>();

    public static String NIL_STR = "nil";

    private UserSessionUtil() {
    }

    public static void removeCurrentSession() {
        CURRENT_SESSION_ID.remove();
    }

    public static void setCurrentSession(String uid,String tid) {
        CURRENT_SESSION_ID.set(new AuthUserInfo(uid,tid));
    }

    public static UserInfo getCurrentSession() {
        return CURRENT_SESSION_ID.get();
    }

    public static String getCurrentSessionUid() {
        return Optional.ofNullable(CURRENT_SESSION_ID.get()).map(UserInfo::getUserId).orElse(NIL_STR);
    }

    public static String getCurrentSessionTid() {
        return Optional.ofNullable(CURRENT_SESSION_ID.get()).map(UserInfo::getTenantId).orElse(NIL_STR);
    }
}
