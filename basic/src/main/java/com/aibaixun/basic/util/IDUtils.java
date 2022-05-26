package com.aibaixun.basic.util;

import java.util.UUID;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class IDUtils {

    private IDUtils() {
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }
}
