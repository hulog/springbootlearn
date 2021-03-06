
package com.fuckSpring.config;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by upsmart on 17-6-15.
 */
@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.fuckSpring.controller..*.*(..))")
    public void ctrlLog() {
    }

    @Before("ctrlLog()")
    public void doCtrlBefore(JoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //打印请求内容
        logger.info("===============请==求==内==容(Start)===============");
        logger.info("客户端IP:{}", request.getRemoteAddr());
        logger.info("请求地址:{}", request.getRequestURL().toString());
        logger.info("请求方式:{}", request.getMethod());
        logger.info("请求类方法:{}", joinPoint.getSignature());
        List<Object> argsList = new ArrayList<>();
        Arrays.stream(joinPoint.getArgs()).forEach(argsList::add);
        logger.info("请求类方法参数:{}", this.toJsonString(argsList));
        logger.info("===============请==求==内==容( End )===============");
    }

    private String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    @AfterReturning(returning = "ret", pointcut = "ctrlLog()")
    public void doCtrlReturning(Object ret) {
        logger.info("返回数据:{}", this.toJsonString(ret));
    }

    @Around("ctrlLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
        logger.info("Controller层切面已执行");
        long start = System.currentTimeMillis();
        Object proceed = null;
        try {
            proceed = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long elapsedTime = System.currentTimeMillis() - start;
        logger.info("执行时间:{}ms", elapsedTime);
        return proceed;
    }
}
