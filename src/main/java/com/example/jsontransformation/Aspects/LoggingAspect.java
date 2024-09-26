package com.example.jsontransformation.Aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

/*    @Before("execution(public * com.example.jsontransformation.*.Transform*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Logging before method execution...");
        System.out.println("Method: " + joinPoint.getSignature().getName());
        System.out.println("Arguments: " + Arrays.toString(joinPoint.getArgs()));
    }

    @After("execution(public * com.example.jsontransformation.*.Transform*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("Logging after method execution...");
        System.out.println("Method: " + joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "execution(public * com.example.jsontransformation.*.Transform*.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("Logging after method returns...");
        System.out.println("Method: " + joinPoint.getSignature().getName());
        System.out.println("Returned value: " + result);
    }

    @AfterThrowing(pointcut = "execution(public * com.example.jsontransformation.*.Transform*.*(..))", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        System.out.println("Logging after method throws an exception...");
        System.out.println("Method: " + joinPoint.getSignature().getName());
        System.out.println("Exception: " + error.getMessage());
    }*/
    @Around("execution(public * com.example.jsontransformation.*.Transform*.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Logging before proceeding on Method: {}, with Arguments: {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
//        System.out.println("Logging before proceeding on Method: " + joinPoint.getSignature().getName() + ", with Arguments: " + Arrays.toString(joinPoint.getArgs()));
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.info("Exception during method execution: {}", throwable.getMessage());
//            System.out.println("Exception during method execution: " + throwable.getMessage());
            throw throwable;
        }
        logger.info("Logging after proceeding on Method: {}, that Returned value: {}", joinPoint.getSignature().getName(), result);
//        System.out.println("Logging after proceeding on Method: " + joinPoint.getSignature().getName() + ", that Returned value: " + result);
        return result;
    }

    @Around("execution(public * com.example.jsontransformation.*.Transform*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object proceed = joinPoint.proceed();
        stopWatch.stop();
        logger.info("Execution Time: {}:: executed in {} ms", joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
//        System.out.println("Execution Time: " + joinPoint.getSignature().getName() + ":: executed in "  +stopWatch.getTotalTimeMillis() + " ms");
        return proceed;
    }
}