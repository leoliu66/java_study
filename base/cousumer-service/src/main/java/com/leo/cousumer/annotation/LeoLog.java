package com.leo.cousumer.annotation;

import java.lang.annotation.*;

/**
 * leo操作审计
 *
 * @author LIULU_LEO
 * Date 2020/5/31
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LeoLog {

    /*String value() default "";*/
}
