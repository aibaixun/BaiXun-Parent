package com.aibaixun.basic.result;

/**
 * 状态码
 * @author wangxiao@aibaoxun.com
 * @date 2021/10/27
 */
public enum BaseResultCode implements ResultCode {

    /**
     * 成功
     */
    OK(0),
    /**
     * token 过期
     */
    TOKEN_EXPIRED(2000),
    /**
     * 刷新token 过期
     */
    REFRESH_TOKEN_EXPIRED(2001),

    /**
     * 参数错误
     */
    BAD_PARAMS(4001),
    /**
     * 未授权
     */
    NO_AUTH(4002),
    /**
     * 未登陆
     */
    NO_LOGIN(4003),
    /**
     * 数据不存在
     */
    NO_ITEM(4004),
    /**
     * 请求太多
     */
    TOO_MANY_REQUEST(4005),
    /**
     * 后端通用错误
     */
    GENERAL_ERROR(5000),
    /**
     * 加解密失败
     */
    ENCRYPT_FAILED(5001),
    /**
     * 锁 异常
     */
    LOCK_FAILED(5002),

    /**
     * json 异常
     */
    JSON_ERROR(5003);

    private final int code;

    BaseResultCode(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return code;
    }


}
