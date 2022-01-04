package com.aibaixun.basic.context;


import com.aibaixun.basic.entity.UserInfo;
import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.result.BaseResultCode;

/**
 * @author wangxiao@aibaixun.com
 * @date 2021/12/31
 */
public class UserContextHolder {

    private static final ThreadLocal<UserInfo> CONTEXT = new InheritableThreadLocal<>();


    private UserContextHolder(){
        throw new RuntimeException("无法初始化UserContextHolder");
    }

    public static void setUserInfo(UserInfo userInfo) {
        CONTEXT.set(userInfo);
    }

    public static String getTenantId() throws BaseException {
        UserInfo userInfo = getUserInfo();
        return userInfo.getTenantId();
    }


    public static String getUserId() throws BaseException {
        UserInfo userInfo = getUserInfo();
        return userInfo.getUserId();
    }


    public static String getUserLabel() throws BaseException {
        UserInfo userInfo = getUserInfo();
        return userInfo.getUserLabel();
    }

    private static UserInfo getUserInfo() throws BaseException {
        var userInfo = CONTEXT.get();
        if (userInfo == null){
            throw new BaseException("用户信息不存在",BaseResultCode.NO_LOGIN);
        }
        return userInfo;
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
