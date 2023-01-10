package com.guo.dd.aspect;

import com.guo.dd.datasource.DataSourceType;
import com.guo.dd.datasource.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Aspect
@Component
@Order(11)// 优先级低的后执行，数字大优先级低,后执行的会覆盖掉先执行的
public class GlobalDataSourceAspect {
    @Autowired
    HttpSession httpSession;
    @Pointcut("execution(* com.guo.dd.service.*.*(..))")
    public void pc(){

    }

    @Around("pc()")
    public Object around(ProceedingJoinPoint pjp){
        DynamicDataSourceContextHolder.setDataSourceType((String) httpSession.getAttribute(DataSourceType.DS_SESSION_KEY));
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }finally {
            DynamicDataSourceContextHolder.clearDataSourceType();
        }
        return null;
    }

}
