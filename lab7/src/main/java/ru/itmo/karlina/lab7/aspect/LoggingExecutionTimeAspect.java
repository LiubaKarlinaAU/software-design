package ru.itmo.karlina.lab7.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingExecutionTimeAspect {

    @Around("execution(* ru.itmo.karlina.lab7.domain.CustomerManager.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startNs = System.nanoTime();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        long finishTime = System.nanoTime() - startNs;
        System.out.println("Start method " + joinPoint.getSignature().getName());
        System.out.println("Finish method " + joinPoint.getSignature().getName()
                + ", execution time in ns: " + finishTime);

        return result;
    }
}