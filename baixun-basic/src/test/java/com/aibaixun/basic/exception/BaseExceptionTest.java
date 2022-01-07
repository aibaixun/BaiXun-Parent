package com.aibaixun.basic.exception;

import com.aibaixun.basic.result.BaseResultCode;

/**
 * @author wangxiao@aibaixun.com
 * @date 2021/12/30
 */
public class BaseExceptionTest {

    public static void main(String[] args) {
        BaseExceptionTest baseExceptionTest = new BaseExceptionTest();
        try {
            baseExceptionTest.testSampleException();
        } catch (BaseException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        try {
            baseExceptionTest.testFillInStackTrace();
        }catch (BaseException e){
            e.printStackTrace();
        }

        try {
            baseExceptionTest.testMoreFillInStackTrace();
        } catch (BaseException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    public void testSampleException () throws BaseException {
        throw new BaseException("10", BaseResultCode.GENERAL_ERROR);
    }

    public void testFillInStackTrace () throws BaseException {
        throw new BaseException(new IllegalArgumentException("参数错误"),BaseResultCode.GENERAL_ERROR);
    }


    public void testMoreFillInStackTrace () throws BaseException {
        throw new BaseException(new BaseException("错误的参数",new IllegalArgumentException("参数错误1"),BaseResultCode.GENERAL_ERROR),BaseResultCode.BAD_PARAMS);
    }



}
