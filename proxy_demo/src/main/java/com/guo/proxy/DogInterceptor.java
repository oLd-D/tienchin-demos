package com.guo.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 编写类的方法的拦截器
 */
public class DogInterceptor implements MethodInterceptor {
    /**
     * 类似于 InvocationHandler#invoke,
     * 调用被代理对象的方法的时候进行拦截并添加自己的逻辑
     * @param o           代理对象
     * @param method      代理的方法
     * @param objects     方法的参数
     * @param methodProxy 方法对象
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        long startTime = System.nanoTime();
        // 类似 JDK 动态代理中的 method.invoke 方法
        Object result = methodProxy.invokeSuper(o, objects);
        long endTime = System.nanoTime();
        System.out.println(method.getName() + " 方法执行耗时 " + (endTime - startTime) + " 纳秒");
        return result;
    }
}
