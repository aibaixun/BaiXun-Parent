package com.aibaixun.common.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
public class TraceIdHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private final Map<String, String> headers = new HashMap();

    public TraceIdHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public void putHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public String getHeader(String name) {
        String headerValue = (String)this.headers.get(name);
        return headerValue != null ? headerValue : ((HttpServletRequest)this.getRequest()).getHeader(name);
    }

    public Enumeration<String> getHeaderNames() {
        Set<String> set = new HashSet(this.headers.keySet());
        Enumeration<String> e = ((HttpServletRequest)this.getRequest()).getHeaderNames();

        while(e.hasMoreElements()) {
            String n = e.nextElement();
            set.add(n);
        }

        return Collections.enumeration(set);
    }
}
