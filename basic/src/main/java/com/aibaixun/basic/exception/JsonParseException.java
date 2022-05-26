package com.aibaixun.basic.exception;

import com.aibaixun.basic.result.BaseResultCode;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class JsonParseException extends BaseException{


    public JsonParseException(String message) {
        super(message, BaseResultCode.JSON_ERROR);
    }
}
