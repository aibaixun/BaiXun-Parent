package com.aibaixun.basic.util;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

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


    public static Long randomLongId() {
        return ThreadLocalRandom.current().nextLong(2<<10, 2L <<30);
    }
}
