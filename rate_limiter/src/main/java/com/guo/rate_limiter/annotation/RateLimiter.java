package com.guo.rate_limiter.annotation;

import com.guo.rate_limiter.enums.LimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimiter {
    String key() default "rate_limit:";

    /**
     * 限流时间窗
     * @return
     */
    int time() default 60;

    int count() default 100;

    LimitType limitType() default LimitType.DEFAULT;

}
