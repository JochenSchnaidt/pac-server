package com.prodyna.pac.monitoring;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

@Aspect
@Order(10)
public class BusinessServicesAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatisticProvider service;

    @Pointcut("execution(** com.prodyna.pac.persistence.UserPersistenceService.*(..))")
    private void persistencePointcut(){}
    
    
    @Around("persistencePointcut()")
    public Object createUserStatistic(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();

        StopWatch watch = new StopWatch();
        watch.start();

        log.error("hijacked method: " + methodName + "   -    hijacked arguments: " + Arrays.toString(joinPoint.getArgs()));

        System.out.println("Around before is running!");

        Object value = joinPoint.proceed(); // continue on the intercepted method

        System.out.println("Around after is running!");

        watch.stop();

        String identifier = joinPoint.getSignature().getDeclaringTypeName() + "." + methodName;

        String className = StringUtils.unqualify(joinPoint.getSignature().getDeclaringTypeName());

        service.updateValue(identifier, className, methodName, watch.getLastTaskTimeMillis());

        log.error("request took: " + watch.getLastTaskTimeMillis() + "ms");

        return value;
    }

}