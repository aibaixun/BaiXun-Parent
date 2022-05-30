package com.aibaixun.common.filter;


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

    private static final String UID = "uid";

    private static final String X_REAL_IP ="X-Real-IP";

    private static final String X_FORWARD_FOR = "X-Forward-For";

    public AuthenticationFilter() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String uid = request.getHeader(UID);
        UserSessionUtil.setCurrentSession(uid);
        logger.info("请求地址: {}", request.getRequestURI());
        if (StringUtil.isEmpty(request.getHeader(X_REAL_IP))) {
            CurrentSafeTicketUtil.setRealIp(request.getHeader(X_FORWARD_FOR));
        } else {
            CurrentSafeTicketUtil.setRealIp(request.getHeader(X_REAL_IP));
        }

        filterChain.doFilter(servletRequest, servletResponse);
        UserSessionUtil.removeCurrentSession();
        CurrentSafeTicketUtil.deleteRealIp();
    }

    public void destroy() {
    }
}
