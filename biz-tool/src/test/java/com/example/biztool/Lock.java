package com.example.biztool;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author linzhou
 * @ClassName Lock.java
 * @createTime 2021年12月17日 15:23:00
 * @Description
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Lock {

    Class<?extends ResultCode> key();

    String name();

}
