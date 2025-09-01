package com.rpgplace.application.infrastructure.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @AfterThrowing(pointcut = "@annotation(logException)", throwing = "ex")
    public void logException(JoinPoint joinPoint, LogException logException, Exception ex) {
        logger.error("{} in {}: {}", logException.message(), joinPoint.getSignature().toShortString(), ex.getMessage());
    }
}
