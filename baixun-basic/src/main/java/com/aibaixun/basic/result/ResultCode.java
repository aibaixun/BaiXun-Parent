package com.aibaixun.basic.result;

import java.io.Serializable;

/**
 * 返回结果 状态码
 * @author wangxiao@aibaoxun.com
 * @date 2021/10/27
 */
public interface ResultCode extends Serializable {

    /**
     * 获取状态码
     * @return code -- 状态码
     */
    Integer getCode();

}
