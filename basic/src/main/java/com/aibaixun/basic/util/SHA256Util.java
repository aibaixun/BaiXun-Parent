package com.aibaixun.basic.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class SHA256Util {

    public SHA256Util() {
    }

    public static String getSHA256StrJava(String timeStamp, String token, String passId, String collection) {
        return getSHA256StrJava(timeStamp + token + passId + collection);
    }

    public static String getSHA256StrJava(String str) {
        String encodeStr = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        }

        return encodeStr;
    }

    public static String getSHA512Encrypt(String str) {
        String encodeStr = "";

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        }

        return encodeStr;
    }

    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();
        String temp = null;

        for(int i = 0; i < bytes.length; ++i) {
            temp = Integer.toHexString(bytes[i] & 255);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }

            stringBuffer.append(temp);
        }

        return stringBuffer.toString();
    }
}
