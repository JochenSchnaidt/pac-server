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

/**
 * Generic aspect to measure the time a method needs to execute.
 */
public abstract class AbstractAspect {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private StatisticProvider service;

	/**
	 * Measures the time a method needs to execute
	 *
	 * @param joinPoint
	 *            the {@code JoinPoint} on which the aspect intercepts
	 * @return the return value of the original method
	 * @throws Throwable
	 *             if any error occurs
	 */
	protected Object createStatistic(ProceedingJoinPoint joinPoint) throws Throwable {

		log.debug("hijacked method: " + joinPoint.getSignature().getName() + " - hijacked arguments: " + Arrays.toString(joinPoint.getArgs()));

		StopWatch watch = new StopWatch();
		watch.start();

		Object value = joinPoint.proceed(); // continue on the intercepted method

		watch.stop();

		logging(joinPoint.getSignature(), watch);

		return value;
	}

	/**
	 * Extracts the identifier to log the measured processing time
	 *
	 * @param signature
	 *            the {@code JoinPoint Signature} of the current method
	 * @param watch
	 *            the {@code StopWatch} with the last measured processing time
	 * @throws MBeanExportException
	 *             forwarded {@code Exception} from {@link StatisticProvider}
	 * @throws MalformedObjectNameException
	 *             forwarded {@code Exception} from {@link StatisticProvider}
	 * @throws NullPointerException
	 *             forwarded {@code Exception} from {@link StatisticProvider}
	 * @throws IllegalStateException
	 *             forwarded {@code Exception} from {@link StatisticProvider}
	 */
	@Async
	protected void logging(Signature signature, StopWatch watch) throws MBeanExportException, MalformedObjectNameException, NullPointerException, IllegalStateException {

		String methodName = signature.getName();

		String identifier = signature.getDeclaringTypeName() + "." + methodName;

		String className = StringUtils.unqualify(signature.getDeclaringTypeName());

		service.updateValue(identifier, className, methodName, watch.getLastTaskTimeMillis());

		log.debug("request took: " + watch.getLastTaskTimeMillis() + "ms");
	}

}
