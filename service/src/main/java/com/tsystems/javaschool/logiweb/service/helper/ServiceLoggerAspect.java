package com.tsystems.javaschool.logiweb.service.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Logs every entry in service methods
 */
@Component
@Aspect
public class ServiceLoggerAspect {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceLoggerAspect.class);

    @Before("execution(* com.tsystems.javaschool.logiweb.service.impl.*.* (..))")
    public void beforeAdvice(JoinPoint jp) throws Exception {
        Class<?> clazz = jp.getTarget().getClass();
        String methodName = jp.getSignature().getName();
        Object[] args = jp.getArgs();

        LOG.info(clazz.getSimpleName() + "." + methodName + "("
                + convertArgsToString(args) + ") is called");

    }

    private String convertArgsToString(Object[] args) {
        if (ArrayUtils.isEmpty(args)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                sb.append("null");
            } else {
                if (args[i].getClass() != null) {
                    sb.append(args[i].getClass().getSimpleName());
                } else {
                    sb.append("");
                }
                sb.append(":");
                sb.append(args[i].toString());
            }

            if (i != args.length - 1) { // not last one
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
