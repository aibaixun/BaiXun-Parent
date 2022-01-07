package com.aibaixun.common.thread;

/**
 * @author jiangshicheng@aibaixun.com
 * @date 2022/1/7
 */
public enum BlockQueueTypeEnum {
    /**
     * 列表队列
     */
    ARRAY,
    /**
     * 单一队列
     */
    SYNCHRONOUS,
    /**
     * 链表队列
     */
    LINKED,
    /**
     * 优先级队列
     */
    PRIORITY
}
