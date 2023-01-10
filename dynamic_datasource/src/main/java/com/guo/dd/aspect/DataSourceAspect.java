package com.guo.dd.aspect;

import com.guo.dd.annotation.DataSource;
import com.guo.dd.datasource.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(12)
public class DataSourceAspect {
    // 所有标了@DataSource注解的方法或者类都会被拦截并加上功能
    @Pointcut("@annotation(com.guo.dd.annotation.DataSource) || @within(com.guo.dd.annotation.DataSource)")
    public void pc(){

    }

    @Around("pc()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        DataSource dataSource=getDataSource(pjp);
        if(dataSource!=null){
            // 拿到@DataSource注解设置的数据源
             String value=dataSource.value();
            DynamicDataSourceContextHolder.setDataSourceType(value);
        }
        try {
            // 执行连接点方法，在过程中发现需要数据源就会去加载数据源，就会去找AbstractRoutingDataSource相关类。执行完之后返回相应的结果
            return pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }finally {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
    }

    private DataSource getDataSource(ProceedingJoinPoint pjp) {
        // 签名包含了当前连接点对象的所有信息
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        // 找标在接入点方法上的DataSource注解（标在方法上的注解优先级比标在类上的高，所以先判断）
        DataSource annotation = AnnotationUtils.findAnnotation(signature.getMethod(), DataSource.class);
        if(annotation!=null){
            return annotation;
        }
        return AnnotationUtils.findAnnotation(signature.getDeclaringType(),DataSource.class);

    }
}
