package com.aibaixun.common.annotation;

import java.lang.annotation.*;

/**
 * @author wangxiao@aibaixun.com
 * @date 2021/12/31
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BxLock {
    /**
     * 锁的key
     */
    String key();
    /**
     * 重试次数
     */
    int retryCounter() default 0;
    /**
     * 加锁的时间(单位 {@code unit})
     *
     */
    long expireTime() default -1;

}
