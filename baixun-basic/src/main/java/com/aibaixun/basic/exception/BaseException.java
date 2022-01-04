package com.aibaixun.basic.exception;

import com.aibaixun.basic.result.ResultCode;

/**
 * 异常信息
 * @author wangxiao@aibaoxun.com
 * @date 2021/10/27
 */
public  class BaseException extends Exception{

    private static final long serialVersionUID = 1L;

    private ResultCode errorCode;

    public BaseException(ResultCode errorCode) {
        this.errorCode = errorCode;
    }

    public BaseException(String message, ResultCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(String message, Throwable cause, ResultCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BaseException(Throwable cause, ResultCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    /**
     *  api异常的昂贵且无用的堆栈跟踪
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }


    public ResultCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ResultCode errorCode) {
        this.errorCode = errorCode;
    }


    @Override
    public String toString() {
        return "BaiXunException{" +
                "errorCode=" + errorCode +
                '}';
    }
}
