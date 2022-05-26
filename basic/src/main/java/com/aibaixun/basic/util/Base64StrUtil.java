package com.aibaixun.basic.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class Base64StrUtil {

    public Base64StrUtil() {
    }

    public static String string2Base64(byte[] bytes) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(bytes);
    }

    public static String string2Base64(String str) {
        return string2Base64(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String decodeBase64String(String base64Str) {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(base64Str), StandardCharsets.UTF_8);
    }

    public static byte[] decodeBase64Bytes(String base64Str) {
        Base64.Decoder decoder = Base64.getDecoder();
        return decoder.decode(base64Str);
    }
}
