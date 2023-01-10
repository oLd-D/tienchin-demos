package com.guo.dd.annotation;

import com.guo.dd.datasource.DataSourceType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface DataSource {
    String value() default DataSourceType.DEFAULT_DS_NAME;
}
