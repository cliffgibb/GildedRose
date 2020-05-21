package com.miwtech.gildedrose.task.framework;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.miwtech.gildedrose.task.framework.exceptions.TaskException;

@Aspect
public class ExceptionTranslationAspect {

    @Around("execution(* com.miwtech.gildedrose.task.tasks.playstore.login.PlayStoreLoginTask..*(..))")
    public Object handleException(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();
        } catch (TaskException e) {
            throw new Exception(e);
        }
    }
}
