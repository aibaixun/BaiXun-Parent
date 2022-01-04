package com.aibaixun.common.util;

import com.aibaixun.common.factory.ApplicationContextHelper;

/**
 * @author wangxiao@aibaixun.com
 * @date 2021/12/31
 */
public class DomainFactory {
    public static <T> T create(Class<T> entityClz){
        return ApplicationContextHelper.getBean(entityClz);
    }
}
