package com.prodyna.pac.monitoring;

import java.util.Arrays;

import javax.management.MalformedObjectNameException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.MBeanExportException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

public abstract class AbstractAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatisticProvider service;

    protected Object createStatistic(ProceedingJoinPoint joinPoint) throws Throwable {

        log.debug("hijacked method: " + joinPoint.getSignature().getName() + " - hijacked arguments: " + Arrays.toString(joinPoint.getArgs()));

        StopWatch watch = new StopWatch();
        watch.start();

        Object value = joinPoint.proceed(); // continue on the intercepted method

        watch.stop();

        logging(joinPoint.getSignature(), watch);

        return value;
    }

    @Async
    protected void logging(Signature signature, StopWatch watch) throws MBeanExportException, MalformedObjectNameException, NullPointerException, IllegalStateException {

        String methodName = signature.getName();

        String identifier = signature.getDeclaringTypeName() + "." + methodName;

        String className = StringUtils.unqualify(signature.getDeclaringTypeName());

        service.updateValue(identifier, className, methodName, watch.getLastTaskTimeMillis());

        log.info("request took: " + watch.getLastTaskTimeMillis() + "ms");
    }

}
