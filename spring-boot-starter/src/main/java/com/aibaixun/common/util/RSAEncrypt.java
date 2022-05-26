package com.aibaixun.common.util;

import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class RSAEncrypt {

    public RSAEncrypt() {
    }

    public static String encrypt(String data, String publicKeyStr) throws Exception {
        PublicKey publicKey = getPublicKey(publicKeyStr);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, publicKey);
        byte[] source = cipher.doFinal(data.getBytes());
        String encryptStr = Base64Utils.encodeToString(source);
        return encryptStr;
    }

    private static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64Utils.decode(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    private static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] buffer = Base64Utils.decode(privateKeyStr.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey)keyFactory.generatePrivate(keySpec);
    }

    private static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, privateKey);
        byte[] output = cipher.doFinal(cipherData);
        return output;
    }

    public static String getRealString(String data, String privateKey) {
        String result = "";
        RSAPrivateKey key = null;

        try {
            key = loadPrivateKey(privateKey);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        try {
            byte[] plainText = decrypt(key, Base64.getDecoder().decode(data));
            result = new String(plainText, StandardCharsets.UTF_8);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return result;
    }
}
