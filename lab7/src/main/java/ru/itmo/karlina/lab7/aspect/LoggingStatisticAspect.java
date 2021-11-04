package ru.itmo.karlina.lab7.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class LoggingStatisticAspect {
    private final Map<String, Integer> methodExecutionNumber = new HashMap<>();
    private final Map<String, Long> methodExecutionTime = new HashMap<>();

    @Around("execution(* ru.itmo.karlina.lab7.domain.CustomerManager.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodSignature = joinPoint.getSignature().toString();
        methodExecutionNumber.put(methodSignature, methodExecutionNumber.getOrDefault(methodSignature, 0) + 1);

        long startNs = System.nanoTime();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        long finishTime = System.nanoTime() - startNs;

        methodExecutionTime.put(methodSignature, methodExecutionTime.getOrDefault(methodSignature, 0L) + finishTime);

        return result;
    }

    public void printStatistic() {
        for(String name : methodExecutionNumber.keySet()) {
            assert methodExecutionTime.containsKey(name);
            System.out.println("Statistics for " + name);
            int executions = methodExecutionNumber.get(name);
            long executionTime = methodExecutionTime.get(name);
            System.out.println("Number of executions: " + executions);
            System.out.println("Average time of execution: " + executionTime / executions);
            System.out.println("All calls execution time: " + executionTime);
            System.out.println();
        }
    }
}
