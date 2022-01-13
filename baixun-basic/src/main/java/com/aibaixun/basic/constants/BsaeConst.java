package com.aibaixun.basic.constants;

/**
 * @author crj
 * @description: 全局常量类
 * @date 2022/1/1310:06
 */
public class BsaeConst {

    /**
     * 从请求HEADER中获取token的前缀
     */
    public static final String TOKEN_HEADER_NAME = "authorization";
    /**
     * 从redis中获取token的前缀
     */
    public static final String TOKEN_REDIS_PREFIX = "gail-auth:";

    public static final String USERID_REDIS_PREFIX = "gail-userId:";

    public static final String USERID_HEADER_NAME = "userId";
}
