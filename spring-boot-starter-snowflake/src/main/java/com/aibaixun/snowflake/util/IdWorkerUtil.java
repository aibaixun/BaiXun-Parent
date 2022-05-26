package com.aibaixun.snowflake.util;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.util.Objects;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class IdWorkerUtil {

    private static IdentifierGenerator identifierGenerator;

    public IdWorkerUtil(IdentifierGenerator identifierGenerator) {
        IdWorkerUtil.identifierGenerator = identifierGenerator;
    }

    public static long getId() {
        return getId(new Object());
    }

    public static long getId(Object entity) {
        return Objects.isNull(identifierGenerator) ? IdWorker.getId(entity) : identifierGenerator.nextId(entity).longValue();
    }

    public static String getIdStr() {
        return getIdStr(new Object());
    }

    public static String getIdStr(Object entity) {
        return Objects.isNull(identifierGenerator) ? IdWorker.getIdStr(entity) : identifierGenerator.nextId(entity).toString();
    }
}
