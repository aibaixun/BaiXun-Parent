package com.aibaixun.basic.thread;


/**
 * @author wangxiao@aibaixun.com
 * @date 2021/12/30
 */
public class BaseThreadFactoryTest {


    public static void main(String[] args) {
        BaseThreadFactoryTest baseThreadFactoryTest = new BaseThreadFactoryTest();
        baseThreadFactoryTest.testNewThreadName();;
    }

    public void testNewThreadName () {
        Thread thread =  BaseThreadFactory.forName("aaa").newThread(() -> System.out.println("bbb"));
        System.out.println(thread.getName());
        System.out.printf("priority=%d", thread.getPriority());
        System.out.println(thread);
        thread.start();
    }
}
