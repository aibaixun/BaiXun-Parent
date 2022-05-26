package com.aibaixun.common.util;

import org.springframework.util.DigestUtils;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class Md5Util {

    public Md5Util() {
    }

    public static String getMd5Sign(String time, String rand, String key, String secVal) {
        String str = time + rand + key + secVal;
        String result = DigestUtils.md5DigestAsHex(str.getBytes());
        return result;
    }



    public static String getNowTimeStamp() {
        long time = System.currentTimeMillis();
        return String.valueOf(time / 1000L);
    }
}
