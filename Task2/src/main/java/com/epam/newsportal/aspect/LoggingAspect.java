package com.epam.newsportal.aspect;

import org.apache.log4j.LogManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger("");
    private int tab;

    @Around("execution(* com.epam.newsportal.*.*.*(..))")
    public Object log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        StringBuilder tabs = new StringBuilder();
        for (int i = 0; i < tab; i++) {
            tabs.append("- ");
        }
        logger.info("{}entering method: {}", tabs, proceedingJoinPoint.getSignature());
        tab++;
        Object object = null;
        try {
            object = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(), throwable);
            throw throwable;
        }
        tab--;
        logger.info("{}exiting method: {}", tabs, proceedingJoinPoint.getSignature());
        return object;
    }
}
