package com.example.jsontransformation.Aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("execution(public * com.example.jsontransformation.*.Transform*.*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Logging before proceeding on Method: {}, with Arguments: {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        Object result = null;
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.info("Exception during method execution: {}", throwable.getMessage());
            throw throwable;
        } finally {
            stopWatch.stop();
            logger.info("Logging after proceeding on Method: {}, that Returned value: {}", joinPoint.getSignature().getName(), result);
            logger.info("Execution Time: {}:: executed in {} ms", joinPoint.getSignature().getName(), stopWatch.getTotalTimeMillis());
        }
        return result;
    }
}