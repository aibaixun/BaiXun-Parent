package com.aibaixun.common.thread;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/1/7
 */
public class ThreadException extends RuntimeException {
    public ThreadException() {
        super();
    }

    public ThreadException(String msg) {
        super(msg);
    }

    public ThreadException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
