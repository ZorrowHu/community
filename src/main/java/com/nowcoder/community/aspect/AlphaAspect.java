package com.nowcoder.community.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class AlphaAspect {
    //第一个*代表接受任何类型的返回值
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut() {

    }
    //切点前织入
    @Before("pointcut()")
    public void before() {
        System.out.println("before");
    }
    //切点后织入
    @After("pointcut()")
    public void after() {
        System.out.println("after");
    }
    //有了返回值以后再织入
    @AfterReturning("pointcut()")
    public void afterRetuning() {
        System.out.println("afterRetuning");
    }
    //抛异常的时候织入
    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        System.out.println("afterThrowing");
    }
    //前后都织入逻辑
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("around before"); //前织入
        Object obj = joinPoint.proceed(); //调用目标组件的方法（原始对象）
        System.out.println("around after"); //后织入
        return obj;
    }

}
