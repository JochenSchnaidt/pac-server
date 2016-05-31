package com.prodyna.pac.monitoring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BusinessAspect extends AbstractAspect {

    @Pointcut("execution(* com.prodyna.pac.service.UserService.*(..))")
    private void userPointcut() {
    }

    @Pointcut("execution(* com.prodyna.pac.service.VoteService.*(..))")
    private void votePointcut() {
    }

    @Pointcut("execution(* com.prodyna.pac.service.VotingService.*(..))")
    private void votingPointcut() {
    }

    @Around("userPointcut()")
    public Object createUserStatistic(ProceedingJoinPoint joinPoint) throws Throwable {
        return createStatistic(joinPoint);
    }

    @Around("votePointcut()")
    public Object createVoteStatistic(ProceedingJoinPoint joinPoint) throws Throwable {
        return createStatistic(joinPoint);
    }

    @Around("votingPointcut()")
    public Object createVotingStatistic(ProceedingJoinPoint joinPoint) throws Throwable {
        return createStatistic(joinPoint);
    }

}