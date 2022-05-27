package com.aibaixun.basic.jwt;


import com.aibaixun.basic.util.IDUtils;
import com.aibaixun.basic.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

/**
 * @author wang xiao
 * @date 2022/5/27
 */
public class JwtUtil {


    private final static String DEFAULT_SECRET = "BX@ZH123654!";

    private final static String ISSUER = "bx";


    public final static String DEFAULT_USER_ID = "uid";
    public final static String DEFAULT_TENANT_ID = "tid";


    public static String encode(String secret, long expireTime, String k1, String v1,String k2,String v2) {
        return encode(secret,Map.of(k1,v1,k2,v2),expireTime);
    }

    public static String encode(String secret, Map<String,String> kv, long expireTime) {
        if (secret == null || secret.length() < 1) {
            secret = DEFAULT_SECRET;
        }
        Date expDate = null;
        if (expireTime > 1) {
            expDate = new Date(System.currentTimeMillis() + expireTime);
        }
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTCreator.Builder builder = JWT.create()
                .withIssuer(ISSUER)
                .withJWTId(String.valueOf(IDUtils.randomLongId()))
                .withExpiresAt(expDate);
        if (ObjectUtil.isNotNull(kv)){
            for (Map.Entry<String, String> entry : kv.entrySet()) {
                builder.withClaim(entry.getKey(),entry.getValue());
            }
        }
        return builder.sign(algorithm);
    }

    public static String decode(String key, String encryptedToken) {
        return decode(null, key, encryptedToken);
    }


    public static String decode(String secret, String key, String encryptedToken) {
        JWTVerifier verifier = getJwtVerifier(secret);
        return verifier.verify(encryptedToken).getClaim(key).asString();
    }

    public static String getUid(String secret,  String encryptedToken) {
        return decode(secret,DEFAULT_USER_ID,encryptedToken);
    }

    public static String getTid(String secret,  String encryptedToken) {
        return decode(secret,DEFAULT_TENANT_ID,encryptedToken);
    }

    public static String getJid(String secret,  String encryptedToken) {
        JWTVerifier verifier = getJwtVerifier(secret);
        return verifier.verify(encryptedToken).getId();
    }

    private static JWTVerifier getJwtVerifier(String secret) {
        if ("".equals(secret) || null == secret) {
            secret = DEFAULT_SECRET;
        }
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
        return verifier;
    }






}
