package com.leo.cousumer.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LeoLogAspect {

    /*@Around("@annotation(leoLog)")
    public void around(ProceedingJoinPoint point, LeoLog leoLog)
        throws Throwable{
        String strClassName = point.getTarget().getClass().getName();
        String strMethidName = point.getSignature().getName();
        log.info("类名={}，方法={}",strClassName, strMethidName);
    }

    @AfterThrowing(pointcut = "@annotation(leoLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint,  LeoLog leoLog, Throwable e){
        System.out.println("执行失败");
        String strClassName = joinPoint.getTarget().getClass().getName();
        String strMethidName = joinPoint.getSignature().getName();
        log.info("类名={}，方法={} 执行失败！",strClassName, strMethidName);
    }*/

    // 把切面的连接点放在了我们的注解上
    @Pointcut("@annotation(com.leo.cousumer.annotation.LeoLog)")
    public void ouAspect() {
    }
    // 在这里定义前置切面
    @Before("ouAspect()")
    public void beforeMethod(JoinPoint joinPoint) {

        String strClassName = joinPoint.getTarget().getClass().getName();
        String strMethidName = joinPoint.getSignature().getName();
        log.info("方法前：类名={}，方法={}",strClassName, strMethidName);
    }

    // 在这里定义前置切面
    @After("ouAspect()")
    public void afterMethod(JoinPoint joinPoint) {

        String strClassName = joinPoint.getTarget().getClass().getName();
        String strMethidName = joinPoint.getSignature().getName();
        log.info("方法后：类名={}，方法={}",strClassName, strMethidName);
        //得到被切方法的参数
        /*System.out.println(joinPoint.getArgs()[0]);*/
    }
}
