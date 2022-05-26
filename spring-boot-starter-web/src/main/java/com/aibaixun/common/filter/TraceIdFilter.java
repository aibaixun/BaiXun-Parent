package com.aibaixun.common.filter;

import com.aibaixun.basic.util.IDUtils;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
@Order
@WebFilter(
        filterName = "traceIdFilter",
        urlPatterns = {"/*"}
)
@Component
public class TraceIdFilter implements Filter {


    public static final String TRACE_ID ="TraceId";

    public TraceIdFilter() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String traceId = MDC.get(TRACE_ID);
        if (traceId == null) {
            traceId = IDUtils.randomUUID();
            MDC.put("TraceId", traceId);
        }

        TraceIdHttpServletRequestWrapper requestWrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest request = (HttpServletRequest)servletRequest;
            if (request.getHeader(TRACE_ID) == null) {
                requestWrapper = new TraceIdHttpServletRequestWrapper(request);
                requestWrapper.putHeader(TRACE_ID, traceId);
            }
        }

        filterChain.doFilter(Objects.nonNull(requestWrapper) ? requestWrapper : servletRequest, servletResponse);
    }
}
