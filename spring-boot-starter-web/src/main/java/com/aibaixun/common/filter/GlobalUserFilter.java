package com.aibaixun.common.filter;

import com.aibaixun.basic.constants.BsaeConst;
import com.aibaixun.basic.context.UserContextHolder;
import com.aibaixun.basic.entity.BaseAuthUser;
import com.aibaixun.common.redis.util.RedisRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author crj
 * @description: 获取当前登录的用户信息
 * @date 2022/1/139:27
 */

@Component
@WebFilter(urlPatterns = "/*", filterName = "globalUserFilter")
public class GlobalUserFilter implements Filter, Ordered {
    @Autowired
    private RedisRepository redisRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader(BsaeConst.TOKEN_HEADER_NAME);
        if(StringUtils.isBlank(token)){
            String userId = request.getHeader(BsaeConst.USERID_HEADER_NAME);
            token = (String) redisRepository.get(getUserIdRedisKey(userId));
        }
        BaseAuthUser userInfo = (BaseAuthUser) redisRepository.get(getTokenRedisKey(token));
        UserContextHolder.setUserInfo(userInfo);
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public int getOrder() {
        return 1;
    }
    private String getTokenRedisKey(String token){
        return BsaeConst.TOKEN_REDIS_PREFIX+token;
    }
    private String getUserIdRedisKey(String userId){
        return BsaeConst.USERID_REDIS_PREFIX+userId;
    }
}
