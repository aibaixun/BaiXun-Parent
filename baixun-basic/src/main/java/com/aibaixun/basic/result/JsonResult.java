package com.aibaixun.basic.result;

import java.io.Serializable;

/**
 * 返回结果
 * @author wangxiao@aibaoxun.com
 * @date 2021/10/27
 */
public final class JsonResult<T> implements Serializable {

    private Integer errorCode;

    private T data;

    private String msg;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private static <T> JsonResult<T> success() {
        return result(BaseResultCode.OK,null,null);
    }

    public static <T> JsonResult<T> success(T data) {
        return result(BaseResultCode.OK,null,data);
    }

    public static <T> JsonResult<T> successWithMsg(String msg, T data) {
        return result(BaseResultCode.OK,msg,data);
    }

    public static <T> JsonResult<T> successJustMsg(String msg) {
        return result(BaseResultCode.OK,msg,null);
    }


    public static <T> JsonResult<T> success(ResultCode code, T data) {
        return result(code,null,data);
    }


    public static <T> JsonResult<T> successWithMsg(ResultCode code, String msg) {
        return result(code,msg,null);
    }

    private static <T> JsonResult<T> failed() {
        return result(BaseResultCode.GENERAL_ERROR,null,null);
    }

    public static <T> JsonResult<T> failed(String msg) {
        return result(BaseResultCode.GENERAL_ERROR, msg, null);
    }

    public static <T> JsonResult<T> failed(Integer code, String msg) {
        return result(code, msg, null);
    }

    public static <T> JsonResult<T> failed(ResultCode code, String msg) {
        return result(code,msg,null);
    }

    public static <T> JsonResult<T> judge(boolean status) {
        if (status) {
            return success();
        } else {
            return failed();
        }
    }


    private static <T> JsonResult<T> result(ResultCode code, String msg, T data) {
        return result(code.getCode(),msg,data);
    }

    private static <T> JsonResult<T> result(int code, String msg, T data) {
        JsonResult<T> jsonResult = new JsonResult<>();
        jsonResult.setErrorCode(code);
        jsonResult.setData(data);
        jsonResult.setMsg(msg);
        return jsonResult;
    }


    public static boolean isSuccess(JsonResult<?> jsonResult) {
        return jsonResult != null && jsonResult.isSuccess();
    }

    boolean isSuccess() {
        return  BaseResultCode.OK.getCode().equals(this.errorCode);
    }


    @Override
    public String toString() {
        return "{" +
                "errorCode=" + errorCode +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
