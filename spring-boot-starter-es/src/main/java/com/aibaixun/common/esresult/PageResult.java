package com.aibaixun.common.esresult;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/4
 */
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -275582248840137389L;
    /**
     * 总数
     */
    private Long count;
    /**
     * 是否成功：0 成功、1 失败
     */
    private int code;
    /**
     * 当前页结果集
     */
    private List<T> data;


    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


    public PageResult<T> data (List<T> data){
        this.data = data;
        return this;
    }

    public PageResult<T> code (int code){
        this.code = code;
        return this;
    }

    public PageResult<T> count (long count){
        this.count = count;
        return this;
    }
}
