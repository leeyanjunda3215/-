package com.back.util;

import java.lang.annotation.*;

/**
 * 系统日注解
 *  被注解标识的方法执行的时候会被AOP拦截，然后记录相关操作
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
