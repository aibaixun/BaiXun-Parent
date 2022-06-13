package com.aibaixun.common.filter;


import com.aibaixun.basic.jwt.JwtUtil;
import com.aibaixun.basic.util.StringUtil;
import com.aibaixun.basic.util.UserSessionUtil;
import com.aibaixun.common.util.CurrentSafeTicketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
@Order
@WebFilter(
        filterName = "authenticationFilter",
        urlPatterns = {"/**"}
)
@Component
public class AuthenticationFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);




    public AuthenticationFilter() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String uid = request.getHeader(JwtUtil.DEFAULT_USER_ID);
        String tid = request.getHeader(JwtUtil.DEFAULT_TENANT_ID);
        UserSessionUtil.setCurrentSession(uid,tid);
        if (StringUtil.isEmpty(request.getHeader(JwtUtil.X_REAL_IP))) {
            CurrentSafeTicketUtil.setRealIp(request.getHeader(JwtUtil.X_FORWARD_FOR));
        } else {
            CurrentSafeTicketUtil.setRealIp(request.getHeader(JwtUtil.X_REAL_IP));
        }
        logger.info("Request Path is: {} from ip: {} by uid:{},startTime is:{}", request.getRequestURI(),CurrentSafeTicketUtil.getRealIp(),uid,System.currentTimeMillis());
        filterChain.doFilter(servletRequest, servletResponse);
        logger.info("Request Path is: {} from ip: {} by uid:{},endTime is:{}", request.getRequestURI(),CurrentSafeTicketUtil.getRealIp(),uid,System.currentTimeMillis());
        UserSessionUtil.removeCurrentSession();
        CurrentSafeTicketUtil.deleteRealIp();
    }

    public void destroy() {
    }
}
