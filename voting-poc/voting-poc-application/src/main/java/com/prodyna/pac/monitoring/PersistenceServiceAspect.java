package com.prodyna.pac.monitoring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PersistenceServiceAspect extends AbstractAspect {

    @Pointcut("execution(** com.prodyna.pac.persistence.UserPersistenceService.*(..))")
    private void userPointcut() {
    }

    @Pointcut("execution(** com.prodyna.pac.persistence.VotePersistenceService.*(..))")
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