package com.nowcoder.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//用元注解进行描述
@Target(ElementType.METHOD)     //作用的位置，可以写在METHOD方法上用来描述方法
@Retention(RetentionPolicy.RUNTIME)  //RUMTIME即程序运行的时候才生效
public @interface LoginRequired {



}
