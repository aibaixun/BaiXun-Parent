package com.aibaixun.common.util;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class CurrentSafeTicketUtil {


    private static final ThreadLocal<String> REAL_IP = new ThreadLocal();

    public CurrentSafeTicketUtil() {
    }


    public static void deleteRealIp() {
        REAL_IP.remove();
    }



    public static void setRealIp(String userIp) {
        REAL_IP.set(userIp);
    }

    public static String getRealIp() {
        return (String)REAL_IP.get();
    }
}
