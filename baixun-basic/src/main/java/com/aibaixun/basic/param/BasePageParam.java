package com.aibaixun.basic.param;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/7
 */
public abstract class BasePageParam {

    private int page;

    private int pageSize;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
