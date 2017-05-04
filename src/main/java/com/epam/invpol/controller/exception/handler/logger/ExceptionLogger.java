package com.epam.invpol.controller.exception.handler.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class ExceptionLogger {

    private static Logger logger = LogManager.getLogger(ExceptionLogger.class);

    @Before("@annotation(com.epam.invpol.controller.exception.handler.logger.annotation.WarnLoggable))")
    public void doLogWarn(JoinPoint joinPoint) {
        logger.warn(Arrays.toString(joinPoint.getArgs()));
    }

    @Before("@annotation(com.epam.invpol.controller.exception.handler.logger.annotation.ErrorLoggable))")
    public void doLogError(JoinPoint joinPoint) {
        logger.error(Arrays.toString(joinPoint.getArgs()));
    }
}
