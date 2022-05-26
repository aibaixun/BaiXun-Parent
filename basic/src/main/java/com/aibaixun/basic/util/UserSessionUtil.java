package com.aibaixun.basic.util;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class UserSessionUtil {
    private static final ThreadLocal<String> CURRENT_SESSION_ID = new ThreadLocal();

    public UserSessionUtil() {
    }

    public static void removeCurrentSession() {
        CURRENT_SESSION_ID.remove();
    }

    public static void setCurrentSession(String sessionId) {
        CURRENT_SESSION_ID.set(sessionId);
    }

    public static String getCurrentSession() {
        return (String)CURRENT_SESSION_ID.get();
    }
}
