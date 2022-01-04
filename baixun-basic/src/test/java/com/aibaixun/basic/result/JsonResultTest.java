package com.aibaixun.basic.result;

import java.util.Arrays;

/**
 * json result test
 * @author wangxiao@aibaixun.com
 * @date 2021/10/27
 */
public class JsonResultTest {

    public static void main(String[] args) {
        JsonResultTest jsonResultTest = new JsonResultTest();
        jsonResultTest.testSuccess();
        System.out.println("------------------");
        jsonResultTest.testFailed();
        System.out.println("------------------");
        jsonResultTest.testMoreThread(10);
    }

    public void testSuccess () {
        System.out.println(JsonResult.successJustMsg("a"));
        System.out.println(JsonResult.success("a"));
        System.out.println(JsonResult.successWithMsg("a","a"));
        System.out.println(JsonResult.successWithMsg(BaseResultCode.BAD_PARAMS,"aa"));
        System.out.println(JsonResult.success("a").isSuccess());
        System.out.println(JsonResult.success(BaseResultCode.OK,""));
    }


    public void testFailed () {
        System.out.println(JsonResult.failed("a"));
        System.out.println(JsonResult.failed(10000,"a"));
        System.out.println(JsonResult.failed(BaseResultCode.BAD_PARAMS,"aa"));
        System.out.println(JsonResult.failed("a").isSuccess());
    }


    public void testMoreThread (int threadSize) {
        final JsonResult[] jsonResults = new JsonResult[threadSize];
        for (int i = 0; i < threadSize; i++) {
            int finalI = i;
            new Thread(() -> jsonResults[finalI] = JsonResult.judge(finalI%2==0)).start();
        }
        Arrays.stream(jsonResults).forEach(e->{
            System.out.println(JsonResult.isSuccess(e));
            System.out.printf("data=%s,errorCode=%s,msg=%s\n", e.getData(),e.getErrorCode(),e.getMsg());
        });
    }



}
