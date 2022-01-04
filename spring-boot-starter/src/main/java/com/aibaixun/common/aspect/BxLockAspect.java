package com.aibaixun.common.aspect;


import com.aibaixun.basic.exception.BaseException;
import com.aibaixun.basic.lock.DefaultMemoryLock;
import com.aibaixun.basic.lock.Lock;
import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.common.annotation.BxLock;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;


/**
 * @author wangxiao@aibaixun.com
 * @date 2022/1/1
 */
@Aspect
public class BxLockAspect {

    @Autowired(required = false)
    private Lock locker;

    /**
     * 用于SpEL表达式解析.
     */
    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();


    private final Logger logger = LoggerFactory.getLogger(BxLockAspect.class);


    @Around("@within(bxLock) || @annotation(bxLock)")
    public Object aroundLock(ProceedingJoinPoint point, BxLock bxLock) throws Throwable {
        if (bxLock == null) {
            // 获取类上的注解
            bxLock = point.getTarget().getClass().getDeclaredAnnotation(BxLock.class);
        }
        String lockKey = bxLock.key();
        if (locker == null) {
            logger.warn("Locker is null,want to be usr MemoryLock");
            locker = new DefaultMemoryLock();
        }
        if (StringUtils.isBlank(lockKey)) {
            throw new BaseException("BxLock key is empty",BaseResultCode.GENERAL_ERROR);
        }
        final String spElFlag = "#";
        if (lockKey.contains(spElFlag)) {
            MethodSignature methodSignature = (MethodSignature)point.getSignature();
            Object[] args = point.getArgs();
            lockKey = getValBySpEl(lockKey, methodSignature, args);
        }
        try {
            var lockResult = locker.lock(lockKey,bxLock.expireTime(),bxLock.retryCounter(),0);
            if (lockResult) {
                return point.proceed();
            } else {
                throw new BaseException("请稍后重试，程序正在处理中",BaseResultCode.GENERAL_ERROR);
            }
        } finally {
            locker.releaseLock(lockKey);
        }
    }

    /**
     * 解析spEL表达式
     */
    private String getValBySpEl(String spEl, MethodSignature methodSignature, Object[] args) {
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        if (paramNames != null && paramNames.length > 0) {
            Expression expression = spelExpressionParser.parseExpression(spEl);
            EvaluationContext context = new StandardEvaluationContext();
            for(int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
            return expression.getValue(context,String.class);
        }
        return null;
    }
}
