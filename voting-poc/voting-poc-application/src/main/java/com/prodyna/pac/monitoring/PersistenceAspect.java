package com.prodyna.pac.monitoring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Class contains {@code Pointcut} expressions and aspect types for the persistence layer.
 *
 * @see AbstractAspect
 */
@Aspect
@Component
public class PersistenceAspect extends AbstractAspect {

	@Pointcut("execution(* com.prodyna.pac.persistence.UserPersistenceService.*(..))")
	private void userPointcut() {
	}

	@Pointcut("execution(* com.prodyna.pac.persistence.VotePersistenceService.*(..))")
	private void votePointcut() {
	}

	@Around("userPointcut()")
	public Object createUserStatistic(ProceedingJoinPoint joinPoint) throws Throwable {
		return createStatistic(joinPoint);
	}

	@Around("votePointcut()")
	public Object createVoteStatistic(ProceedingJoinPoint joinPoint) throws Throwable {
		return createStatistic(joinPoint);
	}

}